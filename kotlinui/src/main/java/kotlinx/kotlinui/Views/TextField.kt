package kotlinx.kotlinui

import kotlinx.ptype.PType
import kotlinx.serialization.*
import kotlinx.serialization.descriptors.*
import kotlinx.serialization.encoding.*

//@Serializable(with = TextFieldSerializer::class)
class TextField<Label : Text> internal constructor(
    val _text: Binding<String>,
    val label: Label,
    val onEditingChanged: (Boolean) -> Unit,
    val onCommit: () -> Unit,
) : View {
    constructor(title: String, text: Label, onEditingChanged: (Boolean) -> Unit, onCommit: () -> Unit)
        : this(Binding.constant(title), text, onEditingChanged, onCommit) {
    }

    constructor(titleKey: LocalizedStringKey, text: Label, onEditingChanged: (Boolean) -> Unit, onCommit: () -> Unit)
        : this(Binding.constant(titleKey.key), text, onEditingChanged, onCommit) {
    }

    override val body: Text
        get() = label

    companion object {
        fun register() {
            PType.register<TextField<Text>>()
        }
    }
}

//class TextFieldSerializer<Label : Text>(private val labelSerializer: KSerializer<Label>) : KSerializer<TextField<Label>> {
//    override val descriptor: SerialDescriptor =
//        buildClassSerialDescriptor("TextField") {
//            element<String>("title")
//            element<String>("titleKey")
//            element<String>("text")
//            element<(Boolean) -> Unit>("onEditingChanged")
//            element<() -> Unit>("onCommit")
//        }
//
//    override fun serialize(encoder: Encoder, value: TextField<Label>) =
//        encoder.encodeStructure(descriptor) {
//            encodeSerializableElement(descriptor, 0, serializer<String>(), value._text)
//            encodeSerializableElement(descriptor, 2, labelSerializer, value._label)
//        }
//
//    @ExperimentalSerializationApi
//    override fun deserialize(decoder: Decoder): TextField<Label> =
//        decoder.decodeStructure(descriptor) {
//            val text = decodeSerializableElement(descriptor, 0, String.serializer())
//            val label = decodeSerializableElement(descriptor, 1, labelSerializer)
//            TextField(isOn) { label }
//        }
//}
