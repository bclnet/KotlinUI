package kotlinx.kotlinui

import kotlinx.kotlinuijson.DynaType
import kotlinx.serialization.*
import kotlinx.serialization.descriptors.*
import kotlinx.serialization.encoding.*

@Serializable(with = VSplitViewSerializer::class)
class VSplitView<Content : View>(
    content: ViewBuilder.() -> Content
) : View {
    val content: Content = content(ViewBuilder())

    override val body: View
        get() = error("Not Implemented")

    companion object {
        fun register() {
            DynaType.register<VSplitView<AnyView>>()
        }
    }
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
