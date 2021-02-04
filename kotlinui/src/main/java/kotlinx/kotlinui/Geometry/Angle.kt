package kotlinx.kotlinui

import kotlinx.serialization.*
import kotlinx.serialization.descriptors.*
import kotlinx.serialization.encoding.*

@Serializable(with = Angle.Serializer::class)
data class Angle(
    var radians: Double = 0.0
) {
    enum class Init { radians, degrees }

    constructor(value: Double, init: Init = Init.radians) : this(
        when (init) {
            Init.radians -> value
            Init.degrees -> value * (Math.PI / 180.0)
        }
    )

    var degrees: Double
        get() = radians * (180.0 / Math.PI)
        set(newValue) {
            radians = newValue * (Math.PI / 180.0)
        }

    //: Codable
    internal object Serializer : KSerializer<Angle> {
        override val descriptor: SerialDescriptor =
            PrimitiveSerialDescriptor(":Angle", PrimitiveKind.DOUBLE)

        override fun serialize(encoder: Encoder, value: Angle) =
            encoder.encodeDouble(value.radians)

        override fun deserialize(decoder: Decoder): Angle =
            Angle(decoder.decodeDouble())
    }

    companion object {
        var zero = Angle(0.0)
        fun radians(radians: Double): Angle = Angle(radians)
        fun degrees(degrees: Double): Angle = Angle(degrees * (Math.PI / 180.0))
    }
}
