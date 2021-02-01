package kotlinx.kotlinui

import android.graphics.Rect
import android.util.SizeF
import kotlinx.serialization.*
import kotlinx.serialization.descriptors.*
import kotlinx.serialization.encoding.*

interface Shape : Animatable, View {
    fun path(rect: Rect): Path
}

@Serializable(with = CGLineCap.Serializer::class)
enum class CGLineCap {
    butt, round, square;

    //: Codable
    internal object Serializer : KSerializer<CGLineCap> {
        override val descriptor: SerialDescriptor =
            PrimitiveSerialDescriptor("CGLineCap", PrimitiveKind.STRING)

        override fun serialize(encoder: Encoder, value: CGLineCap) =
            encoder.encodeString(
                when (value) {
                    butt -> "butt"
                    round -> "round"
                    square -> "square"
                    else -> error("$value")
                }
            )

        override fun deserialize(decoder: Decoder): CGLineCap =
            when (val value = decoder.decodeString()) {
                "butt" -> butt
                "round" -> round
                "square" -> square
                else -> error(value)
            }
    }
}

@Serializable(with = CGLineJoin.Serializer::class)
enum class CGLineJoin {
    miter, round, bevel;

    //: Codable
    internal object Serializer : KSerializer<CGLineJoin> {
        override val descriptor: SerialDescriptor =
            PrimitiveSerialDescriptor("CGLineJoin", PrimitiveKind.STRING)

        override fun serialize(encoder: Encoder, value: CGLineJoin) =
            encoder.encodeString(
                when (value) {
                    miter -> "miter"
                    round -> "round"
                    bevel -> "bevel"
                    else -> error("$value")
                }
            )

        override fun deserialize(decoder: Decoder): CGLineJoin =
            when (val value = decoder.decodeString()) {
                "miter" -> miter
                "round" -> round
                "bevel" -> bevel
                else -> error(value)
            }
    }
}

@Serializable(with = FillStyle.Serializer::class)
data class FillStyle(
    val isEOFilled: Boolean = true,
    val isAntialiased: Boolean = true
) {
    //: Codable
    internal object Serializer : KSerializer<FillStyle> {
        override val descriptor: SerialDescriptor =
            buildClassSerialDescriptor("FillStyle") {
                element<Boolean>("isEOFilled")
                element<Boolean>("isAntialiased")
            }

        override fun serialize(encoder: Encoder, value: FillStyle) =
            encoder.encodeStructure(descriptor) {
                if (!value.isEOFilled) encodeBooleanElement(descriptor, 0, value.isEOFilled)
                if (!value.isAntialiased) encodeBooleanElement(descriptor, 1, value.isAntialiased)
            }

        override fun deserialize(decoder: Decoder): FillStyle =
            decoder.decodeStructure(descriptor) {
                var isEOFilled: Boolean = false
                var isAntialiased: Boolean = false
                while (true) {
                    when (val index = decodeElementIndex(descriptor)) {
                        0 -> isEOFilled = decodeBooleanElement(descriptor, 0)
                        1 -> isAntialiased = decodeBooleanElement(descriptor, 1)
                        CompositeDecoder.DECODE_DONE -> break
                        else -> error("Unexpected index: $index")
                    }
                }
                FillStyle(isEOFilled, isAntialiased)
            }
    }
}

data class FixedRoundedRect(
    val rect: Rect,
    val cornerSize: SizeF,
    val style: RoundedCornerStyle
)

@Serializable(with = RoundedCornerStyle.Serializer::class)
enum class RoundedCornerStyle {
    circular, continuous;

    //: Codable
    internal object Serializer : KSerializer<RoundedCornerStyle> {
        override val descriptor: SerialDescriptor =
            PrimitiveSerialDescriptor("RoundedCornerStyle", PrimitiveKind.STRING)

        override fun serialize(encoder: Encoder, value: RoundedCornerStyle) =
            encoder.encodeString(
                when (value) {
                    circular -> "circular"
                    continuous -> "continuous"
                    else -> error("$value")
                }
            )

