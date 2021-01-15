@file:OptIn(ExperimentalStdlibApi::class)

package kotlinx.kotlinui

import kotlinx.kotlinuijson.DynaType
import kotlinx.serialization.*
import kotlinx.serialization.descriptors.*
import kotlinx.serialization.encoding.*
import kotlin.reflect.typeOf

@Serializable(with = DividerSerializer::class)
class Divider : View {
    override val body: Never
        get() = error("Never")

    companion object {
        fun register() {
            DynaType.register<Divider>()
        }
    }
}

internal object DividerSerializer : KSerializer<Divider> {
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
