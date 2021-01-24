package kotlinx.kotlinui

import kotlinx.ptype.PType
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.*
import kotlinx.serialization.encoding.*
import kotlin.reflect.KType

@Serializable(with = TextFieldStyleStyleModifier.Serializer::class)
internal data class TextFieldStyleStyleModifier<Style>(
    val style: Style
) : ViewModifier {
//    fun body(content: AnyView) : AnyView = action(AnyView { content })

    //: Codable
    internal class Serializer<Style>(private val styleType: KType, private val styleSerializer: KSerializer<Style>) : KSerializer<TextFieldStyleStyleModifier<Style>> {
        override val descriptor: SerialDescriptor =
            buildClassSerialDescriptor("TextFieldStyleStyleModifier") {
                element<String>("style")
            }

        override fun serialize(encoder: Encoder, value: TextFieldStyleStyleModifier<Style>) =
            encoder.encodeStructure(descriptor) {
                val styleKey = PType.typeKey(styleType)
                encodeStringElement(descriptor, 0, styleKey)
            }

        override fun deserialize(decoder: Decoder): TextFieldStyleStyleModifier<Style> =
            decoder.decodeStructure(descriptor) {
                lateinit var styleKey: String
                while (true) {
                    when (val index = decodeElementIndex(descriptor)) {
                        0 -> styleKey = decodeStringElement(descriptor, 0)
                        CompositeDecoder.DECODE_DONE -> break
                        else -> error("Unexpected index: $index")
                    }
                }
                TextFieldStyleStyleModifier(PType.findAction<() -> Style>(styleKey, "style")!!())
            }
    }

    companion object {
        //: Register
        fun register() {
            PType.register<TextFieldStyleStyleModifier<Any>>()
            PType.register<DefaultTextFieldStyle>(actions = hashMapOf("style" to ::DefaultTextFieldStyle))
            PType.register<PlainTextFieldStyle>(actions = hashMapOf("style" to ::PlainTextFieldStyle))
            PType.register<RoundedBorderTextFieldStyle>(actions = hashMapOf("style" to ::RoundedBorderTextFieldStyle))
            PType.register<SquareBorderTextFieldStyle>(actions = hashMapOf("style" to ::SquareBorderTextFieldStyle))
        }
    }
}

interface TextFieldStyle

class DefaultTextFieldStyle : TextFieldStyle
class PlainTextFieldStyle : TextFieldStyle
class RoundedBorderTextFieldStyle : TextFieldStyle
class SquareBorderTextFieldStyle : TextFieldStyle
