package kotlinx.kotlinui

import kotlinx.ptype.PType
import kotlinx.serialization.*
import kotlinx.serialization.descriptors.*
import kotlinx.serialization.encoding.*

@Serializable(with = SelectionShapeStyle.Serializer::class)
@SerialName(":SelectionShapeStyle")
data class SelectionShapeStyle(val isSelected: Boolean) : ShapeStyle {
    //: Codable
    internal object Serializer : KSerializer<SelectionShapeStyle> {
        override val descriptor: SerialDescriptor =
            buildClassSerialDescriptor("SelectionShapeStyle") {
            }

        override fun serialize(encoder: Encoder, value: SelectionShapeStyle) =
            encoder.encodeStructure(descriptor) {
            }

        override fun deserialize(decoder: Decoder): SelectionShapeStyle =
            decoder.decodeStructure(descriptor) {
                SelectionShapeStyle(true)
            }
    }

    companion object {
        //: Register
        fun register() {
            PType.register<SelectionShapeStyle>()
        }
    }
}
