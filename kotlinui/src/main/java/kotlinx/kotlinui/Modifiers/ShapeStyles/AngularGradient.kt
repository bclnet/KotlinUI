package kotlinx.kotlinui

import kotlinx.ptype.PType
import kotlinx.serialization.*
import kotlinx.serialization.descriptors.*
import kotlinx.serialization.encoding.*

@Serializable(with = AngularGradient.Serializer::class)
data class AngularGradient(
    val gradient: Gradient,
    val center: UnitPoint,
    val startAngle: Angle,
    val endAngle: Angle
) : ShapeStyle, View, IAnyView {
    override val anyView: AnyView
        get() = AnyView(this)

    override val body: Never
        get() = error("Never")

    //: Codable
    internal object Serializer : KSerializer<AngularGradient> {
        override val descriptor: SerialDescriptor =
            buildClassSerialDescriptor(":AngularGradient") {
                element<Gradient>("gradient")
                element<UnitPoint>("center")
                element<Angle>("startAngle")
                element<Angle>("endAngle")
            }

        override fun serialize(encoder: Encoder, value: AngularGradient) =
            encoder.encodeStructure(descriptor) {
                encodeSerializableElement(descriptor, 0, Gradient.Serializer, value.gradient)
                encodeSerializableElement(descriptor, 1, UnitPoint.Serializer, value.center)
                if (value.startAngle != Angle.zero) encodeSerializableElement(descriptor, 2, Angle.Serializer, value.startAngle)
                if (value.endAngle != Angle.zero) encodeSerializableElement(descriptor, 3, Angle.Serializer, value.endAngle)
            }

        override fun deserialize(decoder: Decoder): AngularGradient =
            decoder.decodeStructure(descriptor) {
                lateinit var gradient: Gradient
                lateinit var center: UnitPoint
                var startAngle: Angle = Angle.zero
                var endAngle: Angle = Angle.zero
                while (true) {
                    when (val index = decodeElementIndex(descriptor)) {
                        0 -> gradient = decodeSerializableElement(descriptor, 0, Gradient.Serializer)
                        1 -> center = decodeSerializableElement(descriptor, 1, UnitPoint.Serializer)
                        2 -> startAngle = decodeSerializableElement(descriptor, 2, Angle.Serializer)
                        3 -> endAngle = decodeSerializableElement(descriptor, 3, Angle.Serializer)
                        CompositeDecoder.DECODE_DONE -> break
                        else -> error("Unexpected index: $index")
                    }
                }
                AngularGradient(gradient, center, startAngle, endAngle)
            }
    }

    companion object {
        //: Register
        fun register() {
            PType.register<AngularGradient>()
        }
    }
}
