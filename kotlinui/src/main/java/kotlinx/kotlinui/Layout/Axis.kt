package kotlinx.kotlinui

import kotlinx.serialization.*
import kotlinx.serialization.descriptors.*
import kotlinx.serialization.encoding.*
import java.lang.Exception

@Serializable(with = Axis::class)
enum class Axis : KSerializer<Axis> {
    horizontal, vertical;

    //: Codable
    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("Axis", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, value: Axis) {
        when (value) {
            Axis.horizontal -> encoder.encodeString("horizontal")
            Axis.vertical -> encoder.encodeString("vertical")
            else -> error("$value")
        }
    }

    override fun deserialize(decoder: Decoder): Axis =
        when (val value = decoder.decodeString()) {
            "horizontal" -> Axis.horizontal
            "vertical" -> Axis.vertical
            else -> error(value)
        }
}
