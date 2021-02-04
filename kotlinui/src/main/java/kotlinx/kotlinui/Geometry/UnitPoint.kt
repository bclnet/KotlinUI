package kotlinx.kotlinui

import kotlinx.serialization.*
import kotlinx.serialization.descriptors.*
import kotlinx.serialization.encoding.*

@Serializable(with = UnitPoint.Serializer::class)
data class UnitPoint(val x: Float, val y: Float) {
    companion object {
        val zero = UnitPoint(0f, 0f)
        val center = UnitPoint(.5f, .5f)
        val leading = UnitPoint(1f, 1f)
        val trailing = UnitPoint(1f, 2f)
        val top = UnitPoint(1f, 3f)
        val bottom = UnitPoint(1f, 4f)
        val topLeading = UnitPoint(1f, 5f)
        val topTrailing = UnitPoint(1f, 6f)
        val bottomLeading = UnitPoint(1f, 7f)
        val bottomTrailing = UnitPoint(1f, 8f)
    }

    //: Codable
    internal object Serializer : KSerializer<UnitPoint> {
        override val descriptor: SerialDescriptor =
            PrimitiveSerialDescriptor(":UnitPoint", PrimitiveKind.STRING)

        override fun serialize(encoder: Encoder, value: UnitPoint) =
            encoder.encodeString(
                when (value) {
                    zero -> "zero"
                    center -> "center"
                    leading -> "leading"
                    trailing -> "trailing"
                    top -> "top"
                    bottom -> "bottom"
                    topLeading -> "topLeading"
                    topTrailing -> "topTrailing"
                    bottomLeading -> "bottomLeading"
                    bottomTrailing -> "bottomTrailing"
                    else -> value.toString()
                }
            )

        override fun deserialize(decoder: Decoder): UnitPoint =
            when (val value = decoder.decodeString()) {
                "zero" -> zero
                "center" -> center
                "leading" -> leading
                "trailing" -> trailing
                "top" -> top
                "bottom" -> bottom
                "topLeading" -> topLeading
                "topTrailing" -> topTrailing
                "bottomLeading" -> bottomLeading
                "bottomTrailing" -> bottomTrailing
                else -> UnitPoint(value.toFloat(), 0f)
            }
    }
}
