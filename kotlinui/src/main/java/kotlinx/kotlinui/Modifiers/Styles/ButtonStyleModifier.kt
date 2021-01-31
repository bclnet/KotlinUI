package kotlinx.kotlinui

import kotlinx.ptype.PType
import kotlinx.serialization.*
import kotlinx.serialization.descriptors.*
import kotlinx.serialization.encoding.*

@Serializable(with = ButtonStyleModifier.Serializer::class)
@SerialName(":ButtonStyleModifier")
internal data class ButtonStyleModifier<Style : ButtonStyle>(
    @Polymorphic val style: Style
) : ViewModifier {
//    fun body(content: AnyView) : AnyView = action(AnyView { content })

    //: Codable
    internal class Serializer<Style : ButtonStyle> : KSerializer<ButtonStyleModifier<Style>> {
        val styleSerializer = PolymorphicSerializer(Any::class)

        override val descriptor: SerialDescriptor =
            buildClassSerialDescriptor("ButtonStyleModifier") {
                element("style", styleSerializer.descriptor)
            }

        override fun serialize(encoder: Encoder, value: ButtonStyleModifier<Style>) =
            encoder.encodeStructure(descriptor) {
                encodeSerializableElement(descriptor, 0, styleSerializer, value.style)
            }

        override fun deserialize(decoder: Decoder): ButtonStyleModifier<Style> =
            decoder.decodeStructure(descriptor) {
                lateinit var style: Style
                while (true) {
                    when (val index = decodeElementIndex(descriptor)) {
                        0 -> style = decodeSerializableElement(descriptor, 0, styleSerializer) as Style
                        CompositeDecoder.DECODE_DONE -> break
                        else -> error("Unexpected index: $index")
                    }
                }
                ButtonStyleModifier(style)
            }
    }

    companion object {
        //: Register
        fun register() {
            PType.register<ButtonStyleModifier<ButtonStyle>>()
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

@Serializable
@SerialName(":BorderlessButtonStyle")
class BorderlessButtonStyle : ButtonStyle {
    override fun equals(other: Any?): Boolean = other is BorderlessButtonStyle
    override fun hashCode(): Int = javaClass.hashCode()
    override fun makeBody(configuration: ButtonStyleConfiguration): View = configuration.label
}

@Serializable
@SerialName(":DefaultButtonStyle")
class DefaultButtonStyle : ButtonStyle {
    override fun equals(other: Any?): Boolean = other is DefaultButtonStyle
    override fun hashCode(): Int = javaClass.hashCode()
    override fun makeBody(configuration: ButtonStyleConfiguration): View = configuration.label
}

@Serializable
@SerialName(":PlainButtonStyle")
class PlainButtonStyle : ButtonStyle {
    override fun equals(other: Any?): Boolean = other is PlainButtonStyle
    override fun hashCode(): Int = javaClass.hashCode()
    override fun makeBody(configuration: ButtonStyleConfiguration): View = configuration.label
}

@Serializable
@SerialName(":BorderedButtonStyle")
class BorderedButtonStyle : ButtonStyle {
    override fun equals(other: Any?): Boolean = other is BorderedButtonStyle
    override fun hashCode(): Int = javaClass.hashCode()
    override fun makeBody(configuration: ButtonStyleConfiguration): View = configuration.label
}

@Serializable
@SerialName(":LinkButtonStyle")
class LinkButtonStyle : ButtonStyle {
    override fun equals(other: Any?): Boolean = other is LinkButtonStyle
    override fun hashCode(): Int = javaClass.hashCode()
    override fun makeBody(configuration: ButtonStyleConfiguration): View = configuration.label
}

@Serializable
@SerialName(":CardButtonStyle")
class CardButtonStyle : ButtonStyle {
    override fun equals(other: Any?): Boolean = other is CardButtonStyle
    override fun hashCode(): Int = javaClass.hashCode()
    override fun makeBody(configuration: ButtonStyleConfiguration): View = configuration.label
}

data class ButtonStyleConfiguration(val label: Label, val isPressed: Boolean) {
    class Label(val storage: Any) : View {
        override val body: Never
            get() = error("Never")
    }
}
