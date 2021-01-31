package kotlinx.kotlinui

import kotlinx.ptype.PType
import kotlinx.serialization.*
import kotlinx.serialization.descriptors.*
import kotlinx.serialization.encoding.*
import kotlin.reflect.KType

@Serializable(with = ToggleStyleModifier.Serializer::class)
@SerialName(":ToggleStyleModifier")
internal data class ToggleStyleModifier<Style : ToggleStyle>(
    @Polymorphic val style: Style
) : ViewModifier {
//    fun body(content: AnyView) : AnyView = action(AnyView { content })

    //: Codable
    internal class Serializer<Style : ToggleStyle> : KSerializer<ToggleStyleModifier<Style>> {
        val styleSerializer = PolymorphicSerializer(Any::class)

        override val descriptor: SerialDescriptor =
            buildClassSerialDescriptor("ToggleStyleModifier") {
                element<String>("style")
            }

        override fun serialize(encoder: Encoder, value: ToggleStyleModifier<Style>) =
            encoder.encodeStructure(descriptor) {
                encodeSerializableElement(descriptor, 0, styleSerializer, value.style)
            }

        override fun deserialize(decoder: Decoder): ToggleStyleModifier<Style> =
            decoder.decodeStructure(descriptor) {
                lateinit var style: Style
                while (true) {
                    when (val index = decodeElementIndex(descriptor)) {
                        0 -> style = decodeSerializableElement(descriptor, 0, styleSerializer) as Style
                        CompositeDecoder.DECODE_DONE -> break
                        else -> error("Unexpected index: $index")
                    }
                }
                ToggleStyleModifier(style)
            }
    }

    companion object {
        //: Register
        fun register() {
            PType.register<ToggleStyleModifier<ToggleStyle>>()
            PType.register<DefaultToggleStyle>(actions = hashMapOf("style" to ::DefaultToggleStyle))
            PType.register<SwitchToggleStyle>(actions = hashMapOf("style" to ::SwitchToggleStyle))
            PType.register<CheckboxToggleStyle>(actions = hashMapOf("style" to ::CheckboxToggleStyle))
        }
    }
}

interface ToggleStyle {
    fun makeBody(configuration: ToggleStyleConfiguration): View
}

@Serializable
@SerialName(":DefaultToggleStyle")
class DefaultToggleStyle : ToggleStyle {
    override fun equals(other: Any?): Boolean = other is DefaultToggleStyle
    override fun hashCode(): Int = javaClass.hashCode()
    override fun makeBody(configuration: ToggleStyleConfiguration): View = configuration.label
}

@Serializable
@SerialName(":SwitchToggleStyle")
class SwitchToggleStyle : ToggleStyle {
    override fun equals(other: Any?): Boolean = other is SwitchToggleStyle
    override fun hashCode(): Int = javaClass.hashCode()
    override fun makeBody(configuration: ToggleStyleConfiguration): View = configuration.label
}

@Serializable
@SerialName(":CheckboxToggleStyle")
class CheckboxToggleStyle : ToggleStyle {
    override fun equals(other: Any?): Boolean = other is CheckboxToggleStyle
    override fun hashCode(): Int = javaClass.hashCode()
    override fun makeBody(configuration: ToggleStyleConfiguration): View = configuration.label
}

class ToggleStyleConfiguration {
    class Label : View {
        override val body: View
            get() = error("Never")
    }

    var label: Label = Label()
    var isOn: Boolean = false
        get() = field
        set(newValue) {
            field = newValue
        }
    val __isOn: Binding<Boolean>
        get() = Binding({ isOn }, { newValue -> isOn = newValue });
}