        override fun deserialize(decoder: Decoder): RoundedCornerStyle =
            when (val value = decoder.decodeString()) {
                "circular" -> circular
                "continuous" -> continuous
                else -> error(value)
            }
    }
}

@Serializable(with = StrokeStyle.Serializer::class)
data class StrokeStyle(
    val lineWidth: Float = 1f,
    val lineCap: CGLineCap = CGLineCap.butt,
    val lineJoin: CGLineJoin = CGLineJoin.miter,
    val miterLimit: Float = 10f,
    val dash: FloatArray = FloatArray(0),
    val dashPhase: Float = 0f
) {
    override fun equals(other: Any?): Boolean {
        if (other !is StrokeStyle) return false
        return lineWidth == other.lineWidth &&
                lineCap == other.lineCap &&
                lineJoin == other.lineJoin &&
                miterLimit == other.miterLimit &&
                dash contentEquals other.dash &&
                dashPhase == other.dashPhase
    }

    override fun hashCode(): Int {
        var result = lineWidth.hashCode()
        result = 31 * result + lineCap.hashCode()
        result = 31 * result + lineJoin.hashCode()
        result = 31 * result + miterLimit.hashCode()
        result = 31 * result + dash.contentHashCode()
        result = 31 * result + dashPhase.hashCode()
        return result
    }

    //: Codable
    internal object Serializer : KSerializer<StrokeStyle> {
        override val descriptor: SerialDescriptor =
            buildClassSerialDescriptor("StrokeStyle") {
                element<Float>("lineWidth")
                element("lineCap", CGLineCap.Serializer.descriptor)
                element("lineJoin", CGLineJoin.Serializer.descriptor)
                element<Float>("miterLimit")
                element<FloatArray>("dash")
                element<Float>("dashPhase")
            }

        override fun serialize(encoder: Encoder, value: StrokeStyle) =
            encoder.encodeStructure(descriptor) {
                if (value.lineWidth != 1f) encodeFloatElement(descriptor, 0, value.lineWidth)
                if (value.lineCap != CGLineCap.butt) encodeSerializableElement(descriptor, 1, CGLineCap.Serializer, value.lineCap)
                if (value.lineJoin != CGLineJoin.miter) encodeSerializableElement(descriptor, 2, CGLineJoin.Serializer, value.lineJoin)
                if (value.miterLimit != 10f) encodeFloatElement(descriptor, 3, value.miterLimit)
                if (value.dash.isNotEmpty()) encodeSerializableElement(descriptor, 4, serializer<FloatArray>(), value.dash)
                if (value.dashPhase != 0f) encodeFloatElement(descriptor, 5, value.dashPhase)
            }

        override fun deserialize(decoder: Decoder): StrokeStyle =
            decoder.decodeStructure(descriptor) {
                var lineWidth = 1f
                var lineCap: CGLineCap = CGLineCap.butt
                var lineJoin: CGLineJoin = CGLineJoin.miter
                var miterLimit = 10f
                var dash: FloatArray = FloatArray(0)
                var dashPhase = 0f
                while (true) {
                    when (val index = decodeElementIndex(descriptor)) {
                        0 -> lineWidth = decodeFloatElement(descriptor, 0)
                        1 -> lineCap = decodeSerializableElement(descriptor, 1, CGLineCap.Serializer)
                        2 -> lineJoin = decodeSerializableElement(descriptor, 2, CGLineJoin.Serializer)
                        3 -> miterLimit = decodeFloatElement(descriptor, 3)
                        4 -> dash = decodeSerializableElement(descriptor, 4, serializer<FloatArray>())
                        5 -> dashPhase = decodeFloatElement(descriptor, 5)
                        CompositeDecoder.DECODE_DONE -> break
                        else -> error("Unexpected index: $index")
                    }
                }
                StrokeStyle(lineWidth, lineCap, lineJoin, miterLimit, dash, dashPhase)
            }
    }
}