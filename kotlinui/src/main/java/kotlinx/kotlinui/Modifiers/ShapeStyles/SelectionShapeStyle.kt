package kotlinx.kotlinui

import kotlinx.ptype.PType
import kotlinx.serialization.*
import kotlinx.serialization.descriptors.*
import kotlinx.serialization.encoding.*

@Serializable(with = SelectionShapeStyle.Serializer::class)
data class SelectionShapeStyle(
    val isSelected: Boolean
) : ShapeStyle {
    //: Codable
    internal object Serializer : KSerializer<SelectionShapeStyle> {
        override val descriptor: SerialDescriptor =
            buildClassSerialDescriptor(":SelectionShapeStyle") {
                element<Boolean>("selected")
            }

        override fun serialize(encoder: Encoder, value: SelectionShapeStyle) =
            encoder.encodeStructure(descriptor) {
                encodeBooleanElement(descriptor, 0, value.isSelected)
            }

        override fun deserialize(decoder: Decoder): SelectionShapeStyle =
            decoder.decodeStructure(descriptor) {
                var isSelected: Boolean = false
                while (true) {
                    when (val index = decodeElementIndex(descriptor)) {
                        0 -> isSelected = decodeBooleanElement(descriptor, 0)
                        CompositeDecoder.DECODE_DONE -> break
                        else -> error("Unexpected index: $index")
                    }
                }
                SelectionShapeStyle(isSelected)
            }
    }

    companion object {
        //: Register
        fun register() {
            PType.register<SelectionShapeStyle>()
        }
    }
}
