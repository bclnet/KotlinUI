package kotlinx.kotlinui

import kotlinx.ptype.*
import kotlinx.serialization.*
import kotlinx.serialization.descriptors.*
import kotlinx.serialization.encoding.*

@Serializable(with = _AppearanceActionModifier.Serializer::class)
class _AppearanceActionModifier(
    val appear: (() -> Unit)?,
    val disappear: (() -> Unit)?
) : ViewModifier {
//    fun body(content: AnyView): AnyView { AnyView(content.modifier(self)) }

    //: Codable
    internal object Serializer : KSerializer<_AppearanceActionModifier> {
        override val descriptor: SerialDescriptor =
            buildClassSerialDescriptor("_AppearanceActionModifier") {
                element<() -> Unit>("appear")
                element<() -> Unit>("disappear")
            }

        override fun serialize(encoder: Encoder, value: _AppearanceActionModifier) =
            encoder.encodeStructure(descriptor) {
                if (value.appear != null) encodeAction0Element(descriptor, 0, value.appear)
                if (value.disappear != null) encodeAction0Element(descriptor, 1, value.disappear)
            }

        override fun deserialize(decoder: Decoder): _AppearanceActionModifier =
            decoder.decodeStructure(descriptor) {
                var appear: (() -> Unit)? = null
                var disappear: (() -> Unit)? = null
                while (true) {
                    when (val index = decodeElementIndex(descriptor)) {
                        0 -> appear = decodeAction0Element(descriptor, 0)
                        1 -> disappear = decodeAction0Element(descriptor, 1)
                        CompositeDecoder.DECODE_DONE -> break
                        else -> error("Unexpected index: $index")
                    }
                }
                _AppearanceActionModifier(appear, disappear)
            }
    }

    companion object {
        //: Register
        fun register() {
            PType.register<_AppearanceActionModifier>()
        }
    }
}
