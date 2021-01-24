package kotlinx.kotlinui

import kotlinx.ptype.PType
import kotlinx.serialization.*
import kotlinx.serialization.descriptors.*
import kotlinx.serialization.encoding.*

@Serializable(with = __DesignTimeSelectionIdentifier.Serializer::class)
class __DesignTimeSelectionIdentifier(
    val identifier: String
) {
    //: Codable
    internal object Serializer : KSerializer<__DesignTimeSelectionIdentifier> {
        override val descriptor: SerialDescriptor =
            buildClassSerialDescriptor("__DesignTimeSelectionIdentifier") {
                element<String>("identifier")
            }

        override fun serialize(encoder: Encoder, value: __DesignTimeSelectionIdentifier) =
            encoder.encodeStructure(descriptor) {
                encodeStringElement(descriptor, 0, value.identifier)
            }

        override fun deserialize(decoder: Decoder): __DesignTimeSelectionIdentifier =
            decoder.decodeStructure(descriptor) {
                lateinit var identifier: String
                while (true) {
                    when (val index = decodeElementIndex(descriptor)) {
                        0 -> identifier = decodeStringElement(descriptor, 0)
                        CompositeDecoder.DECODE_DONE -> break
                        else -> error("Unexpected index: $index")
                    }
                }
                __DesignTimeSelectionIdentifier(identifier)
            }
    }

    companion object {
        //: Register
        fun register() {
            PType.register<__DesignTimeSelectionIdentifier>()
        }
    }
}
