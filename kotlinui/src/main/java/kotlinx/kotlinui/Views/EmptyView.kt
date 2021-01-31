package kotlinx.kotlinui

import kotlinx.ptype.PType
import kotlinx.serialization.*
import kotlinx.serialization.descriptors.*
import kotlinx.serialization.encoding.*

@Serializable(with = EmptyView.Serializer::class)
class EmptyView : View {
    override val body: Never
        get() = error("Never")

    internal object Serializer : KSerializer<EmptyView> {
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

    companion object {
        //: Register
        fun register() {
            PType.register<EmptyView>()
        }
    }
}


//internal fun EmptyView._makeView(view: _GraphValue<EmptyView>, inputs: _ViewInputs): _ViewOutputs = error("Not Implemented")
//internal fun EmptyView._makeViewList(view: _GraphValue<EmptyView>, inputs: _ViewListInputs): _ViewListOutputs = error("Not Implemented")