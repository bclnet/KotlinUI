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
    var _label: Label = label(ViewBuilder())

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

    internal class Serializer<Label : View> : KSerializer<Toggle<Label>> {
        val bindingSerializer = Binding.Serializer<Boolean>()
        val labelSerializer = PolymorphicSerializer(Any::class)

        override val descriptor: SerialDescriptor =
            buildClassSerialDescriptor("Toggle") {
                element("isOn", bindingSerializer.descriptor)
                element("label", labelSerializer.descriptor)
            }

        override fun serialize(encoder: Encoder, value: Toggle<Label>) =
            encoder.encodeStructure(descriptor) {
                encodeSerializableElement(descriptor, 0, bindingSerializer, value.__isOn)
                encodeSerializableElement(descriptor, 1, labelSerializer, value._label)
            }

        override fun deserialize(decoder: Decoder): Toggle<Label> =
            decoder.decodeStructure(descriptor) {
                lateinit var isOn: Binding<Boolean>
                lateinit var label: Any
                while (true) {
                    when (val index = decodeElementIndex(descriptor)) {
                        0 -> isOn = decodeSerializableElement(descriptor, 0, bindingSerializer)
                        1 -> label = decodeSerializableElement(descriptor, 1, labelSerializer)
                        CompositeDecoder.DECODE_DONE -> break
                        else -> error("Unexpected index: $index")
                    }
                }
                Toggle(isOn) { label as Label }
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
