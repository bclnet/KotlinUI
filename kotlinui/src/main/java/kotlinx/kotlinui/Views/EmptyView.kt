package kotlinx.kotlinui

import kotlinx.serialization.*
import kotlinx.serialization.descriptors.*
import kotlinx.serialization.encoding.*

@Serializable(with = EmptyViewSerializer::class)
class EmptyView : View {
    override val body: Never
        get() = error("Never")
}

internal object EmptyViewSerializer : KSerializer<EmptyView> {
    override val descriptor: SerialDescriptor =
        buildClassSerialDescriptor("EmptyView") {
        }

    override fun serialize(encoder: Encoder, value: EmptyView) =
        encoder.encodeStructure(descriptor) {
        }

    override fun deserialize(decoder: Decoder): EmptyView =
        decoder.decodeStructure(descriptor) {
            EmptyView()
        }
}

//internal fun EmptyView._makeView(view: _GraphValue<EmptyView>, inputs: _ViewInputs): _ViewOutputs = error("Not Implemented")
//internal fun EmptyView._makeViewList(view: _GraphValue<EmptyView>, inputs: _ViewListInputs): _ViewListOutputs = error("Not Implemented")