package kotlinx.kotlinui

import kotlinx.ptype.PType
import kotlinx.serialization.*
import kotlinx.serialization.descriptors.*
import kotlinx.serialization.encoding.*

@Serializable(with = LinearGradient.Serializer::class)
@SerialName(":LinearGradient")
data class LinearGradient(
    val gradient: Gradient,
    val startPoint: UnitPoint,
    val endPoint: UnitPoint
) : ShapeStyle, View, IAnyView {
    override val anyView: AnyView
        get() = AnyView(this)

    override val body: Never
        get() = error("Never")

    //: Codable
    internal object Serializer : KSerializer<LinearGradient> {
        override val descriptor: SerialDescriptor =
            buildClassSerialDescriptor("LinearGradient") {
                element<Gradient>("gradient")
                element<UnitPoint>("startPoint")
                element<UnitPoint>("endPoint")
            }

        override fun serialize(encoder: Encoder, value: LinearGradient) =
            encoder.encodeStructure(descriptor) {
                encodeSerializableElement(descriptor, 0, Gradient.Serializer, value.gradient)
                encodeSerializableElement(descriptor, 1, UnitPoint.Serializer, value.startPoint)
                encodeSerializableElement(descriptor, 2, UnitPoint.Serializer, value.endPoint)
            }

        override fun deserialize(decoder: Decoder): LinearGradient =
            decoder.decodeStructure(descriptor) {
                lateinit var gradient: Gradient
                lateinit var startPoint: UnitPoint
                lateinit var endPoint: UnitPoint
                while (true) {
                    when (val index = decodeElementIndex(descriptor)) {
                        0 -> gradient = decodeSerializableElement(descriptor, 0, Gradient.Serializer)
                        1 -> startPoint = decodeSerializableElement(descriptor, 1, UnitPoint.Serializer)
                        2 -> endPoint = decodeSerializableElement(descriptor, 2, UnitPoint.Serializer)
                        CompositeDecoder.DECODE_DONE -> break
                        else -> error("Unexpected index: $index")
                    }
                }
                LinearGradient(gradient, startPoint, endPoint)
            }
    }

    companion object {
        //: Register
        fun register() {
            PType.register<LinearGradient>()
        }
    }
}
