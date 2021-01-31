package kotlinx.kotlinui

import kotlinx.ptype.PType
import kotlinx.serialization.*
import kotlinx.serialization.descriptors.*
import kotlinx.serialization.encoding.*
import kotlin.reflect.KType

@Serializable(with = DatePickerStyleModifier.Serializer::class)
@SerialName(":DatePickerStyleModifier")
internal data class DatePickerStyleModifier<Style : DatePickerStyle>(
    @Polymorphic val style: Style
) : ViewModifier {
//    fun body(content: AnyView) : AnyView = action(AnyView { content })

    //: Codable
    internal class Serializer<Style : DatePickerStyle> : KSerializer<DatePickerStyleModifier<Style>> {
        val styleSerializer = PolymorphicSerializer(Any::class)

        override val descriptor: SerialDescriptor =
            buildClassSerialDescriptor("DatePickerStyleModifier") {
                element("style", styleSerializer.descriptor)
            }

        override fun serialize(encoder: Encoder, value: DatePickerStyleModifier<Style>) =
            encoder.encodeStructure(descriptor) {
                encodeSerializableElement(descriptor, 0, styleSerializer, value.style)
            }

        override fun deserialize(decoder: Decoder): DatePickerStyleModifier<Style> =
            decoder.decodeStructure(descriptor) {
                lateinit var style: Style
                while (true) {
                    when (val index = decodeElementIndex(descriptor)) {
                        0 -> style = decodeSerializableElement(descriptor, 0, styleSerializer) as Style
                        CompositeDecoder.DECODE_DONE -> break
                        else -> error("Unexpected index: $index")
                    }
                }
                DatePickerStyleModifier(style)
            }
    }

    companion object {
        //: Register
        fun register() {
            PType.register<DatePickerStyleModifier<DatePickerStyle>>()
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

@Serializable
@SerialName(":CompactDatePickerStyle")
class CompactDatePickerStyle : DatePickerStyle {
    override fun equals(other: Any?): Boolean = other is CompactDatePickerStyle
    override fun hashCode(): Int = javaClass.hashCode()
}

@Serializable
@SerialName(":DefaultDatePickerStyle")
class DefaultDatePickerStyle : DatePickerStyle {
    override fun equals(other: Any?): Boolean = other is DefaultDatePickerStyle
    override fun hashCode(): Int = javaClass.hashCode()
}

@Serializable
@SerialName(":GraphicalDatePickerStyle")
class GraphicalDatePickerStyle : DatePickerStyle {
    override fun equals(other: Any?): Boolean = other is GraphicalDatePickerStyle
    override fun hashCode(): Int = javaClass.hashCode()
}

@Serializable
@SerialName(":WheelDatePickerStyle")
class WheelDatePickerStyle : DatePickerStyle {
    override fun equals(other: Any?): Boolean = other is WheelDatePickerStyle
    override fun hashCode(): Int = javaClass.hashCode()
}

@Serializable
@SerialName(":FieldDatePickerStyle")
class FieldDatePickerStyle : DatePickerStyle {
    override fun equals(other: Any?): Boolean = other is FieldDatePickerStyle
    override fun hashCode(): Int = javaClass.hashCode()
}

@Serializable
@SerialName(":StepperFieldDatePickerStyle")
class StepperFieldDatePickerStyle : DatePickerStyle {
    override fun equals(other: Any?): Boolean = other is StepperFieldDatePickerStyle
    override fun hashCode(): Int = javaClass.hashCode()
}

