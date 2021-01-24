package kotlinx.kotlinui

import kotlinx.ptype.PType
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.*
import kotlinx.serialization.encoding.*
import kotlin.reflect.KType

@Serializable(with = MenuStyleModifier.Serializer::class)
internal data class MenuStyleModifier<Style>(
    val style: Style
) : ViewModifier {
//    fun body(content: AnyView) : AnyView = action(AnyView { content })

    //: Codable
    internal class Serializer<Style>(private val styleType: KType, private val styleSerializer: KSerializer<Style>) : KSerializer<MenuStyleModifier<Style>> {
        override val descriptor: SerialDescriptor =
            buildClassSerialDescriptor("MenuStyleModifier") {
                element<String>("style")
            }

        override fun serialize(encoder: Encoder, value: MenuStyleModifier<Style>) =
            encoder.encodeStructure(descriptor) {
                val styleKey = PType.typeKey(styleType)
                encodeStringElement(descriptor, 0, styleKey)
            }

        override fun deserialize(decoder: Decoder): MenuStyleModifier<Style> =
            decoder.decodeStructure(descriptor) {
                lateinit var styleKey: String
                while (true) {
                    when (val index = decodeElementIndex(descriptor)) {
                        0 -> styleKey = decodeStringElement(descriptor, 0)
                        CompositeDecoder.DECODE_DONE -> break
                        else -> error("Unexpected index: $index")
                    }
                }
                MenuStyleModifier(PType.findAction<() -> Style>(styleKey, "style")!!())
            }
    }

    companion object {
        //: Register
        fun register() {
            PType.register<MenuStyleModifier<Any>>()
            PType.register<BorderlessButtonMenuStyle>(actions = hashMapOf("style" to ::BorderlessButtonMenuStyle))
            PType.register<DefaultMenuStyle>(actions = hashMapOf("style" to ::DefaultMenuStyle))
            PType.register<BorderedButtonMenuStyle>(actions = hashMapOf("style" to ::BorderedButtonMenuStyle))
        }
    }
}

interface MenuStyle {
    fun makeBody(configuration: MenuStyleConfiguration): View
}

class BorderlessButtonMenuStyle : MenuStyle {
    override fun makeBody(configuration: MenuStyleConfiguration): View = configuration.label
}

class DefaultMenuStyle : MenuStyle {
    override fun makeBody(configuration: MenuStyleConfiguration): View = configuration.label
}

class BorderedButtonMenuStyle : MenuStyle {
    override fun makeBody(configuration: MenuStyleConfiguration): View = configuration.label
}

class MenuStyleConfiguration {
    class Content : View {
        override val body: View
            get() = error("Never")
    }

    class Label : View {
        override val body: View
            get() = error("Never")
    }

    internal var label: Label = Label()
}