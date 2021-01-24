package kotlinx.kotlinui

import kotlinx.ptype.PType
import kotlinx.serialization.*
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.descriptors.*
import kotlinx.serialization.encoding.*

@Serializable(with = TouchBar.Serializer::class)
class TouchBar<Content : View>(
    id: String?,
    content: ViewBuilder.() -> Content
) : View {
    internal val container: TouchBarContainer = TouchBarContainer(id)
    internal val content: Content = content(ViewBuilder)

    override val body: View
        get() = error("Not Implemented")

    internal class Serializer<Content : View>(private val contentSerializer: KSerializer<Content>) : KSerializer<TouchBar<Content>> {
        override val descriptor: SerialDescriptor =
            buildClassSerialDescriptor("TouchBar") {
                element<String>("id")
                element<View>("content")
            }

        override fun serialize(encoder: Encoder, value: TouchBar<Content>) =
            encoder.encodeStructure(descriptor) {
                val container = value.container
                if (container.id != null) encodeStringElement(descriptor, 0, container.id!!)
                encodeSerializableElement(descriptor, 1, contentSerializer, value.content)
            }

        override fun deserialize(decoder: Decoder): TouchBar<Content> =
            decoder.decodeStructure(descriptor) {
                var id: String? = null
                lateinit var content: Content
                while (true) {
                    when (val index = decodeElementIndex(_VStackLayout.Serializer.descriptor)) {
                        0 -> id = decodeStringElement(descriptor, 0)
                        1 -> content = decodeSerializableElement(descriptor, 1, contentSerializer)
                        CompositeDecoder.DECODE_DONE -> break
                        else -> error("Unexpected index: $index")
                    }
                }
                TouchBar(id) { content }
            }
    }

    companion object {
        //: Register
        fun register() {
            PType.register<TouchBar<AnyView>>()
        }
    }
}

internal class TouchBarContainer(var id: String?)

//internal fun <Content : View> TouchBar<Content>._makeView(view: _GraphValue<TouchBar<Content>>, inputs: _ViewInputs): _ViewOutputs = error("Not Implemented")