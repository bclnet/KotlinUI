package kotlinx.kotlinui

import android.graphics.Rect
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.buildClassSerialDescriptor
import kotlinx.serialization.descriptors.element
import kotlinx.serialization.encoding.*

interface Shape : Animatable, View {
    fun path(rect: Rect): Path
}

@Serializable(with = FillStyle.Serializer::class)
data class FillStyle(
    var isEOFilled: Boolean = true,
    var isAntialiased: Boolean = true
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
                if (value.isEOFilled) encodeBooleanElement(descriptor, 0, value.isEOFilled)
                if (value.isAntialiased) encodeBooleanElement(descriptor, 1, value.isAntialiased)
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
