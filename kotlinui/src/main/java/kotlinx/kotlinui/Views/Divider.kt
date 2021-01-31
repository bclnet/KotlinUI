@file:OptIn(ExperimentalStdlibApi::class)

package kotlinx.kotlinui

import kotlinx.ptype.PType
import kotlinx.serialization.*
import kotlinx.serialization.descriptors.*
import kotlinx.serialization.encoding.*

@Serializable(with = Divider.Serializer::class)
class Divider : View {
    override val body: Never
        get() = error("Never")

    internal object Serializer : KSerializer<Divider> {
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

    companion object {
        //: Register
        fun register() {
            PType.register<Divider>()
        }
    }
}

//internal fun Divider._makeView(view: _GraphValue<Divider>, inputs: _ViewInputs): _ViewOutputs = error("Not Implemented")
