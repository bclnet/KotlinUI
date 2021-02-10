package kotlinx.kotlinui

import android.graphics.Rect
import android.util.SizeF
import kotlinx.ptype.PType
import kotlinx.serialization.*
import kotlinx.serialization.descriptors.*
import kotlinx.serialization.encoding.*

@Serializable(with = ScaledShape.Serializer::class)
data class ScaledShape<Content : Shape>(
    val shape: Content,
    val scale: SizeF,
    val anchor: UnitPoint
) : Shape {
    override fun path(rect: Rect): Path = shape.path(rect)
    override val body: View
        get() = error("Never")

    //: Codable
    internal class Serializer<Content : Shape> : KSerializer<ScaledShape<Content>> {
        val contentSerializer = PolymorphicSerializer(Any::class)

        override val descriptor: SerialDescriptor =
            buildClassSerialDescriptor(":ScaledShape") {
                element("shape", contentSerializer.descriptor)
                element("scale", SizeFSerializer.descriptor)
                element<UnitPoint>("anchor")
            }

        override fun serialize(encoder: Encoder, value: ScaledShape<Content>) =
            encoder.encodeStructure(descriptor) {
                encodeSerializableElement(descriptor, 0, contentSerializer, value.shape)
                encodeSerializableElement(descriptor, 1, SizeFSerializer, value.scale)
                encodeSerializableElement(descriptor, 2, UnitPoint.Serializer, value.anchor)
            }

        override fun deserialize(decoder: Decoder): ScaledShape<Content> =
            decoder.decodeStructure(descriptor) {
                lateinit var shape: Content
                lateinit var scale: SizeF
                lateinit var anchor: UnitPoint
                while (true) {
                    when (val index = decodeElementIndex(descriptor)) {
                        0 -> shape = decodeSerializableElement(descriptor, 0, contentSerializer) as Content
                        1 -> scale = decodeSerializableElement(descriptor, 1, SizeFSerializer)
                        2 -> anchor = decodeSerializableElement(descriptor, 2, UnitPoint.Serializer)
                        CompositeDecoder.DECODE_DONE -> break
                        else -> error("Unexpected index: $index")
                    }
                }
                ScaledShape(shape, scale, anchor)
            }
    }

    companion object {
        //: Register
        fun register() {
            PType.register<ScaledShape<Shape>>()
        }
    }
}

fun Shape.scale(size: Float, anchor: UnitPoint = UnitPoint.center): ScaledShape<Shape> =
    ScaledShape(this, SizeF(size, size), anchor)

fun Shape.scale(x: Float = 1f, y: Float = 1f, anchor: UnitPoint = UnitPoint.center): ScaledShape<Shape> =
    ScaledShape(this, SizeF(x, y), anchor)