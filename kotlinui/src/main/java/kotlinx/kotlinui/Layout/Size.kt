package kotlinx.kotlinui

import kotlinx.serialization.*
import kotlinx.serialization.descriptors.*
import kotlinx.serialization.encoding.*

@Serializable(with = SizeSerializer::class)
class Size(var width: Int, var height: Int) {
    companion object {
        var zero = Size(0, 0)
    }

    override fun equals(other: Any?): Boolean {
        if (other !is Size) return false
        val s = other as Size
        return width.equals(s.width) &&
            height.equals(s.height)
    }

    override fun hashCode(): Int {
        var result = width
        result = 31 * result + height
        return result
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