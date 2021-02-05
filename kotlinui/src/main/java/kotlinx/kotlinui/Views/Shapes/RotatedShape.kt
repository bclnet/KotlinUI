package kotlinx.kotlinui

import android.graphics.Rect
import android.util.SizeF
import kotlinx.ptype.PType
import kotlinx.serialization.*
import kotlinx.serialization.descriptors.*
import kotlinx.serialization.encoding.*

@Serializable(with = RotatedShape.Serializer::class)
data class RotatedShape<Content : Shape>(
    val shape: Content,
    val angle: Angle,
    val anchor: UnitPoint
) : ViewModifier, InsettableShape {
    override fun path(rect: Rect): Path = error("Never")
    override fun inset(by: Float): View = error("Check") //modifier(_Inset(by))

    override val body: View
        get() = error("Never")

    //: Codable
    internal class Serializer<Content : Shape> : KSerializer<RotatedShape<Content>> {
        val contentSerializer = PolymorphicSerializer(Any::class)

        override val descriptor: SerialDescriptor =
            buildClassSerialDescriptor(":RotatedShape") {
                element("shape", contentSerializer.descriptor)
                element<Angle>("angle")
                element<UnitPoint>("anchor")
            }

        override fun serialize(encoder: Encoder, value: RotatedShape<Content>) =
            encoder.encodeStructure(descriptor) {
                encodeSerializableElement(descriptor, 0, contentSerializer, value.shape)
                encodeSerializableElement(descriptor, 1, Angle.Serializer, value.angle)
                encodeSerializableElement(descriptor, 2, UnitPoint.Serializer, value.anchor)
            }

        override fun deserialize(decoder: Decoder): RotatedShape<Content> =
            decoder.decodeStructure(descriptor) {
                lateinit var shape: Content
                lateinit var angle: Angle
                lateinit var anchor: UnitPoint
                while (true) {
                    when (val index = decodeElementIndex(descriptor)) {
                        0 -> shape = decodeSerializableElement(descriptor, 0, contentSerializer) as Content
                        1 -> angle = decodeSerializableElement(descriptor, 1, Angle.Serializer)
                        2 -> anchor = decodeSerializableElement(descriptor, 2, UnitPoint.Serializer)
                        CompositeDecoder.DECODE_DONE -> break
                        else -> error("Unexpected index: $index")
                    }
                }
                RotatedShape(shape, angle, anchor)
            }
    }

    companion object {
        //: Register
        fun register() {
            PType.register<RotatedShape<Shape>>()
        }
    }
}

fun Shape.rotation(angle: Angle, anchor: UnitPoint = UnitPoint.center): View =
    modifier(RotatedShape(this, angle, anchor))