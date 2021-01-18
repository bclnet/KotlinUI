package kotlinx.kotlinui

import kotlinx.serialization.*
import kotlinx.serialization.descriptors.*
import kotlinx.serialization.encoding.*

@Serializable(with = LocalizedStringKey.Serializer::class)
data class LocalizedStringKey(var key: String) {
    private val hasFormatting = false

    internal object Serializer : KSerializer<LocalizedStringKey> {
        override val descriptor: SerialDescriptor =
            PrimitiveSerialDescriptor("LocalizedStringKey", PrimitiveKind.STRING)

        override fun serialize(encoder: Encoder, value: LocalizedStringKey) =
            encoder.encodeString(value.key)

        override fun deserialize(decoder: Decoder): LocalizedStringKey =
            LocalizedStringKey(decoder.decodeString())
    }
}

