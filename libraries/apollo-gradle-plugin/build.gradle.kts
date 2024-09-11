import okhttp3.OkHttpClient
import okhttp3.Request
import okio.buffer
import okio.sink

plugins {
  id("org.jetbrains.kotlin.jvm")
  id("java-gradle-plugin")
  id("com.gradle.plugin-publish")
  id("com.gradleup.gr8")
}

apolloLibrary(
    namespace = "com.apollographql.apollo.gradle.relocated",
    jvmTarget = 11 // AGP requires 11
)

// Configuration dependencies that will be shadowed
val shadeConfiguration = configurations.create("shade")

// Set to false to skip relocation and save some building time during development
val relocateJar = System.getenv("APOLLO_RELOCATE_JAR")?.toBoolean() ?: true

dependencies {
  add(shadeConfiguration.name, project(":apollo-gradle-plugin-external"))

  testImplementation(project(":apollo-ast"))
  testImplementation(libs.junit)
  testImplementation(libs.truth)
  testImplementation(libs.assertj)
  testImplementation(libs.okhttp.mockwebserver)
  testImplementation(libs.okhttp.tls)

  testImplementation(libs.apollo.execution)
  testImplementation(libs.apollo.execution.http4k)

  testImplementation(platform(libs.http4k.bom.get()))
  testImplementation(libs.http4k.core)
  testImplementation(libs.http4k.server.jetty)
  testImplementation(libs.slf4j.nop.get().toString()) {
    because("jetty uses SL4F")
  }

}

if (relocateJar) {
  val embeddedJarFile = file("build/r8/embedded.jar")
  val relocatedJarFile = file("build/r8/relocated.jar")
  val r8File = file("build/r8/r8.jar")

  val embeddedJarTaskProvider = tasks.register("embeddeJar", Zip::class) {
    /**
     * TODO: configuration cache
     */
    from(shadeConfiguration.elements.map { fileSystemLocations ->
      files().apply {
        fileSystemLocations.forEach {
          from(zipTree(it.asFile))
        }
      }
    })
    // The jar is mostly empty but this is needed for the plugin descriptor
    from(tasks.jar.map { zipTree(it.outputs.files.singleFile) })

    exclude("META-INF/MANIFEST.MF")

    duplicatesStrategy = DuplicatesStrategy.EXCLUDE

    destinationDirectory.set(embeddedJarFile.parentFile)
    archiveFileName.set(embeddedJarFile.name)
  }

  val downloadR8TaskProvider = tasks.register("downloadR8", DownloadR8Task::class.java) {
    sha1 = "c3d27dec4e48f97502a83d440c987c7a54f46c7b"
    outputFile.set(r8File)
  }

  val relocatedJarTaskProvider = tasks.register("relocateJar", JavaExec::class) {
    dependsOn(embeddedJarTaskProvider)
    debug = true
    classpath(downloadR8TaskProvider)
    mainClass.set("com.android.tools.r8.SwissArmyKnife")

    args(
        "relocator",
        "--input",
        embeddedJarFile.absolutePath,
        "--output",
        relocatedJarFile.absolutePath,
        "--map",
        "kotlin.Metadata->kotlin.Metadata",
        "--map",
        "kotlin.**->com.apollographql.relocated.kotlin",
        "--map",
        "org.objectweb.**->com.apollographql.relocated.org.objectweb",
        "--map",
        "org.jetbrains.**->com.apollographql.relocated.org.jetbrains",
        "--map",
        "org.intellij.**->com.apollographql.relocated.org.intellij",
        "--map",
        "kotlinx.**->com.apollographql.relocated.kotlinx",
        "--map",
        "okhttp3.**->com.apollographql.relocated.okhttp3",
        "--map",
        "com.squareup.**->com.apollographql.relocated.com.squareup",
        "--map",
        "com.benasher44.**->com.apollographql.relocated.com.benasher44",
        "--map",
        "okio.**->com.apollographql.relocated.com.okio",
    )
  }

  configurations.named("compileOnly").configure {
    extendsFrom(shadeConfiguration)
  }
  configurations.named("testImplementation").configure {
    extendsFrom(shadeConfiguration)
  }

  replaceOutgoingJar2(relocatedJarTaskProvider.map { relocatedJarFile })
} else {
  configurations.named("implementation").configure {
    extendsFrom(shadeConfiguration)
  }
}

/**
 * A task to download R8 from the Google servers
 * Ideally, we should use a maven repository instead. Maven repositories usually give more
 * immutability guarantees as well as easier caching. But `com.android.tools:r8:8.5.35` is
 * minified and doesn't expose `SwissArmKnife`.
 *
 * On the bright side, using a commit from main allows easier updating/testing.
 */
abstract class DownloadR8Task: DefaultTask() {
  @get:Input
  abstract val sha1: Property<String>

  @get:OutputFile
  abstract val outputFile: RegularFileProperty

