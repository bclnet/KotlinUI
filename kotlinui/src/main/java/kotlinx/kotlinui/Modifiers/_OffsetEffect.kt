package kotlinx.kotlinui

import android.util.SizeF
import kotlinx.ptype.PType
import kotlinx.serialization.*
import kotlinx.serialization.descriptors.*
import kotlinx.serialization.encoding.*

@Serializable(with = _OffsetEffect.Serializer::class)
class _OffsetEffect(
    val offset: SizeF,
) : ViewModifier {
//    fun body(content: AnyView): AnyView { AnyView(content.modifier(self)) }

    //: Codable
    internal object Serializer : KSerializer<_OffsetEffect> {
        override val descriptor: SerialDescriptor =
            buildClassSerialDescriptor("_OffsetEffect") {
                element<SizeF>("offset")
            }

        override fun serialize(encoder: Encoder, value: _OffsetEffect) =
            encoder.encodeStructure(descriptor) {
                encodeSerializableElement(descriptor, 0, SizeFSerializer, value.offset)
            }

        override fun deserialize(decoder: Decoder): _OffsetEffect =
            decoder.decodeStructure(descriptor) {
                lateinit var offset: SizeF
                while (true) {
                    when (val index = decodeElementIndex(descriptor)) {
                        0 -> offset = decodeSerializableElement(descriptor, 0, SizeFSerializer)
                        CompositeDecoder.DECODE_DONE -> break
                        else -> error("Unexpected index: $index")
                    }
                }
                _OffsetEffect(offset)
            }
    }

    companion object {
        //: Register
        fun register() {
            PType.register<_OffsetEffect>()
        }
    }
}

fun View.offset(x: Float = 0f, y: Float = 0f): View =
    modifier(_OffsetEffect(SizeF(x, y)))
