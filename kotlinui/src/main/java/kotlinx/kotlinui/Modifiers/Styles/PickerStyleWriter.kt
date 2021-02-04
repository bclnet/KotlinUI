package kotlinx.kotlinui

import kotlinx.ptype.PType
import kotlinx.serialization.*
import kotlinx.serialization.descriptors.*
import kotlinx.serialization.encoding.*
import kotlin.reflect.KType

@Serializable(with = PickerStyleWriter.Serializer::class)
internal data class PickerStyleWriter<Style : PickerStyle>(
    @Polymorphic val style: Style
) : ViewModifier {
//    fun body(content: AnyView) : AnyView = action(AnyView { content })

    //: Codable
    internal class Serializer<Style : PickerStyle> : KSerializer<PickerStyleWriter<Style>> {
        val styleSerializer = PolymorphicSerializer(Any::class)

        override val descriptor: SerialDescriptor =
            buildClassSerialDescriptor(":PickerStyleWriter") {
                element<String>("style")
            }

        override fun serialize(encoder: Encoder, value: PickerStyleWriter<Style>) =
            encoder.encodeStructure(descriptor) {
                encodeSerializableElement(descriptor, 0, styleSerializer, value.style)
            }

        override fun deserialize(decoder: Decoder): PickerStyleWriter<Style> =
            decoder.decodeStructure(descriptor) {
                lateinit var style: Style
                while (true) {
                    when (val index = decodeElementIndex(descriptor)) {
                        0 -> style = decodeSerializableElement(descriptor, 0, styleSerializer) as Style
                        CompositeDecoder.DECODE_DONE -> break
                        else -> error("Unexpected index: $index")
                    }
                }
                PickerStyleWriter(style)
            }
    }

    companion object {
        //: Register
        fun register() {
            PType.register<PickerStyleWriter<PickerStyle>>()
            PType.register<CircularProgressViewStyle>()
            PType.register<DefaultPickerStyle>()
            PType.register<InlinePickerStyle>()
            PType.register<MenuPickerStyle>()
            PType.register<SegmentedPickerStyle>()
            PType.register<WheelPickerStyle>()
            PType.register<PopUpButtonPickerStyle>()
            PType.register<RadioGroupPickerStyle>()
        }
    }
}

//class _PickerValue<Style : PickerStyle, SelectionValue>

interface PickerStyle

@Serializable
@SerialName(":DefaultPickerStyle")
class DefaultPickerStyle : PickerStyle {
    override fun equals(other: Any?): Boolean = other is DefaultPickerStyle
    override fun hashCode(): Int = javaClass.hashCode()
}

@Serializable
@SerialName(":InlinePickerStyle")
class InlinePickerStyle : PickerStyle {
    override fun equals(other: Any?): Boolean = other is InlinePickerStyle
    override fun hashCode(): Int = javaClass.hashCode()
}

@Serializable
@SerialName(":MenuPickerStyle")
class MenuPickerStyle : PickerStyle {
    override fun equals(other: Any?): Boolean = other is MenuPickerStyle
    override fun hashCode(): Int = javaClass.hashCode()
}

@Serializable
@SerialName(":SegmentedPickerStyle")
class SegmentedPickerStyle : PickerStyle {
    override fun equals(other: Any?): Boolean = other is SegmentedPickerStyle
    override fun hashCode(): Int = javaClass.hashCode()
}

@Serializable
@SerialName(":WheelPickerStyle")
class WheelPickerStyle : PickerStyle {
    override fun equals(other: Any?): Boolean = other is WheelPickerStyle
    override fun hashCode(): Int = javaClass.hashCode()
}

@Serializable
@SerialName(":PopUpButtonPickerStyle")
class PopUpButtonPickerStyle : PickerStyle {
    override fun equals(other: Any?): Boolean = other is PopUpButtonPickerStyle
    override fun hashCode(): Int = javaClass.hashCode()
}

@Serializable
@SerialName(":RadioGroupPickerStyle")
class RadioGroupPickerStyle : PickerStyle {
    override fun equals(other: Any?): Boolean = other is RadioGroupPickerStyle
    override fun hashCode(): Int = javaClass.hashCode()
}

//internal fun <SelectionValue> DefaultPickerStyle._makeView(value: _GraphValue<_PickerValue<DefaultPickerStyle, SelectionValue>>, inputs: _ViewInputs): _ViewOutputs = error("Not Implemented")
//internal fun <SelectionValue> DefaultPickerStyle._makeViewList(value: _GraphValue<_PickerValue<DefaultPickerStyle, SelectionValue>>, inputs: _ViewListInputs): _ViewListOutputs = error("Not Implemented")
//internal fun <SelectionValue> SegmentedPickerStyle._makeView(value: _GraphValue<_PickerValue<SegmentedPickerStyle, SelectionValue>>, inputs: _ViewInputs): _ViewOutputs = error("Not Implemented")
//internal fun <SelectionValue> SegmentedPickerStyle._makeViewList(value: _GraphValue<_PickerValue<SegmentedPickerStyle, SelectionValue>>, inputs: _ViewListInputs): _ViewListOutputs = error("Not Implemented")
//internal fun <SelectionValue> PopUpButtonPickerStyle._makeView(value: _GraphValue<_PickerValue<PopUpButtonPickerStyle, SelectionValue>>, inputs: _ViewInputs): _ViewOutputs = error("Not Implemented")
//internal fun <SelectionValue> PopUpButtonPickerStyle._makeViewList(value: _GraphValue<_PickerValue<PopUpButtonPickerStyle, SelectionValue>>, inputs: _ViewListInputs): _ViewListOutputs = error("Not Implemented")
