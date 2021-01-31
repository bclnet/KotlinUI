package kotlinx.kotlinui

import kotlinx.ptype.PType
import kotlinx.serialization.*
import kotlinx.serialization.descriptors.*
import kotlinx.serialization.encoding.*

@Serializable(with = _ClipEffect.Serializer::class)
data class _ClipEffect<ClipShape : Shape>(
    val shape: ClipShape,
    val style: FillStyle
) : ViewModifier {
//    fun body(content: AnyView): AnyView { AnyView(content.modifier(self)) }

    //: Codable
    internal class Serializer<ClipShape : Shape> : KSerializer<_ClipEffect<ClipShape>> {
        val shapeSerializer = PolymorphicSerializer(Any::class)

        override val descriptor: SerialDescriptor =
            buildClassSerialDescriptor("_ClipEffect") {
                element("shape", shapeSerializer.descriptor)
                element<FillStyle>("style")
            }

        override fun serialize(encoder: Encoder, value: _ClipEffect<ClipShape>) =
            encoder.encodeStructure(descriptor) {
                encodeSerializableElement(descriptor, 0, shapeSerializer, value.shape)
                encodeSerializableElement(descriptor, 1, FillStyle.Serializer, value.style)
            }

        override fun deserialize(decoder: Decoder): _ClipEffect<ClipShape> =
            decoder.decodeStructure(descriptor) {
                lateinit var shape: ClipShape
                lateinit var style: FillStyle
                while (true) {
                    when (val index = decodeElementIndex(descriptor)) {
                        0 -> shape = decodeSerializableElement(descriptor, 0, shapeSerializer) as ClipShape
                        1 -> style = decodeSerializableElement(descriptor, 1, FillStyle.Serializer)
                        CompositeDecoder.DECODE_DONE -> break
                        else -> error("Unexpected index: $index")
                    }
                }
                _ClipEffect(shape, style)
            }
    }

    companion object {
        //: Register
        fun register() {
            PType.register<_ClipEffect<Shape>>()
        }
    }
}

fun <S : Shape> View.clipShape(shape: S, style: FillStyle = FillStyle()): View =
    modifier(_ClipEffect<S>(shape, style))
