package kotlinx.kotlinui

import android.graphics.Rect
import android.util.SizeF
import kotlinx.ptype.PType
import kotlinx.serialization.*
import kotlinx.serialization.descriptors.*
import kotlinx.serialization.encoding.*

@Serializable(with = _SizedShape.Serializer::class)
data class _SizedShape<S : View>(
    val shape: S,
    val size: SizeF
) : ViewModifier {
    //: Codable
    internal class Serializer<S : View> : KSerializer<_SizedShape<S>> {
        val shapeSerializer = PolymorphicSerializer(Any::class)

        override val descriptor: SerialDescriptor =
            buildClassSerialDescriptor(":_SizedShape") {
                element("shape", shapeSerializer.descriptor)
                element("size", SizeFSerializer.descriptor)
            }

        override fun serialize(encoder: Encoder, value: _SizedShape<S>) =
            encoder.encodeStructure(descriptor) {
                encodeSerializableElement(descriptor, 0, shapeSerializer, value.shape)
                encodeSerializableElement(descriptor, 1, SizeFSerializer, value.size)
            }

        override fun deserialize(decoder: Decoder): _SizedShape<S> =
            decoder.decodeStructure(descriptor) {
                lateinit var shape: S
                lateinit var size: SizeF
                while (true) {
                    when (val index = decodeElementIndex(descriptor)) {
                        0 -> shape = decodeSerializableElement(descriptor, 0, shapeSerializer) as S
                        1 -> size = decodeSerializableElement(descriptor, 1, SizeFSerializer)
                        CompositeDecoder.DECODE_DONE -> break
                        else -> error("Unexpected index: $index")
                    }
                }
                _SizedShape(shape, size)
            }
    }

    companion object {
        //: Register
        fun register() {
            PType.register<_SizedShape<View>>()
        }
    }
}

fun Shape.size(size: SizeF): View =
    modifier(_SizedShape(this, size))

fun Shape.size(width: Float, height: Float): View =
    modifier(_SizedShape(this, SizeF(width, height)))
