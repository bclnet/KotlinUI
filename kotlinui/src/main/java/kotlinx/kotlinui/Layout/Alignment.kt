package kotlinx.kotlinui
// https://github.com/Kotlin/kotlinx.serialization/blob/master/docs/serializers.md

import kotlinx.serialization.*
import kotlinx.serialization.descriptors.*
import kotlinx.serialization.encoding.*

@Serializable(with = AlignmentSerializer::class)
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
}

internal object AlignmentSerializer : KSerializer<Alignment> {
    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("Alignment", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, value: Alignment) {
        when (value) {
            Alignment.center -> encoder.encodeString("center")
            Alignment.leading -> encoder.encodeString("leading")
            Alignment.trailing -> encoder.encodeString("trailing")
            Alignment.top -> encoder.encodeString("top")
            Alignment.bottom -> encoder.encodeString("bottom")
            Alignment.topLeading -> encoder.encodeString("topLeading")
            Alignment.topTrailing -> encoder.encodeString("topTrailing")
            Alignment.bottomLeading -> encoder.encodeString("bottomLeading")
            Alignment.bottomTrailing -> encoder.encodeString("bottomTrailing")
            else -> error("$value")
        }
    }

    override fun deserialize(decoder: Decoder): Alignment =
        when (val value = decoder.decodeString()) {
            "center" -> Alignment.center
            "leading" -> Alignment.leading
            "trailing" -> Alignment.trailing
            "top" -> Alignment.top
            "bottom" -> Alignment.bottom
            "topLeading" -> Alignment.topLeading
            "topTrailing" -> Alignment.topTrailing
            "bottomLeading" -> Alignment.bottomLeading
            "bottomTrailing" -> Alignment.bottomTrailing
            else -> error(value)
        }
}

@Serializable(with = HorizontalAlignmentSerializer::class)
enum class HorizontalAlignment {
    leading, center, trailing
}

internal object HorizontalAlignmentSerializer : KSerializer<HorizontalAlignment> {
    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("HorizontalAlignment", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, value: HorizontalAlignment) {
        when (value) {
            HorizontalAlignment.leading -> encoder.encodeString("leading")
            HorizontalAlignment.center -> encoder.encodeString("center")
            HorizontalAlignment.trailing -> encoder.encodeString("trailing")
        }
    }

    override fun deserialize(decoder: Decoder): HorizontalAlignment =
        when (val value = decoder.decodeString()) {
            "leading" -> HorizontalAlignment.leading
            "center" -> HorizontalAlignment.center
            "trailing" -> HorizontalAlignment.trailing
            else -> error(value)
        }
}

@Serializable(with = VerticalAlignmentSerializer::class)
enum class VerticalAlignment {
    top, center, bottom, firstTextBaseline, lastTextBaseline
}

internal object VerticalAlignmentSerializer : KSerializer<VerticalAlignment> {
    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("VerticalAlignment", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, value: VerticalAlignment) {
        when (value) {
            VerticalAlignment.top -> encoder.encodeString("top")
            VerticalAlignment.center -> encoder.encodeString("center")
            VerticalAlignment.bottom -> encoder.encodeString("bottom")
            VerticalAlignment.firstTextBaseline -> encoder.encodeString("firstTextBaseline")
            VerticalAlignment.lastTextBaseline -> encoder.encodeString("lastTextBaseline")
        }
    }

    override fun deserialize(decoder: Decoder): VerticalAlignment =
        when (val value = decoder.decodeString()) {
            "top" -> VerticalAlignment.top
            "center" -> VerticalAlignment.center
            "bottom" -> VerticalAlignment.bottom
            "firstTextBaseline" -> VerticalAlignment.firstTextBaseline
            "lastTextBaseline" -> VerticalAlignment.lastTextBaseline
            else -> error(value)
        }
}
