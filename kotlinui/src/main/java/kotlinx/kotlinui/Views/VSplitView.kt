package kotlinx.kotlinui

import kotlinx.serialization.*
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.descriptors.*
import kotlinx.serialization.encoding.*

@Serializable(with = VSplitViewSerializer::class)
class VSplitView<Content : View>(
    content: () -> Content
) : View {
    val content: Content = content()

    override var body: View = error("Not Implemented")
}

class VSplitViewSerializer<Content : View>(private val contentSerializer: KSerializer<Content>) : KSerializer<VSplitView<Content>> {
    override val descriptor: SerialDescriptor =
        buildClassSerialDescriptor("VSplitView") {
            element<View>("content")
        }

    override fun serialize(encoder: Encoder, value: VSplitView<Content>) =
        encoder.encodeStructure(descriptor) {
            encodeSerializableElement(descriptor, 0, contentSerializer, value.content)
        }

    @ExperimentalSerializationApi
    override fun deserialize(decoder: Decoder): VSplitView<Content> =
        decoder.decodeStructure(descriptor) {
            val content = decodeSerializableElement(descriptor, 0, contentSerializer)
            VSplitView { content }
        }
}

internal fun <Content : View> VSplitView<Content>._makeView(view: _GraphValue<VSplitView<Content>>, inputs: _ViewInputs): _ViewOutputs = error("Not Implemented")
