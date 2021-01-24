package kotlinx.kotlinui

import kotlinx.ptype.PType
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.*
import kotlinx.serialization.encoding.*
import kotlin.reflect.KType

@Serializable(with = ButtonStyleModifier.Serializer::class)
internal data class ButtonStyleModifier<Style>(
    val style: Style
) : ViewModifier {
//    fun body(content: AnyView) : AnyView = action(AnyView { content })

    //: Codable
    internal class Serializer<Style>(private val styleType: KType, private val styleSerializer: KSerializer<Style>) : KSerializer<ButtonStyleModifier<Style>> {
        override val descriptor: SerialDescriptor =
            buildClassSerialDescriptor("ButtonStyleModifier") {
                element<String>("style")
            }

        override fun serialize(encoder: Encoder, value: ButtonStyleModifier<Style>) =
            encoder.encodeStructure(descriptor) {
                val styleKey = PType.typeKey(styleType)
                encodeStringElement(descriptor, 0, styleKey)
            }

        override fun deserialize(decoder: Decoder): ButtonStyleModifier<Style> =
            decoder.decodeStructure(descriptor) {
                lateinit var styleKey: String
                while (true) {
                    when (val index = decodeElementIndex(descriptor)) {
                        0 -> styleKey = decodeStringElement(descriptor, 0)
                        CompositeDecoder.DECODE_DONE -> break
                        else -> error("Unexpected index: $index")
                    }
                }
                ButtonStyleModifier(PType.findAction<() -> Style>(styleKey, "style")!!())
            }
    }

    companion object {
        //: Register
        fun register() {
            PType.register<ButtonStyleModifier<Any>>()
            PType.register<BorderlessButtonStyle>(actions = hashMapOf("style" to ::BorderlessButtonStyle))
            PType.register<DefaultButtonStyle>(actions = hashMapOf("style" to ::DefaultButtonStyle))
            PType.register<PlainButtonStyle>(actions = hashMapOf("style" to ::PlainButtonStyle))
            PType.register<BorderedButtonStyle>(actions = hashMapOf("style" to ::BorderedButtonStyle))
            PType.register<LinkButtonStyle>(actions = hashMapOf("style" to ::LinkButtonStyle))
            PType.register<CardButtonStyle>(actions = hashMapOf("style" to ::CardButtonStyle))
        }
    }
}

interface ButtonStyle {
    fun makeBody(configuration: ButtonStyleConfiguration): View
}

class BorderlessButtonStyle : ButtonStyle {
    override fun makeBody(configuration: ButtonStyleConfiguration): View = configuration.label
}

class DefaultButtonStyle : ButtonStyle {
    override fun makeBody(configuration: ButtonStyleConfiguration): View = configuration.label
}

class PlainButtonStyle : ButtonStyle {
    override fun makeBody(configuration: ButtonStyleConfiguration): View = configuration.label
}

class BorderedButtonStyle : ButtonStyle {
    override fun makeBody(configuration: ButtonStyleConfiguration): View = configuration.label
}

class LinkButtonStyle : ButtonStyle {
    override fun makeBody(configuration: ButtonStyleConfiguration): View = configuration.label
}

class CardButtonStyle : ButtonStyle {
    override fun makeBody(configuration: ButtonStyleConfiguration): View = configuration.label
}

class ButtonStyleConfiguration(val label: Label, val isPressed: Boolean) {
    class Label(val storage: Any) : View {
        override val body: Never
            get() = error("Never")
    }
}
