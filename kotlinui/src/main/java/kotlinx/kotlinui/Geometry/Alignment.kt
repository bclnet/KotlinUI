package kotlinx.kotlinui
// https://github.com/Kotlin/kotlinx.serialization/blob/master/docs/serializers.md

import kotlinx.serialization.*
import kotlinx.serialization.descriptors.*
import kotlinx.serialization.encoding.*

@Serializable(with = Alignment.Serializer::class)
data class Alignment(var horizontal: HorizontalAlignment, var vertical: VerticalAlignment) {
    companion object {
        var center = Alignment(HorizontalAlignment.center, VerticalAlignment.center)
        var leading = Alignment(HorizontalAlignment.leading, VerticalAlignment.center)
        var trailing = Alignment(HorizontalAlignment.trailing, VerticalAlignment.center)
        var top = Alignment(HorizontalAlignment.center, VerticalAlignment.top)
        var bottom = Alignment(HorizontalAlignment.center, VerticalAlignment.bottom)
        var topLeading = Alignment(HorizontalAlignment.leading, VerticalAlignment.top)
        var topTrailing = Alignment(HorizontalAlignment.trailing, VerticalAlignment.top)
        var bottomLeading = Alignment(HorizontalAlignment.leading, VerticalAlignment.bottom)
        var bottomTrailing = Alignment(HorizontalAlignment.trailing, VerticalAlignment.bottom)
    }

    //: Codable
    internal object Serializer : KSerializer<Alignment> {
        override val descriptor: SerialDescriptor =
            PrimitiveSerialDescriptor("Alignment", PrimitiveKind.STRING)

        override fun serialize(encoder: Encoder, value: Alignment) =
            encoder.encodeString(
                when (value) {
                    center -> "center"
                    leading -> "leading"
                    trailing -> "trailing"
                    top -> "top"
                    bottom -> "bottom"
                    topLeading -> "topLeading"
                    topTrailing -> "topTrailing"
                    bottomLeading -> "bottomLeading"
                    bottomTrailing -> "bottomTrailing"
                    else -> error("$value")
                }
            )

        override fun deserialize(decoder: Decoder): Alignment =
            when (val value = decoder.decodeString()) {
                "center" -> center
                "leading" -> leading
                "trailing" -> trailing
                "top" -> top
                "bottom" -> bottom
                "topLeading" -> topLeading
                "topTrailing" -> topTrailing
                "bottomLeading" -> bottomLeading
                "bottomTrailing" -> bottomTrailing
                else -> error(value)
            }
    }
}

@Serializable(with = HorizontalAlignment.Serializer::class)
enum class HorizontalAlignment {
    leading, center, trailing;

    //: Codable
    internal object Serializer : KSerializer<HorizontalAlignment> {
        override val descriptor: SerialDescriptor =
            PrimitiveSerialDescriptor("HorizontalAlignment", PrimitiveKind.STRING)

        override fun serialize(encoder: Encoder, value: HorizontalAlignment) =
            encoder.encodeString(
                when (value) {
                    leading -> "leading"
                    center -> "center"
                    trailing -> "trailing"
                }
            )

        override fun deserialize(decoder: Decoder): HorizontalAlignment =
            when (val value = decoder.decodeString()) {
                "leading" -> leading
                "center" -> center
                "trailing" -> trailing
                else -> error(value)
            }
    }
}

@Serializable(with = VerticalAlignment.Serializer::class)
enum class VerticalAlignment {
    top, center, bottom, firstTextBaseline, lastTextBaseline;

    //: Codable
    internal object Serializer : KSerializer<VerticalAlignment> {
        override val descriptor: SerialDescriptor =
            PrimitiveSerialDescriptor("VerticalAlignment", PrimitiveKind.STRING)

        override fun serialize(encoder: Encoder, value: VerticalAlignment) =
            encoder.encodeString(
                when (value) {
                    top -> "top"
                    center -> "center"
                    bottom -> "bottom"
                    firstTextBaseline -> "firstTextBaseline"
                    lastTextBaseline -> "lastTextBaseline"
                }
            )

        override fun deserialize(decoder: Decoder): VerticalAlignment =
            when (val value = decoder.decodeString()) {
                "top" -> top
                "center" -> center
                "bottom" -> bottom
                "firstTextBaseline" -> firstTextBaseline
                "lastTextBaseline" -> lastTextBaseline
                else -> error(value)
            }
    }
}

