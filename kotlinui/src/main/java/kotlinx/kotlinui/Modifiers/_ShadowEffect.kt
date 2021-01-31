package kotlinx.kotlinui

import android.util.SizeF
import kotlinx.ptype.PType
import kotlinx.serialization.*
import kotlinx.serialization.descriptors.*
import kotlinx.serialization.encoding.*

@Serializable(with = _ShadowEffect.Serializer::class)
data class _ShadowEffect(
    val color: Color,
    val radius: Float,
    val offset: SizeF
) : ViewModifier {
    //: Codable
    internal object Serializer : KSerializer<_ShadowEffect> {
        override val descriptor: SerialDescriptor =
            buildClassSerialDescriptor("_ShadowEffect") {
                element("color", Color.Serializer.descriptor)
                element<Float>("radius")
                element("offset", SizeFSerializer.descriptor)
            }

        override fun serialize(encoder: Encoder, value: _ShadowEffect) =
            encoder.encodeStructure(descriptor) {
                encodeSerializableElement(descriptor, 0, Color.Serializer, value.color)
                encodeFloatElement(descriptor, 1, value.radius)
                encodeSerializableElement(descriptor, 2, SizeFSerializer, value.offset)
            }

        override fun deserialize(decoder: Decoder): _ShadowEffect =
            decoder.decodeStructure(descriptor) {
                lateinit var color: Color
                var radius: Float = 0f
                lateinit var offset: SizeF
                while (true) {
                    when (val index = decodeElementIndex(descriptor)) {
                        0 -> color = decodeSerializableElement(descriptor, 0, Color.Serializer)
                        1 -> radius = decodeFloatElement(descriptor, 1)
                        2 -> offset = decodeSerializableElement(descriptor, 0, SizeFSerializer)
                        CompositeDecoder.DECODE_DONE -> break
                        else -> error("Unexpected index: $index")
                    }
                }
                _ShadowEffect(color, radius, offset)
            }
    }

    companion object {
        //: Register
        fun register() {
            PType.register<_ShadowEffect>()
        }
    }
}

fun View.shadow(color: Color = Color(Color.RGBColorSpace.sRGBLinear, 0.0, 0.33), radius: Float, x: Float = 0f, y: Float = 0f): View =
    modifier(_ShadowEffect(color, radius, SizeF(x, y)))
