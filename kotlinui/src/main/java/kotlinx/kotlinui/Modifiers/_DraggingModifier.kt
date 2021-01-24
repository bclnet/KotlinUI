package kotlinx.kotlinui

import kotlinx.ptype.PType
import kotlinx.serialization.*
import kotlinx.serialization.descriptors.*
import kotlinx.serialization.encoding.*

@Serializable(with = _DraggingModifier.Serializer::class)
class _DraggingModifier(
    val itemsForDragHandler: Any
) : ViewModifier {
//    fun body(content: AnyView): AnyView { fatalError("Not Supported") }

    //: Codable
    internal object Serializer : KSerializer<_DraggingModifier> {
        override val descriptor: SerialDescriptor =
            buildClassSerialDescriptor("_DraggingModifier") {
                element<String>("itemsForDragHandler")
            }

        override fun serialize(encoder: Encoder, value: _DraggingModifier) =
            encoder.encodeStructure(descriptor) {
                error("")
            }

        override fun deserialize(decoder: Decoder): _DraggingModifier =
            decoder.decodeStructure(descriptor) {
                error("")
            }
    }

    companion object {
        //: Register
        fun register() {
            PType.register<_DraggingModifier>()
        }
    }
}
