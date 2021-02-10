package kotlinx.kotlinui

import android.view.View as XView
import kotlinx.ptype.PType
import kotlinx.serialization.*
import kotlinx.serialization.descriptors.*
import kotlinx.serialization.encoding.*

@Serializable(with = VSplitView.Serializer::class)
class VSplitView<Content : View>(
    content: ViewBuilder.() -> Content
) : View {
    override fun equals(other: Any?): Boolean = other is VSplitView<*> && content == other.content
    override fun hashCode(): Int = content.hashCode()

    val content: Content = content(ViewBuilder)

    override val body: View
        get() = error("Never")

    //: Codable
    internal class Serializer<Content : View> : KSerializer<VSplitView<Content>> {
        val contentSerializer = PolymorphicSerializer(Any::class)

        override val descriptor: SerialDescriptor =
            buildClassSerialDescriptor(":VSplitView") {
                element<View>("content")
            }

        override fun serialize(encoder: Encoder, value: VSplitView<Content>) =
            encoder.encodeStructure(descriptor) {
                encodeSerializableElement(descriptor, 0, contentSerializer, value.content)
            }

        override fun deserialize(decoder: Decoder): VSplitView<Content> =
            decoder.decodeStructure(descriptor) {
                lateinit var content: Content
                while (true) {
                    when (val index = decodeElementIndex(descriptor)) {
                        0 -> content = decodeSerializableElement(descriptor, 0, contentSerializer) as Content
                        CompositeDecoder.DECODE_DONE -> break
                        else -> error("Unexpected index: $index")
                    }
                }
                VSplitView { content }
            }
    }

    companion object {
        //: Register
        fun register() {
            PType.register<VSplitView<AnyView>>()
        }
    }
}

//internal fun <Content : View> VSplitView<Content>._makeView(view: _GraphValue<VSplitView<Content>>, inputs: _ViewInputs): _ViewOutputs = error("Not Implemented")
