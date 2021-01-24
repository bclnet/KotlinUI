package kotlinx.kotlinui

import kotlinx.ptype.PType
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.*
import kotlinx.serialization.encoding.*
import kotlin.reflect.KType

@Serializable(with = PickerStyleWriter.Serializer::class)
internal data class PickerStyleWriter<Style>(
    val style: Style
) : ViewModifier {
//    fun body(content: AnyView) : AnyView = action(AnyView { content })

    //: Codable
    internal class Serializer<Style>(private val styleType: KType, private val styleSerializer: KSerializer<Style>) : KSerializer<PickerStyleWriter<Style>> {
        override val descriptor: SerialDescriptor =
            buildClassSerialDescriptor("PickerStyleWriter") {
                element<String>("style")
            }

        override fun serialize(encoder: Encoder, value: PickerStyleWriter<Style>) =
            encoder.encodeStructure(descriptor) {
                val styleKey = PType.typeKey(styleType)
                encodeStringElement(descriptor, 0, styleKey)
            }

        override fun deserialize(decoder: Decoder): PickerStyleWriter<Style> =
            decoder.decodeStructure(descriptor) {
                lateinit var styleKey: String
                while (true) {
                    when (val index = decodeElementIndex(descriptor)) {
                        0 -> styleKey = decodeStringElement(descriptor, 0)
                        CompositeDecoder.DECODE_DONE -> break
                        else -> error("Unexpected index: $index")
                    }
                }
                PickerStyleWriter(PType.findAction<() -> Style>(styleKey, "style")!!())
            }
    }

    companion object {
        //: Register
        fun register() {
            PType.register<PickerStyleWriter<Any>>()
            PType.register<CircularProgressViewStyle>(actions = hashMapOf("style" to ::CircularProgressViewStyle))
            PType.register<DefaultPickerStyle>(actions = hashMapOf("style" to ::DefaultPickerStyle))
            PType.register<InlinePickerStyle>(actions = hashMapOf("style" to ::InlinePickerStyle))
            PType.register<MenuPickerStyle>(actions = hashMapOf("style" to ::MenuPickerStyle))
            PType.register<SegmentedPickerStyle>(actions = hashMapOf("style" to ::SegmentedPickerStyle))
            PType.register<WheelPickerStyle>(actions = hashMapOf("style" to ::WheelPickerStyle))
            PType.register<PopUpButtonPickerStyle>(actions = hashMapOf("style" to ::PopUpButtonPickerStyle))
            PType.register<RadioGroupPickerStyle>(actions = hashMapOf("style" to ::RadioGroupPickerStyle))
        }
    }
}

//class _PickerValue<Style : PickerStyle, SelectionValue>

interface PickerStyle

class DefaultPickerStyle : PickerStyle
class InlinePickerStyle : PickerStyle
class MenuPickerStyle : PickerStyle
class SegmentedPickerStyle : PickerStyle
class WheelPickerStyle : PickerStyle
class PopUpButtonPickerStyle : PickerStyle
class RadioGroupPickerStyle : PickerStyle

//internal fun <SelectionValue> DefaultPickerStyle._makeView(value: _GraphValue<_PickerValue<DefaultPickerStyle, SelectionValue>>, inputs: _ViewInputs): _ViewOutputs = error("Not Implemented")
//internal fun <SelectionValue> DefaultPickerStyle._makeViewList(value: _GraphValue<_PickerValue<DefaultPickerStyle, SelectionValue>>, inputs: _ViewListInputs): _ViewListOutputs = error("Not Implemented")
//internal fun <SelectionValue> SegmentedPickerStyle._makeView(value: _GraphValue<_PickerValue<SegmentedPickerStyle, SelectionValue>>, inputs: _ViewInputs): _ViewOutputs = error("Not Implemented")
//internal fun <SelectionValue> SegmentedPickerStyle._makeViewList(value: _GraphValue<_PickerValue<SegmentedPickerStyle, SelectionValue>>, inputs: _ViewListInputs): _ViewListOutputs = error("Not Implemented")
//internal fun <SelectionValue> PopUpButtonPickerStyle._makeView(value: _GraphValue<_PickerValue<PopUpButtonPickerStyle, SelectionValue>>, inputs: _ViewInputs): _ViewOutputs = error("Not Implemented")
//internal fun <SelectionValue> PopUpButtonPickerStyle._makeViewList(value: _GraphValue<_PickerValue<PopUpButtonPickerStyle, SelectionValue>>, inputs: _ViewListInputs): _ViewListOutputs = error("Not Implemented")
