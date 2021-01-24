package kotlinx.kotlinui

import kotlinx.ptype.PType
import kotlinx.serialization.*
import kotlinx.serialization.descriptors.*
import kotlinx.serialization.encoding.*

@Serializable(with = RadialGradient.Serializer::class)
class RadialGradient(
    val gradient: Gradient,
    val center: UnitPoint,
    val startRadius: Float,
    val endRadius: Float,
) : ShapeStyle, View, IAnyView {
    override val anyView: AnyView
        get() = AnyView(this)

    override val body: Never
        get() = error("Never")

    //: Codable
    internal object Serializer : KSerializer<RadialGradient> {
        override val descriptor: SerialDescriptor =
            buildClassSerialDescriptor("RadialGradient") {
                element<Gradient>("gradient")
                element<UnitPoint>("center")
                element<Float>("startRadius")
                element<Float>("endRadius")
            }

        override fun serialize(encoder: Encoder, value: RadialGradient) =
            encoder.encodeStructure(descriptor) {
                encodeSerializableElement(descriptor, 0, Gradient.Serializer, value.gradient)
                encodeSerializableElement(descriptor, 1, UnitPoint.Serializer, value.center)
                encodeFloatElement(descriptor, 2, value.startRadius)
                encodeFloatElement(descriptor, 3, value.endRadius)
            }

        override fun deserialize(decoder: Decoder): RadialGradient =
            decoder.decodeStructure(descriptor) {
                lateinit var gradient: Gradient
                lateinit var center: UnitPoint
                var startRadius: Float = 0f
                var endRadius: Float = 0f
                while (true) {
                    when (val index = decodeElementIndex(descriptor)) {
                        0 -> gradient = decodeSerializableElement(descriptor, 0, Gradient.Serializer)
                        1 -> center = decodeSerializableElement(descriptor, 1, UnitPoint.Serializer)
                        2 -> startRadius = decodeFloatElement(descriptor, 2)
                        3 -> endRadius = decodeFloatElement(descriptor, 3)
                        CompositeDecoder.DECODE_DONE -> break
                        else -> error("Unexpected index: $index")
                    }
                }
                RadialGradient(gradient, center, startRadius, endRadius)
            }
    }

    companion object {
        //: Register
        fun register() {
            PType.register<RadialGradient>()
        }
    }
}
