package kotlinx.kotlinui

import kotlinx.kotlinuijson.DynaType
import kotlinx.serialization.*
import kotlinx.serialization.descriptors.*
import kotlinx.serialization.encoding.*
import kotlinx.system.KTypeBase

@Serializable(with = DividerSerializer::class)
class Divider : KTypeBase(), View {
    override val body: View
        get() = error("Never")

    companion object {
        //: Register
        fun register() {
            DynaType.register(Divider::class)
        }
    }
}

class DividerSerializer : KSerializer<Divider> {
    override val descriptor: SerialDescriptor =
        buildClassSerialDescriptor("Divider") {
        }

    override fun serialize(encoder: Encoder, value: Divider) =
        encoder.encodeStructure(descriptor) {
        }

    override fun deserialize(decoder: Decoder): Divider =
        decoder.decodeStructure(descriptor) {
            Divider()
        }
}

internal fun Divider._makeView(view: _GraphValue<Divider>, inputs: _ViewInputs): _ViewOutputs = error("Not Implemented")
