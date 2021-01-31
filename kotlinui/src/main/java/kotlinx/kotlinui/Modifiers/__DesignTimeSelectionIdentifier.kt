package kotlinx.kotlinui

import kotlinx.ptype.PType
import kotlinx.serialization.*
import kotlinx.serialization.descriptors.*
import kotlinx.serialization.encoding.*

@Serializable(with = __DesignTimeSelectionIdentifier.Serializer::class)
data class __DesignTimeSelectionIdentifier(
    val identifier: String
) {
    //: Codable
    internal object Serializer : KSerializer<__DesignTimeSelectionIdentifier> {
        override val descriptor: SerialDescriptor =
            PrimitiveSerialDescriptor("__DesignTimeSelectionIdentifier", PrimitiveKind.STRING)

        override fun serialize(encoder: Encoder, value: __DesignTimeSelectionIdentifier) =
            encoder.encodeString(value.identifier)

        override fun deserialize(decoder: Decoder): __DesignTimeSelectionIdentifier =
            __DesignTimeSelectionIdentifier(decoder.decodeString())
    }

    companion object {
        //: Register
        fun register() {
            PType.register<__DesignTimeSelectionIdentifier>()
        }
    }
}
