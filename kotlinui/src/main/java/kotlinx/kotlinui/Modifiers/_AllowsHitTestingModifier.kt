package kotlinx.kotlinui

import kotlinx.ptype.PType
import kotlinx.serialization.*
import kotlinx.serialization.descriptors.*
import kotlinx.serialization.encoding.*

@Serializable(with = _AllowsHitTestingModifier.Serializer::class)
data class _AllowsHitTestingModifier(
    val allowsHitTesting: Boolean
) : ViewModifier {
//    fun body(content: AnyView): AnyView { AnyView(content.modifier(self)) }

    //: Codable
    internal object Serializer : KSerializer<_AllowsHitTestingModifier> {
        override val descriptor: SerialDescriptor =
            buildClassSerialDescriptor(":_AllowsHitTestingModifier") {
                element<Boolean>("active")
            }

        override fun serialize(encoder: Encoder, value: _AllowsHitTestingModifier) =
            encoder.encodeStructure(descriptor) {
                encodeBooleanElement(descriptor, 0, value.allowsHitTesting)
            }

        override fun deserialize(decoder: Decoder): _AllowsHitTestingModifier =
            decoder.decodeStructure(descriptor) {
                var allowsHitTesting: Boolean = false
                while (true) {
                    when (val index = decodeElementIndex(descriptor)) {
                        0 -> allowsHitTesting = decodeBooleanElement(descriptor, 0)
                        CompositeDecoder.DECODE_DONE -> break
                        else -> error("Unexpected index: $index")
                    }
                }
                _AllowsHitTestingModifier(allowsHitTesting)
            }
    }

    companion object {
        //: Register
        fun register() {
            PType.register<_AllowsHitTestingModifier>()
        }
    }
}
