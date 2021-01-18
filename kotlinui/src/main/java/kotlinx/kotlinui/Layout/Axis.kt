package kotlinx.kotlinui

import kotlinx.serialization.*
import kotlinx.serialization.descriptors.*
import kotlinx.serialization.encoding.*

@Serializable(with = Axis.Serializer::class)
enum class Axis {
    horizontal, vertical;

    //: Codable
    internal object Serializer : KSerializer<Axis> {
        override val descriptor: SerialDescriptor =
            PrimitiveSerialDescriptor("Axis", PrimitiveKind.STRING)

        override fun serialize(encoder: Encoder, value: Axis) {
            when (value) {
                Axis.horizontal -> encoder.encodeString("horizontal")
                Axis.vertical -> encoder.encodeString("vertical")
            }
        }

        override fun deserialize(decoder: Decoder): Axis =
            when (val value = decoder.decodeString()) {
                "horizontal" -> Axis.horizontal
                "vertical" -> Axis.vertical
                else -> error(value)
            }
    }
}
