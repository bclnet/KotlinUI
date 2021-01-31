package kotlinx.kotlinui

import kotlinx.ptype.PType
import kotlinx.serialization.*
import kotlinx.serialization.descriptors.*
import kotlinx.serialization.encoding.*

@Serializable(with = _FrameLayout.Serializer::class)
data class _FrameLayout(
    val width: Float?,
    val height: Float?,
    val alignment: Alignment,
) : ViewModifier {
    //: Codable
    internal object Serializer : KSerializer<_FrameLayout> {
        override val descriptor: SerialDescriptor =
            buildClassSerialDescriptor("_FrameLayout") {
                element<Float>("width")
                element<Float>("height")
                element<Alignment>("alignment")
            }

        override fun serialize(encoder: Encoder, value: _FrameLayout) =
            encoder.encodeStructure(descriptor) {
                if (value.width != null) encodeFloatElement(descriptor, 0, value.width)
                if (value.height != null) encodeFloatElement(descriptor, 1, value.height)
                if (value.alignment != Alignment.center) encodeSerializableElement(descriptor, 2, Alignment.Serializer, value.alignment)
            }

        override fun deserialize(decoder: Decoder): _FrameLayout =
            decoder.decodeStructure(descriptor) {
                var width: Float? = null
                var height: Float? = null
                var alignment: Alignment = Alignment.center
                while (true) {
                    when (val index = decodeElementIndex(descriptor)) {
                        0 -> width = decodeFloatElement(descriptor, 0)
                        1 -> height = decodeFloatElement(descriptor, 1)
                        2 -> alignment = decodeSerializableElement(descriptor, 2, Alignment.Serializer)
                        CompositeDecoder.DECODE_DONE -> break
                        else -> error("Unexpected index: $index")
                    }
                }
                _FrameLayout(width, height, alignment)
            }
    }

    companion object {
        //: Register
        fun register() {
            PType.register<_FrameLayout>()
        }
    }
}

fun View.frame(width: Float? = null, height: Float? = null, alignment: Alignment = Alignment.center): View =
    modifier(_FrameLayout(width, height, alignment))
