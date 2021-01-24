package kotlinx.kotlinui

import kotlinx.ptype.PType
import kotlinx.serialization.*
import kotlinx.serialization.descriptors.*
import kotlinx.serialization.encoding.*

@Serializable(with = _AccessibilityIgnoresInvertColorsViewModifier.Serializer::class)
class _AccessibilityIgnoresInvertColorsViewModifier(
    val active: Boolean
) : ViewModifier {
//    fun body(content: AnyView): AnyView { AnyView(content.modifier(self)) }

    //: Codable
    internal object Serializer : KSerializer<_AccessibilityIgnoresInvertColorsViewModifier> {
        override val descriptor: SerialDescriptor =
            buildClassSerialDescriptor("_AccessibilityIgnoresInvertColorsViewModifier") {
                element<Boolean>("active")
            }

        override fun serialize(encoder: Encoder, value: _AccessibilityIgnoresInvertColorsViewModifier) =
            encoder.encodeStructure(descriptor) {
                encodeBooleanElement(descriptor, 0, value.active)
            }

        override fun deserialize(decoder: Decoder): _AccessibilityIgnoresInvertColorsViewModifier =
            decoder.decodeStructure(descriptor) {
                var active: Boolean = false
                while (true) {
                    when (val index = decodeElementIndex(descriptor)) {
                        0 -> active = decodeBooleanElement(descriptor, 0)
                        CompositeDecoder.DECODE_DONE -> break
                        else -> error("Unexpected index: $index")
                    }
                }
                _AccessibilityIgnoresInvertColorsViewModifier(active)
            }
    }

    companion object {
        //: Register
        fun register() {
            PType.register<_AccessibilityIgnoresInvertColorsViewModifier>()
        }
    }
}
