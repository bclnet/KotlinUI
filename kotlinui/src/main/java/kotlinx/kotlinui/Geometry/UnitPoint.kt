package kotlinx.kotlinui

import kotlinx.serialization.*
import kotlinx.serialization.descriptors.*
import kotlinx.serialization.encoding.*

data class UnitPoint(val x: Float, val y: Float) {
    companion object {
        var zero = UnitPoint(0f, 0f)
        var center = UnitPoint(.5f, .5f)
        var leading = UnitPoint(1f, 1f)
        var trailing = UnitPoint(1f, 2f)
        var top = UnitPoint(1f, 3f)
        var bottom = UnitPoint(1f, 4f)
        var topLeading = UnitPoint(1f, 5f)
        var topTrailing = UnitPoint(1f, 6f)
        var bottomLeading = UnitPoint(1f, 7f)
        var bottomTrailing = UnitPoint(1f, 8f)
    }

    //: Codable
    internal object Serializer : KSerializer<UnitPoint> {
        override val descriptor: SerialDescriptor =
            PrimitiveSerialDescriptor("UnitPoint", PrimitiveKind.STRING)

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
