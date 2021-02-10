package kotlinx.kotlinui

import android.graphics.Rect
import android.util.SizeF
import kotlinx.ptype.PType
import kotlinx.serialization.*
import kotlinx.serialization.descriptors.*
import kotlinx.serialization.encoding.*

@Serializable(with = _FilledShape.Serializer::class)
data class _FilledShape<S : Shape>(
    val shape: S,
    val style: FillStyle
) : Shape {
    override fun path(rect: Rect): Path = shape.path(rect)
    override val body: View
        get() = error("Never")

    //: Codable
    internal class Serializer<S : Shape> : KSerializer<_FilledShape<S>> {
        val shapeSerializer = PolymorphicSerializer(Any::class)

        override val descriptor: SerialDescriptor =
            buildClassSerialDescriptor(":_FilledShape") {
                element("shape", shapeSerializer.descriptor)
                element<FillStyle>("style")
            }

        override fun serialize(encoder: Encoder, value: _FilledShape<S>) =
            encoder.encodeStructure(descriptor) {
                encodeSerializableElement(descriptor, 0, shapeSerializer, value.shape)
                encodeSerializableElement(descriptor, 1, FillStyle.Serializer, value.style)
            }

        override fun deserialize(decoder: Decoder): _FilledShape<S> =
            decoder.decodeStructure(descriptor) {
                lateinit var shape: S
                lateinit var style: FillStyle
                while (true) {
                    when (val index = decodeElementIndex(descriptor)) {
                        0 -> shape = decodeSerializableElement(descriptor, 0, shapeSerializer) as S
                        1 -> style = decodeSerializableElement(descriptor, 1, FillStyle.Serializer)
                        CompositeDecoder.DECODE_DONE -> break
                        else -> error("Unexpected index: $index")
                    }
                }
                _FilledShape(shape, style)
            }
    }

    companion object {
        //: Register
        fun register() {
            PType.register<_FilledShape<Shape>>()
        }
    }
}

fun <S : ShapeStyle> Shape.fill(content: S, style: FillStyle = FillStyle()): View =
    _FilledShape(content.makeView(), style)

fun Shape.fill(style: FillStyle = FillStyle()): View =
    _FilledShape(this, style)
