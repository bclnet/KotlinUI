package kotlinx.kotlinui

import android.view.View as XView
import kotlinx.ptype.*
import kotlinx.serialization.*
import kotlinx.serialization.descriptors.*
import kotlinx.serialization.encoding.*

@Serializable(with = TextField.Serializer::class)
data class TextField<Label : Text> internal constructor(
    val _text: Binding<String>,
    @Polymorphic val label: Label,
    val onEditingChanged: (Boolean) -> Unit,
    val onCommit: () -> Unit
) : View {
    constructor(title: String, text: Label, onEditingChanged: (Boolean) -> Unit, onCommit: () -> Unit) : this(Binding.constant(title), text, onEditingChanged, onCommit)
    constructor(titleKey: LocalizedStringKey, text: Label, onEditingChanged: (Boolean) -> Unit, onCommit: () -> Unit) : this(Binding.constant(titleKey.key), text, onEditingChanged, onCommit)

    override val body: Text
        get() = label

    //: Codable
    internal class Serializer<Label : Text> : KSerializer<TextField<Label>> {
        val labelSerializer = PolymorphicSerializer(Any::class)

        override val descriptor: SerialDescriptor =
            buildClassSerialDescriptor("TextField") {
                element<String>("text")
                element("label", labelSerializer.descriptor)
                element<(Boolean) -> Unit>("onEditingChanged")
                element<() -> Unit>("onCommit")
            }

        override fun serialize(encoder: Encoder, value: TextField<Label>) =
            encoder.encodeStructure(descriptor) {
                encodeSerializableElement(descriptor, 0, serializer(), value._text)
                encodeSerializableElement(descriptor, 1, labelSerializer, value.label)
                encodeAction1Element(descriptor, 2, value.onEditingChanged)
                encodeAction0Element(descriptor, 3, value.onCommit)
            }

        override fun deserialize(decoder: Decoder): TextField<Label> =
            decoder.decodeStructure(descriptor) {
                lateinit var text: Binding<String>
                lateinit var label: Label
                lateinit var onEditingChanged: (Boolean) -> Unit
                lateinit var onCommit: () -> Unit
                while (true) {
                    when (val index = decodeElementIndex(_VStackLayout.Serializer.descriptor)) {
                        0 -> text = decodeSerializableElement(descriptor, 0, serializer())
                        1 -> label = decodeSerializableElement(descriptor, 1, labelSerializer) as Label
                        2 -> onEditingChanged = decodeAction1Element(descriptor, 2)
                        3 -> onCommit = decodeAction0Element(descriptor, 3)
                        CompositeDecoder.DECODE_DONE -> break
                        else -> error("Unexpected index: $index")
                    }
                }
                TextField(text, label, onEditingChanged, onCommit)
            }
    }

    companion object {
        //: Register
        fun register() {
            PType.register<TextField<Text>>()
        }
    }
}


