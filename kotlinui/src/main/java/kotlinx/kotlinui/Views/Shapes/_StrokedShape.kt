package kotlinx.kotlinui

import android.graphics.Rect
import android.util.SizeF
import kotlinx.ptype.PType
import kotlinx.serialization.*
import kotlinx.serialization.descriptors.*
import kotlinx.serialization.encoding.*

@Serializable(with = _StrokedShape.Serializer::class)
data class _StrokedShape<S : Shape>(
    val shape: S,
    val style: StrokeStyle
) : Shape {
    override fun path(rect: Rect): Path = shape.path(rect)
    override val body: View
        get() = error("Never")

    //: Codable
    internal class Serializer<S : Shape> : KSerializer<_StrokedShape<S>> {
        val shapeSerializer = PolymorphicSerializer(Any::class)

        override val descriptor: SerialDescriptor =
            buildClassSerialDescriptor(":_StrokedShape") {
                element("shape", shapeSerializer.descriptor)
                element<StrokeStyle>("style")
            }

        override fun serialize(encoder: Encoder, value: _StrokedShape<S>) =
            encoder.encodeStructure(descriptor) {
                encodeSerializableElement(descriptor, 0, shapeSerializer, value.shape)
                encodeSerializableElement(descriptor, 1, StrokeStyle.Serializer, value.style)
            }

        override fun deserialize(decoder: Decoder): _StrokedShape<S> =
            decoder.decodeStructure(descriptor) {
                lateinit var shape: S
                lateinit var style: StrokeStyle
                while (true) {
                    when (val index = decodeElementIndex(descriptor)) {
                        0 -> shape = decodeSerializableElement(descriptor, 0, shapeSerializer) as S
                        1 -> style = decodeSerializableElement(descriptor, 1, StrokeStyle.Serializer)
                        CompositeDecoder.DECODE_DONE -> break
                        else -> error("Unexpected index: $index")
                    }
                }
                _StrokedShape(shape, style)
            }
    }

    companion object {
        //: Register
        fun register() {
            PType.register<_StrokedShape<Shape>>()
        }
    }
}

fun <S : ShapeStyle> Shape.stroke(content: S, lineWidth: Float = 1f): View =
    _StrokedShape(content.makeView(), StrokeStyle(lineWidth = lineWidth))

fun <S : ShapeStyle> Shape.stroke(content: S, style: StrokeStyle): View =
    _StrokedShape(content.makeView(), style)

fun Shape.stroke(lineWidth: Float = 1f): Shape =
    _StrokedShape(this, StrokeStyle(lineWidth = lineWidth))

fun Shape.stroke(style: StrokeStyle): Shape =
    _StrokedShape(this, style)

fun <S : ShapeStyle> InsettableShape.strokeBorder(content: S, lineWidth: Float = 1f, antialiased: Boolean = true): View =
    _StrokedShape(content.makeView(), StrokeStyle(lineWidth = lineWidth / 2))

fun <S : ShapeStyle> InsettableShape.strokeBorder(content: S, style: StrokeStyle, antialiased: Boolean = true): View =
    _StrokedShape(content.makeView(), style)

fun InsettableShape.strokeBorder(lineWidth: Float = 1f, antialiased: Boolean = true): View =
    _StrokedShape(this, StrokeStyle(lineWidth = lineWidth))

fun InsettableShape.strokeBorder(style: StrokeStyle, antialiased: Boolean = true): View =
    _StrokedShape(this, style)