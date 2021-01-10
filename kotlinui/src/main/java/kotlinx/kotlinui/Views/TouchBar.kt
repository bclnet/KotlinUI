package kotlinx.kotlinui

import kotlinx.serialization.*
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.descriptors.*
import kotlinx.serialization.encoding.*

@Serializable(with = TouchBarSerializer::class)
class TouchBar<Content : View>(
    id: String?,
    content: () -> Content
) : View {
    internal val container: TouchBarContainer = TouchBarContainer(id)
    internal val content: Content = content()

    override var body: View = error("Not Implemented")
}

class TouchBarSerializer<Content : View>(private val contentSerializer: KSerializer<Content>) : KSerializer<TouchBar<Content>> {
    override val descriptor: SerialDescriptor =
        buildClassSerialDescriptor("TouchBar") {
            element<String?>("id")
            element<View>("content")
        }

    override fun serialize(encoder: Encoder, value: TouchBar<Content>) =
        encoder.encodeStructure(descriptor) {
            val container = value.container
            encodeNullableSerializableElement(descriptor, 0, String.serializer(), container.id)
            encodeSerializableElement(descriptor, 1, contentSerializer, value.content)
        }

    @ExperimentalSerializationApi
    override fun deserialize(decoder: Decoder): TouchBar<Content> =
        decoder.decodeStructure(descriptor) {
            val id = decodeNullableSerializableElement(descriptor, 0, String.serializer() as KSerializer<String?>)
            val content = decodeSerializableElement(descriptor, 1, contentSerializer)
            TouchBar(id) { content }
        }
}

internal fun <Content : View> TouchBar<Content>._makeView(view: _GraphValue<TouchBar<Content>>, inputs: _ViewInputs): _ViewOutputs = error("Not Implemented")

internal class TouchBarContainer(var id: String?)