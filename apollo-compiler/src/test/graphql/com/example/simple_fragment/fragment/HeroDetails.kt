// AUTO-GENERATED FILE. DO NOT MODIFY.
//
// This class was automatically generated by Apollo GraphQL plugin from the GraphQL queries it found.
// It should not be modified by hand.
//
package com.example.simple_fragment.fragment

import com.apollographql.apollo.api.GraphqlFragment
import com.apollographql.apollo.api.ResponseField
import com.apollographql.apollo.api.internal.ResponseFieldMapper
import com.apollographql.apollo.api.internal.ResponseFieldMarshaller
import com.apollographql.apollo.api.internal.ResponseReader
import kotlin.Array
import kotlin.String
import kotlin.Suppress

@Suppress("NAME_SHADOWING", "UNUSED_ANONYMOUS_PARAMETER", "LocalVariableName",
    "RemoveExplicitTypeArguments", "NestedLambdaShadowedImplicitParameter")
internal data class HeroDetails(
  val __typename: String = "Character",
  /**
   * The name of the character
   */
  val name: String,
  val fragments: Fragments
) : GraphqlFragment {
  override fun marshaller(): ResponseFieldMarshaller = ResponseFieldMarshaller { writer ->
    writer.writeString(RESPONSE_FIELDS[0], this@HeroDetails.__typename)
    writer.writeString(RESPONSE_FIELDS[1], this@HeroDetails.name)
    this@HeroDetails.fragments.marshaller().marshal(writer)
  }

  companion object {
    private val RESPONSE_FIELDS: Array<ResponseField> = arrayOf(
        ResponseField.forString("__typename", "__typename", null, false, null),
        ResponseField.forString("name", "name", null, false, null),
        ResponseField.forString("__typename", "__typename", null, false, null)
        )

    val FRAGMENT_DEFINITION: String = """
        |fragment HeroDetails on Character {
        |  __typename
        |  name
        |  ... HumanDetails
        |}
        """.trimMargin()

    operator fun invoke(reader: ResponseReader): HeroDetails = reader.run {
      val __typename = readString(RESPONSE_FIELDS[0])
      val name = readString(RESPONSE_FIELDS[1])
      val fragments = Fragments(reader)
      HeroDetails(
        __typename = __typename,
        name = name,
        fragments = fragments
      )
    }

    @Suppress("FunctionName")
    fun Mapper(): ResponseFieldMapper<HeroDetails> = ResponseFieldMapper { invoke(it) }
  }

  internal data class Fragments(
    val humanDetails: HumanDetails?
  ) {
    fun marshaller(): ResponseFieldMarshaller = ResponseFieldMarshaller { writer ->
      writer.writeFragment(this@Fragments.humanDetails?.marshaller())
    }

    companion object {
      private val RESPONSE_FIELDS: Array<ResponseField> = arrayOf(
          ResponseField.forFragment("__typename", "__typename", listOf(
            ResponseField.Condition.typeCondition(arrayOf("Human"))
          ))
          )

      operator fun invoke(reader: ResponseReader): Fragments = reader.run {
        val humanDetails = readFragment<HumanDetails>(RESPONSE_FIELDS[0]) { reader ->
          HumanDetails(reader)
        }
        Fragments(
          humanDetails = humanDetails
        )
      }

      @Suppress("FunctionName")
      fun Mapper(): ResponseFieldMapper<Fragments> = ResponseFieldMapper { invoke(it) }
    }
  }
}
