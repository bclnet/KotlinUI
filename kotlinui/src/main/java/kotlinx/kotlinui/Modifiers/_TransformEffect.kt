package kotlinx.kotlinui

import android.graphics.Matrix
import kotlinx.ptype.PType
import kotlinx.serialization.*
import kotlinx.serialization.descriptors.*
import kotlinx.serialization.encoding.*

data class _TransformEffect(
    val transform: Matrix,
) : ViewModifier {
    //: Codable
    internal object Serializer : KSerializer<_TransformEffect> {
        override val descriptor: SerialDescriptor =
            buildClassSerialDescriptor(":_TransformEffect") { }

        override fun serialize(encoder: Encoder, value: _TransformEffect) =
            encoder.encodeStructure(descriptor) { }

        override fun deserialize(decoder: Decoder): _TransformEffect =
            decoder.decodeStructure(descriptor) { error("") }
    }

    companion object {
        //: Register
        fun register() {
            PType.register<_TransformEffect>()
        }
    }
}
