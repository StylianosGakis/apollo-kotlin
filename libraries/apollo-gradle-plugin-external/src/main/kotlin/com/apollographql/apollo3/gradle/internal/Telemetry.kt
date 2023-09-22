package com.apollographql.apollo3.gradle.internal

import com.apollographql.apollo3.gradle.api.androidExtension
import org.gradle.api.Project

internal fun getTelemetryData(project: Project, apolloExtension: DefaultApolloExtension) = DefaultTelemetryData(
    gradleVersion = project.gradle.gradleVersion,
    androidMinSdk = project.androidExtension?.minSdk,
    androidTargetSdk = project.androidExtension?.targetSdk,
    androidCompileSdk = project.androidExtension?.compileSdkVersion,
    androidAgpVersion = project.androidExtension?.pluginVersion,
    apolloServiceTelemetryData = apolloExtension.getServiceTelemetryData(),
    apolloGenerateSourcesDuringGradleSync = apolloExtension.generateSourcesDuringGradleSync.orNull,
    apolloLinkSqlite = apolloExtension.linkSqlite.orNull,
    apolloUseAntlr = apolloExtension.useAntlr.orNull,
    apolloServiceCount = apolloExtension.serviceCount,
)