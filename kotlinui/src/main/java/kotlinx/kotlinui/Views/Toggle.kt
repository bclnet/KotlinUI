package kotlinx.kotlinui

import kotlinx.ptype.PType
import kotlinx.serialization.*
import kotlinx.serialization.descriptors.*
import kotlinx.serialization.encoding.*

@Serializable(with = TouchBarSerializer::class)
class Toggle<Label : View>(
    isOn: Binding<Boolean>,
    label: ViewBuilder.() -> Label
) : View {
    var __isOn: Binding<Boolean> = isOn
    var _label: Label = label(ViewBuilder)

    // where Label == ToggleStyleConfiguration.Label
    //public Constructor(configuration: ToggleStyleConfiguration) : this() {
    //    this.__isOn = configuration.__isOn;
    //    this._label = configuration.label;
    //}
    // where Label == Text
//    constructor(titleKey: LocalizedStringKey?, isOn: Binding<Boolean?>?) {
//        error("Not Implemented")
//    }
//    // where Label == Text
//    constructor(title: String?, isOn: Binding<Boolean?>?) {
//        error("Not Implemented")
//    }

    override val body: Label
        get() = _label

    companion object {
        fun register() {
            PType.register<Toggle<AnyView>>()
        }
    }
}

class ToggleSerializer<Label : View>(private val labelSerializer: KSerializer<Label>) : KSerializer<Toggle<Label>> {
    override val descriptor: SerialDescriptor =
        buildClassSerialDescriptor("Toggle") {
            element<Binding<Boolean>>("isOn")
            element<View>("label")
        }

    override fun serialize(encoder: Encoder, value: Toggle<Label>) =
        encoder.encodeStructure(descriptor) {
            encodeSerializableElement(descriptor, 0, serializer<Binding<Boolean>>(), value.__isOn)
            encodeSerializableElement(descriptor, 1, labelSerializer, value._label)
        }

    @ExperimentalSerializationApi
    override fun deserialize(decoder: Decoder): Toggle<Label> =
        decoder.decodeStructure(descriptor) {
            val isOn = decodeSerializableElement(descriptor, 0, serializer<Binding<Boolean>>())
            val label = decodeSerializableElement(descriptor, 1, labelSerializer)
            Toggle(isOn) { label }
        }
}

interface ToggleStyle {
    fun makeBody(configuration: ToggleStyleConfiguration): View
}

class ToggleStyleConfiguration {
    class Label : View {
        override var body: View = error("Never")
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

fun <S : ToggleStyle> View.toggleStyle(style: S): View =
    modifier(ToggleStyleModifier(style))

class CheckboxToggleStyle : ToggleStyle {
    override fun makeBody(configuration: ToggleStyleConfiguration): View = configuration.label
}

class ToggleStyleModifier<Style>(var style: Style) : ViewModifier