  @TaskAction
  fun taskAction() {
    if (outputFile.get().asFile.exists()) {
      return
    }

    val url = "https://storage.googleapis.com/r8-releases/raw/main/${sha1.get()}/r8.jar"
    val request = Request.Builder()
        .get()
        .url(url)
        .build()

    OkHttpClient().newCall(request).execute().use { response ->
      check(response.isSuccessful) {
        "Cannot download $url (code=${response.code}): ${response.body?.string()}"
      }

      outputFile.get().asFile.outputStream().sink().buffer().use {
        it.writeAll(response.body!!.source())
      }
    }
  }
}

fun replaceOutgoingJar2(newJar: Any) {
  project.configurations.configureEach {
    outgoing {
      val removed = artifacts.removeIf {
        it.name == "apollo-gradle-plugin" && it.type == "jar" && it.classifier.isNullOrEmpty()
      }
      if (removed) {

        artifact(newJar) {
          // Pom and maven consumers do not like the `-all` or `-shadowed` classifiers
          classifier = ""
        }
      }
    }
  }
}

gradlePlugin {
  website.set("https://github.com/apollographql/apollo-kotlin")
  vcsUrl.set("https://github.com/apollographql/apollo-kotlin")

  plugins {
    create("apolloGradlePlugin") {
      id = "com.apollographql.apollo"
      displayName = "Apollo Kotlin GraphQL client plugin."
      description = "Automatically generates typesafe java and kotlin models from your GraphQL files."
      implementationClass = "com.apollographql.apollo.gradle.internal.ApolloPlugin"
      tags.set(listOf("graphql", "apollo"))
    }
  }
}

/**
 * This is so that the plugin marker pom contains a <scm> tag
 * It was recommended by the Gradle support team.
 */
configure<PublishingExtension> {
  publications.configureEach {
    if (name == "apolloGradlePluginPluginMarkerMaven") {
      this as MavenPublication
      pom {
        scm {
          url.set(findProperty("POM_SCM_URL") as String?)
          connection.set(findProperty("POM_SCM_CONNECTION") as String?)
          developerConnection.set(findProperty("POM_SCM_DEV_CONNECTION") as String?)
        }
      }
    }
  }
}

tasks.register("cleanStaleTestProjects") {
  /**
   * Remove stale testProject directories
   */
  val buildFiles = layout.buildDirectory.asFile.get().listFiles()
  doFirst {
    buildFiles?.forEach {
      if (it.isDirectory && it.name.startsWith("testProject")) {
        it.deleteRecursively()
      }
    }
  }
}

tasks.withType<Test> {
  dependsOn(":apollo-annotations:publishAllPublicationsToPluginTestRepository")
  dependsOn(":apollo-api:publishAllPublicationsToPluginTestRepository")
  dependsOn(":apollo-ast:publishAllPublicationsToPluginTestRepository")
  dependsOn(":apollo-normalized-cache-api:publishAllPublicationsToPluginTestRepository")
  dependsOn(":apollo-mpp-utils:publishAllPublicationsToPluginTestRepository")
  dependsOn(":apollo-compiler:publishAllPublicationsToPluginTestRepository")
  dependsOn(":apollo-gradle-plugin-external:publishAllPublicationsToPluginTestRepository")
  dependsOn(":apollo-tooling:publishAllPublicationsToPluginTestRepository")
  dependsOn("publishAllPublicationsToPluginTestRepository")

  dependsOn("cleanStaleTestProjects")

  addRelativeInput("testFiles", "testFiles")
  addRelativeInput("testProjects", "testProjects")

  maxParallelForks = (Runtime.getRuntime().availableProcessors() / 2).takeIf { it > 0 } ?: 1
}

val allTests = tasks.create("allTests")
tasks.check {
  dependsOn(allTests)
}

fun createTests(javaVersion: Int) {
  val sourceSet = sourceSets.create("test-java$javaVersion")

  configurations[sourceSet.implementationConfigurationName].extendsFrom(configurations.testImplementation.get())
  dependencies.add(sourceSet.implementationConfigurationName, sourceSets.getByName("test").output)
  configurations[sourceSet.runtimeOnlyConfigurationName].extendsFrom(configurations.testRuntimeOnly.get())

  val task = tasks.register<Test>("testJava$javaVersion") {
    description = "Runs integration tests for Java $javaVersion."
    group = "verification"
    useJUnit()

    testClassesDirs = sourceSet.output.classesDirs
    classpath = configurations[sourceSet.runtimeClasspathConfigurationName] + sourceSet.output

    environment("APOLLO_RELOCATE_JAR", System.getenv("APOLLO_RELOCATE_JAR"))
    setTestToolchain(project, this, javaVersion)
  }

  allTests.dependsOn(task)
}

tasks.named("test").configure {
  // Disable the default tests, they are empty
  enabled = false
}

listOf(11, 17).forEach { javaVersion ->
  createTests(javaVersion)
}

tasks.register("acceptAndroidLicenses") {
  val source = rootProject.file("android-licenses/android-sdk-preview-license")
  val target = rootProject.file("${System.getenv("ANDROID_HOME")}/licenses/android-sdk-preview-license")
  doLast {
    source.copyTo(target, overwrite = true)
  }
}

tasks.named("testJava17").configure {
  dependsOn("acceptAndroidLicenses")
}
