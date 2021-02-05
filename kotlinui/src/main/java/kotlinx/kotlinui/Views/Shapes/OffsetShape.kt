package kotlinx.kotlinui

import android.graphics.PointF
import android.graphics.Rect
import android.util.SizeF
import kotlinx.ptype.PType
import kotlinx.serialization.*
import kotlinx.serialization.descriptors.*
import kotlinx.serialization.encoding.*

@Serializable(with = OffsetShape.Serializer::class)
data class OffsetShape<Content : Shape>(
    val shape: Content,
    val offset: SizeF
) : ViewModifier, InsettableShape {
    override fun path(rect: Rect): Path = error("Never")
    override fun inset(by: Float): View = error("TEST") //modifier(_Inset(by))

    override val body: View
        get() = error("Never")

    //: Codable
    internal class Serializer<Content : Shape> : KSerializer<OffsetShape<Content>> {
        val contentSerializer = PolymorphicSerializer(Any::class)

        override val descriptor: SerialDescriptor =
            buildClassSerialDescriptor(":OffsetShape") {
                element("shape", contentSerializer.descriptor)
                element("offset", SizeFSerializer.descriptor)
            }

        override fun serialize(encoder: Encoder, value: OffsetShape<Content>) =
            encoder.encodeStructure(descriptor) {
                encodeSerializableElement(descriptor, 0, contentSerializer, value.shape)
                encodeSerializableElement(descriptor, 1, SizeFSerializer, value.offset)
            }

        override fun deserialize(decoder: Decoder): OffsetShape<Content> =
            decoder.decodeStructure(descriptor) {
                lateinit var shape: Content
                lateinit var offset: SizeF
                while (true) {
                    when (val index = decodeElementIndex(descriptor)) {
                        0 -> shape = decodeSerializableElement(descriptor, 0, contentSerializer) as Content
                        1 -> offset = decodeSerializableElement(descriptor, 1, SizeFSerializer)
                        CompositeDecoder.DECODE_DONE -> break
                        else -> error("Unexpected index: $index")
                    }
                }
                OffsetShape(shape, offset)
            }
    }

    companion object {
        //: Register
        fun register() {
            PType.register<OffsetShape<Shape>>()
        }
    }
}

fun Shape.offset(offset: SizeF): View =
    modifier(OffsetShape(this, offset))

fun Shape.offset(offset: PointF): View =
    modifier(OffsetShape(this, SizeF(offset.x, offset.y)))

fun Shape.offset(x: Float = 0f, y: Float = 0f): View =
    modifier(OffsetShape(this, SizeF(x, y)))