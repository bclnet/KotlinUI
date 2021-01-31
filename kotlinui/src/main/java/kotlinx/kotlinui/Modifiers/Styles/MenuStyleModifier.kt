package kotlinx.kotlinui

import kotlinx.ptype.PType
import kotlinx.serialization.*
import kotlinx.serialization.descriptors.*
import kotlinx.serialization.encoding.*
import kotlin.reflect.KType

@Serializable(with = MenuStyleModifier.Serializer::class)
@SerialName(":MenuStyleModifier")
internal data class MenuStyleModifier<Style : MenuStyle>(
    @Polymorphic val style: Style
) : ViewModifier {
//    fun body(content: AnyView) : AnyView = action(AnyView { content })

    //: Codable
    internal class Serializer<Style : MenuStyle> : KSerializer<MenuStyleModifier<Style>> {
        val styleSerializer = PolymorphicSerializer(Any::class)

        override val descriptor: SerialDescriptor =
            buildClassSerialDescriptor("MenuStyleModifier") {
                element<String>("style")
            }

        override fun serialize(encoder: Encoder, value: MenuStyleModifier<Style>) =
            encoder.encodeStructure(descriptor) {
                encodeSerializableElement(descriptor, 0, styleSerializer, value.style)
            }

        override fun deserialize(decoder: Decoder): MenuStyleModifier<Style> =
            decoder.decodeStructure(descriptor) {
                lateinit var style: Style
                while (true) {
                    when (val index = decodeElementIndex(descriptor)) {
                        0 -> style = decodeSerializableElement(descriptor, 0, styleSerializer) as Style
                        CompositeDecoder.DECODE_DONE -> break
                        else -> error("Unexpected index: $index")
                    }
                }
                MenuStyleModifier(style)
            }
    }

    companion object {
        //: Register
        fun register() {
            PType.register<MenuStyleModifier<MenuStyle>>()
            PType.register<BorderlessButtonMenuStyle>(actions = hashMapOf("style" to ::BorderlessButtonMenuStyle))
            PType.register<DefaultMenuStyle>(actions = hashMapOf("style" to ::DefaultMenuStyle))
            PType.register<BorderedButtonMenuStyle>(actions = hashMapOf("style" to ::BorderedButtonMenuStyle))
        }
    }
}

interface MenuStyle {
    fun makeBody(configuration: MenuStyleConfiguration): View
}

@Serializable
@SerialName(":BorderlessButtonMenuStyle")
class BorderlessButtonMenuStyle : MenuStyle {
    override fun equals(other: Any?): Boolean = other is BorderlessButtonMenuStyle
    override fun hashCode(): Int = javaClass.hashCode()
    override fun makeBody(configuration: MenuStyleConfiguration): View = configuration.label
}

@Serializable
@SerialName(":DefaultMenuStyle")
class DefaultMenuStyle : MenuStyle {
    override fun equals(other: Any?): Boolean = other is DefaultMenuStyle
    override fun hashCode(): Int = javaClass.hashCode()
    override fun makeBody(configuration: MenuStyleConfiguration): View = configuration.label
}

@Serializable
@SerialName(":BorderedButtonMenuStyle")
class BorderedButtonMenuStyle : MenuStyle {
    override fun equals(other: Any?): Boolean = other is BorderedButtonMenuStyle
    override fun hashCode(): Int = javaClass.hashCode()
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