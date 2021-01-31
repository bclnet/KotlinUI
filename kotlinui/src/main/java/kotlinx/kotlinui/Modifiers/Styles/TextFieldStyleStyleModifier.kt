package kotlinx.kotlinui

import kotlinx.ptype.PType
import kotlinx.serialization.*
import kotlinx.serialization.descriptors.*
import kotlinx.serialization.encoding.*
import kotlin.reflect.KType

@Serializable(with = TextFieldStyleStyleModifier.Serializer::class)
@SerialName(":TextFieldStyleStyleModifier")
internal data class TextFieldStyleStyleModifier<Style : TextFieldStyle>(
    @Polymorphic val style: Style
) : ViewModifier {
//    fun body(content: AnyView) : AnyView = action(AnyView { content })

    //: Codable
    internal class Serializer<Style : TextFieldStyle> : KSerializer<TextFieldStyleStyleModifier<Style>> {
        val styleSerializer = PolymorphicSerializer(Any::class)

        override val descriptor: SerialDescriptor =
            buildClassSerialDescriptor("TextFieldStyleStyleModifier") {
                element<String>("style")
            }

        override fun serialize(encoder: Encoder, value: TextFieldStyleStyleModifier<Style>) =
            encoder.encodeStructure(descriptor) {
                encodeSerializableElement(descriptor, 0, styleSerializer, value.style)
            }

        override fun deserialize(decoder: Decoder): TextFieldStyleStyleModifier<Style> =
            decoder.decodeStructure(descriptor) {
                lateinit var style: Style
                while (true) {
                    when (val index = decodeElementIndex(descriptor)) {
                        0 -> style = decodeSerializableElement(descriptor, 0, styleSerializer) as Style
                        CompositeDecoder.DECODE_DONE -> break
                        else -> error("Unexpected index: $index")
                    }
                }
                TextFieldStyleStyleModifier(style)
            }
    }

    companion object {
        //: Register
        fun register() {
            PType.register<TextFieldStyleStyleModifier<TextFieldStyle>>()
            PType.register<DefaultTextFieldStyle>(actions = hashMapOf("style" to ::DefaultTextFieldStyle))
            PType.register<PlainTextFieldStyle>(actions = hashMapOf("style" to ::PlainTextFieldStyle))
            PType.register<RoundedBorderTextFieldStyle>(actions = hashMapOf("style" to ::RoundedBorderTextFieldStyle))
            PType.register<SquareBorderTextFieldStyle>(actions = hashMapOf("style" to ::SquareBorderTextFieldStyle))
        }
    }
}

interface TextFieldStyle

@Serializable
@SerialName(":DefaultTextFieldStyle")
class DefaultTextFieldStyle : TextFieldStyle {
    override fun equals(other: Any?): Boolean = other is DefaultTextFieldStyle
    override fun hashCode(): Int = javaClass.hashCode()
}

@Serializable
@SerialName(":PlainTextFieldStyle")
class PlainTextFieldStyle : TextFieldStyle {
    override fun equals(other: Any?): Boolean = other is PlainTextFieldStyle
    override fun hashCode(): Int = javaClass.hashCode()
}

@Serializable
@SerialName(":RoundedBorderTextFieldStyle")
class RoundedBorderTextFieldStyle : TextFieldStyle {
    override fun equals(other: Any?): Boolean = other is RoundedBorderTextFieldStyle
    override fun hashCode(): Int = javaClass.hashCode()
}

@Serializable
@SerialName(":SquareBorderTextFieldStyle")
class SquareBorderTextFieldStyle : TextFieldStyle {
    override fun equals(other: Any?): Boolean = other is SquareBorderTextFieldStyle
    override fun hashCode(): Int = javaClass.hashCode()
}
