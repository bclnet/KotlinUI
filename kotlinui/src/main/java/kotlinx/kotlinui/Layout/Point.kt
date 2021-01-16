package kotlinx.kotlinui

import kotlinx.serialization.*
import kotlinx.serialization.descriptors.*
import kotlinx.serialization.encoding.*

@Serializable(with = PointSerializer::class)
data class Point(var x: Int, var y: Int) {
    companion object {
        var zero = Point(0, 0)
    }
}

internal object PointSerializer : KSerializer<Point> {
    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("Point", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, value: Point) {
        when (value) {
            else -> error("$value")
        }
    }

    override fun deserialize(decoder: Decoder): Point =
        when (val value = decoder.decodeString()) {
            else -> error(value)
        }
}