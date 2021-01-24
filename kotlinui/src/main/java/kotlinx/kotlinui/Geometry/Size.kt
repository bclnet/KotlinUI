package kotlinx.kotlinui

import kotlinx.serialization.*
import kotlinx.serialization.descriptors.*
import kotlinx.serialization.encoding.*

@Serializable(with = Size.Serializer::class)
data class Size(var width: Int, var height: Int) {
    //: Codable
    internal object Serializer : KSerializer<Size> {
        override val descriptor: SerialDescriptor =
            PrimitiveSerialDescriptor("Size", PrimitiveKind.STRING)

        override fun serialize(encoder: Encoder, value: Size) =
            encoder.encodeSerializableValue(serializer<Array<Int>>(), arrayOf(value.width, value.height))

        override fun deserialize(decoder: Decoder): Size =
            decoder.decodeSerializableValue(serializer<Array<Int>>()).let {
                Size(it[0], it[1])
            }
    }

    companion object {
        var zero = Size(0, 0)
    }
}