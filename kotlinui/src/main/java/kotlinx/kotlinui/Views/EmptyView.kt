package kotlinx.kotlinui

import kotlinx.serialization.*
import kotlinx.serialization.descriptors.*
import kotlinx.serialization.encoding.*
import kotlinx.system.KTypeBase

@Serializable(with = EmptyViewSerializer::class)
class EmptyView : KTypeBase(), View {
    override val body: View
        get() = error("Never")
}

class EmptyViewSerializer : KSerializer<EmptyView> {
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

internal fun EmptyView._makeView(view: _GraphValue<EmptyView>, inputs: _ViewInputs): _ViewOutputs = error("Not Implemented")
internal fun EmptyView._makeViewList(view: _GraphValue<EmptyView>, inputs: _ViewListInputs): _ViewListOutputs = error("Not Implemented")