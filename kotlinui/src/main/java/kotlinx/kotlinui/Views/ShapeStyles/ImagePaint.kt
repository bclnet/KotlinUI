package kotlinx.kotlinui

import android.graphics.RectF
import kotlinx.ptype.PType
import kotlinx.serialization.*
import kotlinx.serialization.descriptors.*
import kotlinx.serialization.encoding.*

@Serializable(with = ImagePaint.Serializer::class)
data class ImagePaint(
    val image: Image,
    val sourceRect: RectF,
    val scale: Float
) : ShapeStyle, View, IAnyView {
    override val anyView: AnyView
        get() = AnyView(this)

    override fun makeView(): Shape = error("Not Implemented")
    override val body: Never
        get() = error("Never")

    //: Codable
    internal object Serializer : KSerializer<ImagePaint> {
        override val descriptor: SerialDescriptor =
            buildClassSerialDescriptor(":ImagePaint") {
                element<Image>("image")
                element("sourceRect", RectFSerializer.descriptor)
                element<Float>("scale")
            }

        override fun serialize(encoder: Encoder, value: ImagePaint) =
            encoder.encodeStructure(descriptor) {
                encodeSerializableElement(descriptor, 0, Image.Serializer, value.image)
                if (value.sourceRect != RectF(0f, 0f, 1f, 1f)) encodeSerializableElement(descriptor, 1, RectFSerializer, value.sourceRect)
                if (value.scale != 1f) encodeFloatElement(descriptor, 2, value.scale)
            }

        override fun deserialize(decoder: Decoder): ImagePaint =
            decoder.decodeStructure(descriptor) {
                lateinit var image: Image
                var sourceRect: RectF = RectF(0f, 0f, 1f, 1f)
                var scale: Float = 1f
                while (true) {
                    when (val index = decodeElementIndex(descriptor)) {
                        0 -> image = decodeSerializableElement(descriptor, 0, Image.Serializer)
                        1 -> sourceRect = decodeSerializableElement(descriptor, 1, RectFSerializer)
                        2 -> scale = decodeFloatElement(descriptor, 2)
                        CompositeDecoder.DECODE_DONE -> break
                        else -> error("Unexpected index: $index")
                    }
                }
                ImagePaint(image, sourceRect, scale)
            }
    }

    companion object {
        //: Register
        fun register() {
            PType.register<ImagePaint>()
        }
    }
}
