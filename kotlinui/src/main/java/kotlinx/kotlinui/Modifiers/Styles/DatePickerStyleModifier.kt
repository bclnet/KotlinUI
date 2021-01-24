package kotlinx.kotlinui

import kotlinx.ptype.PType
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.*
import kotlinx.serialization.encoding.*
import kotlin.reflect.KType

@Serializable(with = DatePickerStyleModifier.Serializer::class)
internal data class DatePickerStyleModifier<Style>(
    val style: Style
) : ViewModifier {
//    fun body(content: AnyView) : AnyView = action(AnyView { content })

    //: Codable
    internal class Serializer<Style>(private val styleType: KType, private val styleSerializer: KSerializer<Style>) : KSerializer<DatePickerStyleModifier<Style>> {
        override val descriptor: SerialDescriptor =
            buildClassSerialDescriptor("DatePickerStyleModifier") {
                element<String>("style")
            }

        override fun serialize(encoder: Encoder, value: DatePickerStyleModifier<Style>) =
            encoder.encodeStructure(descriptor) {
                val styleKey = PType.typeKey(styleType)
                encodeStringElement(descriptor, 0, styleKey)
            }

        override fun deserialize(decoder: Decoder): DatePickerStyleModifier<Style> =
            decoder.decodeStructure(descriptor) {
                lateinit var styleKey: String
                while (true) {
                    when (val index = decodeElementIndex(descriptor)) {
                        0 -> styleKey = decodeStringElement(descriptor, 0)
                        CompositeDecoder.DECODE_DONE -> break
                        else -> error("Unexpected index: $index")
                    }
                }
                DatePickerStyleModifier(PType.findAction<() -> Style>(styleKey, "style")!!())
            }
    }

    companion object {
        //: Register
        fun register() {
            PType.register<DatePickerStyleModifier<Any>>()
            PType.register<CompactDatePickerStyle>(actions = hashMapOf("style" to ::CompactDatePickerStyle))
            PType.register<DefaultDatePickerStyle>(actions = hashMapOf("style" to ::DefaultDatePickerStyle))
            PType.register<GraphicalDatePickerStyle>(actions = hashMapOf("style" to ::GraphicalDatePickerStyle))
            PType.register<WheelDatePickerStyle>(actions = hashMapOf("style" to ::WheelDatePickerStyle))
            PType.register<FieldDatePickerStyle>(actions = hashMapOf("style" to ::FieldDatePickerStyle))
            PType.register<StepperFieldDatePickerStyle>(actions = hashMapOf("style" to ::StepperFieldDatePickerStyle))
        }
    }
}

interface DatePickerStyle

class CompactDatePickerStyle : DatePickerStyle
class DefaultDatePickerStyle : ListStyle
class GraphicalDatePickerStyle : ListStyle
class WheelDatePickerStyle : ListStyle
class FieldDatePickerStyle : ListStyle
class StepperFieldDatePickerStyle : ListStyle
