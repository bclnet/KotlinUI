package kotlinx.kotlinui

import android.graphics.Matrix
import android.graphics.Rect
import kotlinx.ptype.PType
import kotlinx.serialization.*
import kotlinx.serialization.descriptors.*
import kotlinx.serialization.encoding.*

@Serializable(with = TransformedShape.Serializer::class)
data class TransformedShape<Content : Shape>(
    val shape: Content,
    val transform: Matrix
) : Shape {
    override fun path(rect: Rect): Path = shape.path(rect)
    override val body: View
        get() = error("Never")

    //: Codable
    internal class Serializer<Content : Shape> : KSerializer<TransformedShape<Content>> {
        val contentSerializer = PolymorphicSerializer(Any::class)

        override val descriptor: SerialDescriptor =
            buildClassSerialDescriptor(":TransformedShape") {
                element("shape", contentSerializer.descriptor)
                element("transform", MatrixSerializer.descriptor)
            }

        override fun serialize(encoder: Encoder, value: TransformedShape<Content>) =
            encoder.encodeStructure(descriptor) {
                encodeSerializableElement(descriptor, 0, contentSerializer, value.shape)
                encodeSerializableElement(descriptor, 1, MatrixSerializer, value.transform)
            }

        override fun deserialize(decoder: Decoder): TransformedShape<Content> =
            decoder.decodeStructure(descriptor) {
                lateinit var shape: Content
                lateinit var transform: Matrix
                while (true) {
                    when (val index = decodeElementIndex(descriptor)) {
                        0 -> shape = decodeSerializableElement(descriptor, 0, contentSerializer) as Content
                        1 -> transform = decodeSerializableElement(descriptor, 1, MatrixSerializer)
                        CompositeDecoder.DECODE_DONE -> break
                        else -> error("Unexpected index: $index")
                    }
                }
                TransformedShape(shape, transform)
            }
    }

    companion object {
        //: Register
        fun register() {
            PType.register<TransformedShape<Shape>>()
        }
    }
}

fun Shape.transform(matrix: Matrix): TransformedShape<Shape> =
    TransformedShape(this, matrix)
