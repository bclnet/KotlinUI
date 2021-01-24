package kotlinx.kotlinui

import kotlinx.ptype.PType
import kotlinx.serialization.*
import kotlinx.serialization.descriptors.*
import kotlinx.serialization.encoding.*

@Serializable(with = SeparatorShapeStyle.Serializer::class)
class SeparatorShapeStyle(
) {
    //: Codable
    internal object Serializer : KSerializer<SeparatorShapeStyle> {
        override val descriptor: SerialDescriptor =
            buildClassSerialDescriptor("SeparatorShapeStyle") {
            }

        override fun serialize(encoder: Encoder, value: SeparatorShapeStyle) =
            encoder.encodeStructure(descriptor) {
                error("Not Implemented")
            }

        override fun deserialize(decoder: Decoder): SeparatorShapeStyle =
            decoder.decodeStructure(descriptor) {
                error("Not Implemented")
            }
    }

    companion object {
        //: Register
        fun register() {
            PType.register<SeparatorShapeStyle>()
        }
    }
}
