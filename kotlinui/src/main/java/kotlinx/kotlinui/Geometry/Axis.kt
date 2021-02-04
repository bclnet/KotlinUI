package kotlinx.kotlinui

import kotlinx.serialization.*
import kotlinx.serialization.descriptors.*
import kotlinx.serialization.encoding.*
import java.util.*

@Serializable(with = Axis.Serializer::class)
enum class Axis {
    horizontal, vertical;

    //: Codable
    internal object Serializer : KSerializer<Axis> {
        override val descriptor: SerialDescriptor =
            PrimitiveSerialDescriptor(":Axis", PrimitiveKind.STRING)

        override fun serialize(encoder: Encoder, value: Axis) =
            encoder.encodeString(
                when (value) {
                    horizontal -> "horizontal"
                    vertical -> "vertical"
                }
            )

        override fun deserialize(decoder: Decoder): Axis =
            when (val value = decoder.decodeString()) {
                "horizontal" -> horizontal
                "vertical" -> vertical
                else -> error(value)
            }
    }

    object Set {
        @Serializable(with = SetSerializer::class)
        var horizontal: EnumSet<Axis> = EnumSet.of(Axis.horizontal)

        @Serializable(with = SetSerializer::class)
        var vertical: EnumSet<Axis> = EnumSet.of(Axis.vertical)
    }

    //: Codable
    internal object SetSerializer : KSerializer<EnumSet<Axis>> {
        override val descriptor: SerialDescriptor =
            PrimitiveSerialDescriptor("Axis.Set", PrimitiveKind.STRING)

        override fun serialize(encoder: Encoder, value: EnumSet<Axis>) {
            encoder.encodeSerializableValue(serializer(), value.map {
                when (it) {
                    horizontal -> "horizontal"
                    vertical -> "vertical"
                }
            }.toList())
        }

        override fun deserialize(decoder: Decoder): EnumSet<Axis> =
            EnumSet.copyOf(decoder.decodeSerializableValue(serializer<Array<String>>()).map {
                when (it) {
                    "horizontal" -> horizontal
                    "vertical" -> vertical
                    else -> error(it)
                }
            }.toList())
    }
}
