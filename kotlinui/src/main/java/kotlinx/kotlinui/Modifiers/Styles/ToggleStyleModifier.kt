package kotlinx.kotlinui

import kotlinx.ptype.PType
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.*
import kotlinx.serialization.encoding.*
import kotlin.reflect.KType

@Serializable(with = ToggleStyleModifier.Serializer::class)
internal data class ToggleStyleModifier<Style>(
    val style: Style
) : ViewModifier {
//    fun body(content: AnyView) : AnyView = action(AnyView { content })

    //: Codable
    internal class Serializer<Style>(private val styleType: KType, private val styleSerializer: KSerializer<Style>) : KSerializer<ToggleStyleModifier<Style>> {
        override val descriptor: SerialDescriptor =
            buildClassSerialDescriptor("ToggleStyleModifier") {
                element<String>("style")
            }

        override fun serialize(encoder: Encoder, value: ToggleStyleModifier<Style>) =
            encoder.encodeStructure(descriptor) {
                val styleKey = PType.typeKey(styleType)
                encodeStringElement(descriptor, 0, styleKey)
            }

        override fun deserialize(decoder: Decoder): ToggleStyleModifier<Style> =
            decoder.decodeStructure(descriptor) {
                lateinit var styleKey: String
                while (true) {
                    when (val index = decodeElementIndex(descriptor)) {
                        0 -> styleKey = decodeStringElement(descriptor, 0)
                        CompositeDecoder.DECODE_DONE -> break
                        else -> error("Unexpected index: $index")
                    }
                }
                ToggleStyleModifier(PType.findAction<() -> Style>(styleKey, "style")!!())
            }
    }

    companion object {
        //: Register
        fun register() {
            PType.register<ToggleStyleModifier<Any>>()
            PType.register<DefaultToggleStyle>(actions = hashMapOf("style" to ::DefaultToggleStyle))
            PType.register<SwitchToggleStyle>(actions = hashMapOf("style" to ::SwitchToggleStyle))
            PType.register<CheckboxToggleStyle>(actions = hashMapOf("style" to ::CheckboxToggleStyle))
        }
    }
}

interface ToggleStyle {
    fun makeBody(configuration: ToggleStyleConfiguration): View
}

class DefaultToggleStyle : ToggleStyle {
    override fun makeBody(configuration: ToggleStyleConfiguration): View = configuration.label
}

class SwitchToggleStyle : ToggleStyle {
    override fun makeBody(configuration: ToggleStyleConfiguration): View = configuration.label
}

class CheckboxToggleStyle : ToggleStyle {
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