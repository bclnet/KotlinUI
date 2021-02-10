package kotlinx.kotlinui

import android.graphics.Rect
import android.util.SizeF
import kotlinx.ptype.PType
import kotlinx.serialization.*
import kotlinx.serialization.descriptors.*
import kotlinx.serialization.encoding.*

@Serializable(with = _TrimmedShape.Serializer::class)
data class _TrimmedShape<S : Shape>(
    val shape: S,
    val startFraction: Float,
    val endFraction: Float
) : Shape {
    override fun path(rect: Rect): Path = shape.path(rect)
    override val body: View
        get() = error("Never")

    //: Codable
    internal class Serializer<S : Shape> : KSerializer<_TrimmedShape<S>> {
        val shapeSerializer = PolymorphicSerializer(Any::class)

        override val descriptor: SerialDescriptor =
            buildClassSerialDescriptor(":_TrimmedShape") {
                element("shape", shapeSerializer.descriptor)
                element<Float>("startFraction")
                element<Float>("endFraction")
            }

        override fun serialize(encoder: Encoder, value: _TrimmedShape<S>) =
            encoder.encodeStructure(descriptor) {
                encodeSerializableElement(descriptor, 0, shapeSerializer, value.shape)
                encodeFloatElement(descriptor, 1, value.startFraction)
                encodeFloatElement(descriptor, 2, value.endFraction)
            }

        override fun deserialize(decoder: Decoder): _TrimmedShape<S> =
            decoder.decodeStructure(descriptor) {
                lateinit var shape: S
                var startFraction = 0f
                var endFraction = 0f
                while (true) {
                    when (val index = decodeElementIndex(descriptor)) {
                        0 -> shape = decodeSerializableElement(descriptor, 0, shapeSerializer) as S
                        1 -> startFraction = decodeFloatElement(descriptor, 1)
                        2 -> endFraction = decodeFloatElement(descriptor, 2)
                        CompositeDecoder.DECODE_DONE -> break
                        else -> error("Unexpected index: $index")
                    }
                }
                _TrimmedShape(shape, startFraction, endFraction)
            }
    }

    companion object {
        //: Register
        fun register() {
            PType.register<_TrimmedShape<Shape>>()
        }
    }
}

fun Shape.trim(startFraction: Float = 0f, endFraction: Float = 1f): View =
    _TrimmedShape(this, startFraction, endFraction)
