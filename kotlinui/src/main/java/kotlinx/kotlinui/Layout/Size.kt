package kotlinx.kotlinui

import kotlinx.serialization.*
import kotlinx.serialization.descriptors.*
import kotlinx.serialization.encoding.*

@Serializable(with = SizeSerializer::class)
data class Size(var width: Int, var height: Int) {
    companion object {
        var zero = Size(0, 0)
    }
}

internal object SizeSerializer : KSerializer<Size> {
    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("Size", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, value: Size) {
        when (value) {
            else -> error("$value")
        }
    }

    override fun deserialize(decoder: Decoder): Size =
        when (val value = decoder.decodeString()) {
            else -> error(value)
        }
}