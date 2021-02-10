package kotlinx.kotlinui

import android.content.Context
import android.util.SizeF
import android.widget.FrameLayout
import kotlinx.ptype.PType
import kotlinx.serialization.*
import kotlinx.serialization.descriptors.*
import kotlinx.serialization.encoding.*
import java.util.*
import android.view.View as XView

@Serializable(with = GeometryReader.Serializer::class)
class GeometryReader<Content : View>(
    val content: ViewBuilder.(Geometry) -> Content
) : View, ViewBuildable {
    override val body: Never
        get() = error("Never")

    //: Codable
    internal class Serializer<Content : View> : KSerializer<GeometryReader<Content>> {
        val contentSerializer = PolymorphicSerializer(Any::class)

        override val descriptor: SerialDescriptor =
            buildClassSerialDescriptor(":GeometryReader") {
                element("content", contentSerializer.descriptor)
            }

        override fun serialize(encoder: Encoder, value: GeometryReader<Content>) =
            encoder.encodeStructure(descriptor) {
                val content = value.content(ViewBuilder, Geometry(SizeF(100f, 100f)))
                encodeSerializableElement(descriptor, 0, contentSerializer, content)
            }

        override fun deserialize(decoder: Decoder): GeometryReader<Content> =
            decoder.decodeStructure(descriptor) {
                lateinit var content: Content
                while (true) {
                    when (val index = decodeElementIndex(descriptor)) {
                        0 -> content = decodeSerializableElement(descriptor, 0, contentSerializer) as Content
                        CompositeDecoder.DECODE_DONE -> break
                        else -> error("Unexpected index: $index")
                    }
                }
                GeometryReader { content }
            }
    }

    //: ViewBuildable
    override fun buildView(context: Context?): XView {
        val content = content(ViewBuilder, Geometry(SizeF(100f, 100f)))
        val view = content.builder.buildView(context)
        return view
    }

    class Geometry(
        val size: SizeF
    )

    companion object {
        //: Register
        fun register() {
            PType.register<GeometryReader<AnyView>>()
        }
    }
}
