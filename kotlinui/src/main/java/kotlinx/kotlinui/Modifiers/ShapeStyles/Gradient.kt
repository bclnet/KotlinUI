package kotlinx.kotlinui

import kotlinx.serialization.*
import kotlinx.serialization.descriptors.*
import kotlinx.serialization.encoding.*

@Serializable(with = Gradient.Serializer::class)
class Gradient(
    val stops: Array<Stop>
) {
    constructor(colors: Array<Color>) : this(colors.map { Stop(it, 0f) }.toTypedArray())

    @Serializable(with = Stop.Serializer::class)
    data class Stop(
        val color: Color,
        val location: Float
    ) {
        //: Codable
        internal object Serializer : KSerializer<Stop> {
            override val descriptor: SerialDescriptor =
                buildClassSerialDescriptor("Stop") {
                    element<Color>("color")
                    element<Float>("location")
                }

            override fun serialize(encoder: Encoder, value: Stop) =
                encoder.encodeStructure(descriptor) {
                    encodeSerializableElement(descriptor, 0, Color.Serializer, value.color)
                    encodeFloatElement(descriptor, 1, value.location)
                }

            override fun deserialize(decoder: Decoder): Stop =
                decoder.decodeStructure(descriptor) {
                    lateinit var color: Color
                    var location: Float = 0f
                    while (true) {
                        when (val index = decodeElementIndex(descriptor)) {
                            0 -> color = decodeSerializableElement(descriptor, 0, Color.Serializer)
                            1 -> location = decodeFloatElement(descriptor, 1)
                            CompositeDecoder.DECODE_DONE -> break
                            else -> error("Unexpected index: $index")
                        }
                    }
                    Stop(color, location)
                }
        }
    }

    //: Codable
    internal object Serializer : KSerializer<Gradient> {
        override val descriptor: SerialDescriptor =
            PrimitiveSerialDescriptor("Gradient", PrimitiveKind.STRING)

        override fun serialize(encoder: Encoder, value: Gradient) =
            encoder.encodeSerializableValue(serializer<Array<Stop>>(), value.stops)

        override fun deserialize(decoder: Decoder): Gradient =
            Gradient(decoder.decodeSerializableValue(serializer<Array<Stop>>()))
    }
}
