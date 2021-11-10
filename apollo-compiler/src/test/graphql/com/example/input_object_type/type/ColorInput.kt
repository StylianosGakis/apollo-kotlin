// AUTO-GENERATED FILE. DO NOT MODIFY.
//
// This class was automatically generated by Apollo GraphQL plugin from the GraphQL queries it found.
// It should not be modified by hand.
//
package com.example.input_object_type.type

import com.apollographql.apollo.api.Input
import com.apollographql.apollo.api.InputType
import com.apollographql.apollo.api.internal.InputFieldMarshaller
import kotlin.Double
import kotlin.Int
import kotlin.Suppress

/**
 * The input object sent when passing in a color
 */
@Suppress("NAME_SHADOWING", "UNUSED_ANONYMOUS_PARAMETER", "LocalVariableName",
    "RemoveExplicitTypeArguments", "NestedLambdaShadowedImplicitParameter")
data class ColorInput(
  /**
   * Red color
   */
  val red: Int = 1,
  /**
   * Green color
   */
  val green: Input<Double> = Input.optional(0.0),
  /**
   * Blue color
   */
  val blue: Double = 1.5,
  /**
   * for test purpose only
   */
  val enumWithDefaultValue: Input<Episode> = Input.optional(Episode.safeValueOf("new")),
  /**
   * Circle ref to review input
   */
  val reviewRefInput: Input<ReviewRefInput> = Input.absent()
) : InputType {
  override fun marshaller(): InputFieldMarshaller = InputFieldMarshaller.invoke { writer ->
    writer.writeInt("red", this@ColorInput.red)
    if (this@ColorInput.green.defined) {
      writer.writeDouble("green", this@ColorInput.green.value)
    }
    writer.writeDouble("blue", this@ColorInput.blue)
    if (this@ColorInput.enumWithDefaultValue.defined) {
      writer.writeString("enumWithDefaultValue",
          this@ColorInput.enumWithDefaultValue.value?.rawValue)
    }
    if (this@ColorInput.reviewRefInput.defined) {
      writer.writeObject("reviewRefInput", this@ColorInput.reviewRefInput.value?.marshaller())
    }
  }
}