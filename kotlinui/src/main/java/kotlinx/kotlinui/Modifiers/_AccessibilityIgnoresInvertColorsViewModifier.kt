package kotlinx.kotlinui

import kotlinx.ptype.PType
import kotlinx.serialization.*
import kotlinx.serialization.descriptors.*
import kotlinx.serialization.encoding.*

@Serializable(with = _AccessibilityIgnoresInvertColorsViewModifier.Serializer::class)
data class _AccessibilityIgnoresInvertColorsViewModifier(
    val active: Boolean
) : ViewModifier {
//    fun body(content: AnyView): AnyView { AnyView(content.modifier(self)) }

    //: Codable
    internal object Serializer : KSerializer<_AccessibilityIgnoresInvertColorsViewModifier> {
        override val descriptor: SerialDescriptor =
            PrimitiveSerialDescriptor("_AccessibilityIgnoresInvertColorsViewModifier", PrimitiveKind.BOOLEAN)

        override fun serialize(encoder: Encoder, value: _AccessibilityIgnoresInvertColorsViewModifier) =
            encoder.encodeBoolean(value.active)

        override fun deserialize(decoder: Decoder): _AccessibilityIgnoresInvertColorsViewModifier =
            _AccessibilityIgnoresInvertColorsViewModifier(decoder.decodeBoolean())
    }

    companion object {
        //: Register
        fun register() {
            PType.register<_AccessibilityIgnoresInvertColorsViewModifier>()
        }
    }
}
