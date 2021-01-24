package kotlinx.kotlinui

import kotlinx.ptype.PType
import kotlinx.serialization.*
import kotlinx.serialization.descriptors.*
import kotlinx.serialization.encoding.*

@Serializable(with = _PositionLayout.Serializer::class)
class _PositionLayout : ViewModifier {
//    fun body(content: AnyView): AnyView { AnyView(content.modifier(self)) }

    //: Codable
    internal object Serializer : KSerializer<_PositionLayout> {
        override val descriptor: SerialDescriptor =
            buildClassSerialDescriptor("_PositionLayout") {
            }

        override fun serialize(encoder: Encoder, value: _PositionLayout) =
            encoder.encodeStructure(descriptor) {
                error("Not Implemented")
            }

        override fun deserialize(decoder: Decoder): _PositionLayout =
            decoder.decodeStructure(descriptor) {
                error("Not Implemented")
            }
    }

    companion object {
        //: Register
        fun register() {
            PType.register<_PositionLayout>()
        }
    }
}
