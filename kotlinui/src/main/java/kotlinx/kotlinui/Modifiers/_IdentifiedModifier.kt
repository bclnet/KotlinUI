package kotlinx.kotlinui

import kotlinx.ptype.PType
import kotlinx.serialization.*
import kotlinx.serialization.descriptors.*
import kotlinx.serialization.encoding.*

@Serializable(with = _EnvironmentKeyWritingModifier.Serializer::class)
data class _IdentifiedModifier<Identifier>(
    val identifier: Identifier
) : ViewModifier {
//    fun body(content: AnyView): AnyView { AnyView(content.modifier(self)) }

    //: Codable
    internal class Serializer<Identifier> : KSerializer<_IdentifiedModifier<Identifier>> {
        val identifierSerializer = PolymorphicSerializer(Any::class)

        override val descriptor: SerialDescriptor =
            buildClassSerialDescriptor(":_IdentifiedModifier") {
                element("identifier", identifierSerializer.descriptor)
            }

        override fun serialize(encoder: Encoder, value: _IdentifiedModifier<Identifier>) =
            encoder.encodeStructure(descriptor) {
                encodeSerializableElement(descriptor, 1, identifierSerializer, value.identifier as Any)
            }

        override fun deserialize(decoder: Decoder): _IdentifiedModifier<Identifier> =
            decoder.decodeStructure(descriptor) {
                var identifier: Identifier? = null
                while (true) {
                    when (val index = decodeElementIndex(descriptor)) {
                        0 -> identifier = decodeSerializableElement(descriptor, 0, identifierSerializer) as Identifier
                        CompositeDecoder.DECODE_DONE -> break
                        else -> error("Unexpected index: $index")
                    }
                }
                _IdentifiedModifier(identifier!!)
            }
    }

    companion object {
        //: Register
        fun register() {
            PType.register<_IdentifiedModifier<__DesignTimeSelectionIdentifier>>()
        }
    }
}
