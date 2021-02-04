package kotlinx.kotlinui

import kotlinx.serialization.*
import kotlinx.serialization.descriptors.*
import kotlinx.serialization.encoding.*

@Serializable(with = Point.Serializer::class)
data class Point(var x: Int, var y: Int) {
    //: Codable
    internal object Serializer : KSerializer<Point> {
        override val descriptor: SerialDescriptor =
            buildClassSerialDescriptor(":Point") {}

        override fun serialize(encoder: Encoder, value: Point) =
            encoder.encodeSerializableValue(serializer<IntArray>(), arrayOf(value.x, value.y).toIntArray())

        override fun deserialize(decoder: Decoder): Point =
            decoder.decodeSerializableValue(serializer<IntArray>()).let { Point(it[0], it[1]) }
    }

    companion object {
        var zero = Point(0, 0)
    }
}