package kotlinx.kotlinui

import kotlinx.ptype.PType
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.*
import kotlinx.serialization.encoding.*
import kotlin.reflect.KType

@Serializable(with = GroupBoxStyleModifier.Serializer::class)
internal data class GroupBoxStyleModifier<Style>(
    val style: Style
) : ViewModifier {
//    fun body(content: AnyView) : AnyView = action(AnyView { content })

    //: Codable
    internal class Serializer<Style>(private val styleType: KType, private val styleSerializer: KSerializer<Style>) : KSerializer<GroupBoxStyleModifier<Style>> {
        override val descriptor: SerialDescriptor =
            buildClassSerialDescriptor("GroupBoxStyleModifier") {
                element<String>("style")
            }

        override fun serialize(encoder: Encoder, value: GroupBoxStyleModifier<Style>) =
            encoder.encodeStructure(descriptor) {
                val styleKey = PType.typeKey(styleType)
                encodeStringElement(descriptor, 0, styleKey)
            }

        override fun deserialize(decoder: Decoder): GroupBoxStyleModifier<Style> =
            decoder.decodeStructure(descriptor) {
                lateinit var styleKey: String
                while (true) {
                    when (val index = decodeElementIndex(descriptor)) {
                        0 -> styleKey = decodeStringElement(descriptor, 0)
                        CompositeDecoder.DECODE_DONE -> break
                        else -> error("Unexpected index: $index")
                    }
                }
                GroupBoxStyleModifier(PType.findAction<() -> Style>(styleKey, "style")!!())
            }
    }

    companion object {
        //: Register
        fun register() {
            PType.register<GroupBoxStyleModifier<Any>>()
            PType.register<DefaultGroupBoxStyle>(actions = hashMapOf("style" to ::DefaultGroupBoxStyle))
        }
    }
}

interface GroupBoxStyle {
    fun makeBody(configuration: GroupBoxStyleConfiguration): View
}

class DefaultGroupBoxStyle : GroupBoxStyle {
    override fun makeBody(configuration: GroupBoxStyleConfiguration): View = configuration.label
}

class GroupBoxStyleConfiguration {
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