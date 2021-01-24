package kotlinx.kotlinui

import kotlinx.ptype.PType
import kotlinx.serialization.*
import kotlinx.serialization.descriptors.*
import kotlinx.serialization.encoding.*

@Serializable(with = Toggle.Serializer::class)
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

    internal class Serializer<Label : View>(private val labelSerializer: KSerializer<Label>) : KSerializer<Toggle<Label>> {
        override val descriptor: SerialDescriptor =
            buildClassSerialDescriptor("Toggle") {
                element<Binding<Boolean>>("isOn")
                element<View>("label")
            }

        override fun serialize(encoder: Encoder, value: Toggle<Label>) =
            encoder.encodeStructure(descriptor) {
                encodeSerializableElement(descriptor, 0, serializer(), value.__isOn)
                encodeSerializableElement(descriptor, 1, labelSerializer, value._label)
            }

        override fun deserialize(decoder: Decoder): Toggle<Label> =
            decoder.decodeStructure(descriptor) {
                lateinit var isOn: Binding<Boolean>
                lateinit var label: Label
                while (true) {
                    when (val index = decodeElementIndex(_VStackLayout.Serializer.descriptor)) {
                        0 -> isOn = decodeSerializableElement(descriptor, 0, serializer())
                        1 -> label = decodeSerializableElement(descriptor, 1, labelSerializer)
                        CompositeDecoder.DECODE_DONE -> break
                        else -> error("Unexpected index: $index")
                    }
                }
                Toggle(isOn) { label }
            }
    }

    companion object {
        //: Register
        fun register() {
            PType.register<Toggle<AnyView>>()
        }
    }
}

fun <S : ToggleStyle> View.toggleStyle(style: S): View =
    modifier(ToggleStyleModifier(style))
