package kotlinx.kotlinui

import kotlinx.ptype.PType
import kotlinx.serialization.*
import kotlinx.serialization.descriptors.*
import kotlinx.serialization.encoding.*

@Serializable(with = _ShapeView.Serializer::class)
data class _ShapeView<Content : Shape, Style : ShapeStyle>(
    val shape: Content,
    val style: Style,
    val fillStyle: FillStyle = FillStyle()
) : View {
    override val body: Never
        get() = error("Never")

    //: Codable
    internal class Serializer<Content : Shape, Style : ShapeStyle> : KSerializer<_ShapeView<Content, Style>> {
        val contentSerializer = PolymorphicSerializer(Any::class)
        val styleSerializer = PolymorphicSerializer(Any::class)

        override val descriptor: SerialDescriptor =
            buildClassSerialDescriptor("_ShapeView") {
                element("shape", contentSerializer.descriptor)
                element("style", styleSerializer.descriptor)
                element<FillStyle>("fillStyle")
            }

        override fun serialize(encoder: Encoder, value: _ShapeView<Content, Style>) =
            encoder.encodeStructure(descriptor) {
                encodeSerializableElement(descriptor, 0, contentSerializer, value.shape)
                encodeSerializableElement(descriptor, 1, styleSerializer, value.style)
                encodeSerializableElement(descriptor, 2, FillStyle.Serializer, value.fillStyle)
            }

        override fun deserialize(decoder: Decoder): _ShapeView<Content, Style> =
            decoder.decodeStructure(descriptor) {
                lateinit var shape: Content
                lateinit var style: Style
                lateinit var fillStyle: FillStyle
                while (true) {
                    when (val index = decodeElementIndex(descriptor)) {
                        0 -> shape = decodeSerializableElement(descriptor, 0, contentSerializer) as Content
                        1 -> style = decodeSerializableElement(descriptor, 1, styleSerializer) as Style
                        2 -> fillStyle = decodeSerializableElement(descriptor, 2, FillStyle.Serializer)
                        CompositeDecoder.DECODE_DONE -> break
                        else -> error("Unexpected index: $index")
                    }
                }
                _ShapeView(shape, style, fillStyle)
            }
    }

    companion object {
        //: Register
        fun register() {
//            PType.register<_ShapeView<AnyShape, AngularGradient>>()
//            PType.register<_ShapeView<AnyShape, BackgroundStyle>>()
//            PType.register<_ShapeView<AnyShape, Color>>()
//            PType.register<_ShapeView<AnyShape, ForegroundStyle>>()
//            PType.register<_ShapeView<AnyShape, ImagePaint>>()
//            PType.register<_ShapeView<AnyShape, LinearGradient>>()
//            PType.register<_ShapeView<AnyShape, RadialGradient>>()
//            PType.register<_ShapeView<AnyShape, SelectionShapeStyle>>()
//            PType.register<_ShapeView<AnyShape, SeparatorShapeStyle>>()
        }
    }

}

//internal fun <Content : Shape, Style : ShapeStyle> _ShapeView<Content, Style>._makeView(view: _GraphValue<_ShapeView<Content, Style>>, inputs: _ViewInputs): _ViewOutputs = error("Not Implemented")