package kotlinx.kotlinui

import android.graphics.Rect
import android.util.SizeF
import kotlinx.ptype.PType
import kotlinx.serialization.*
import kotlinx.serialization.descriptors.*
import kotlinx.serialization.encoding.*

@Serializable(with = _StrokedShape.Serializer::class)
data class _StrokedShape<S : View>(
    val shape: S,
    val style: StrokeStyle
) : ViewModifier {
    //: Codable
    internal class Serializer<S : View> : KSerializer<_StrokedShape<S>> {
        val shapeSerializer = PolymorphicSerializer(Any::class)

        override val descriptor: SerialDescriptor =
            buildClassSerialDescriptor(":_StrokeShape") {
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
            PType.register<_StrokedShape<View>>()
        }
    }
}

fun <S : Shape> Shape.stroke(content: S, lineWidth: Float = 1f): View =
    modifier(_StrokedShape(content, StrokeStyle(lineWidth = lineWidth)))

fun <S : Shape> Shape.stroke(content: S, style: StrokeStyle): View =
    modifier(_StrokedShape(content, style))

fun <S : Shape> Shape.stroke(lineWidth: Float = 1f): View =
    modifier(_StrokedShape(this, StrokeStyle(lineWidth = lineWidth)))

fun <S : Shape> Shape.stroke(style: StrokeStyle): View =
    modifier(_StrokedShape(this, style))
