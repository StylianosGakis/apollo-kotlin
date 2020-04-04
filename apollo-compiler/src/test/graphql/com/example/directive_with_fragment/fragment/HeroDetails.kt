// AUTO-GENERATED FILE. DO NOT MODIFY.
//
// This class was automatically generated by Apollo GraphQL plugin from the GraphQL queries it found.
// It should not be modified by hand.
//
package com.example.directive_with_fragment.fragment

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
data class HeroDetails(
  val __typename: String = "Character",
  /**
   * The name of the character
   */
  val name: String
) : GraphqlFragment {
  override fun marshaller(): ResponseFieldMarshaller = ResponseFieldMarshaller { writer ->
    writer.writeString(RESPONSE_FIELDS[0], this@HeroDetails.__typename)
    writer.writeString(RESPONSE_FIELDS[1], this@HeroDetails.name)
  }

  companion object {
    private val RESPONSE_FIELDS: Array<ResponseField> = arrayOf(
        ResponseField.forString("__typename", "__typename", null, false, null),
        ResponseField.forString("name", "name", null, false, null)
        )

    val FRAGMENT_DEFINITION: String = """
        |fragment HeroDetails on Character {
        |  __typename
        |  name
        |}
        """.trimMargin()

    operator fun invoke(reader: ResponseReader): HeroDetails = reader.run {
      val __typename = readString(RESPONSE_FIELDS[0])
      val name = readString(RESPONSE_FIELDS[1])
      HeroDetails(
        __typename = __typename,
        name = name
      )
    }

    @Suppress("FunctionName")
    fun Mapper(): ResponseFieldMapper<HeroDetails> = ResponseFieldMapper { invoke(it) }
  }
}
