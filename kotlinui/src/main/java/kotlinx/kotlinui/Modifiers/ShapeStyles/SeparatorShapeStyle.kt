package kotlinx.kotlinui

import kotlinx.ptype.PType
import kotlinx.serialization.*
import kotlinx.serialization.descriptors.*
import kotlinx.serialization.encoding.*

@Serializable(with = SeparatorShapeStyle.Serializer::class)
@SerialName(":SeparatorShapeStyle")
class SeparatorShapeStyle {
    override fun equals(other: Any?): Boolean = other is SeparatorShapeStyle
    override fun hashCode(): Int = javaClass.hashCode()

    //: Codable
    internal object Serializer : KSerializer<SeparatorShapeStyle> {
        override val descriptor: SerialDescriptor =
            buildClassSerialDescriptor("SeparatorShapeStyle") {
            }

        override fun serialize(encoder: Encoder, value: SeparatorShapeStyle) =
            encoder.encodeStructure(descriptor) {
            }

        override fun deserialize(decoder: Decoder): SeparatorShapeStyle =
            decoder.decodeStructure(descriptor) {
                SeparatorShapeStyle()
            }
    }

    companion object {
        //: Register
        fun register() {
            PType.register<SeparatorShapeStyle>()
        }
    }
}
