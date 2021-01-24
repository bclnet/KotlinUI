package kotlinx.kotlinui

import kotlinx.ptype.PType
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.*
import kotlinx.serialization.encoding.*
import kotlin.reflect.KType

@Serializable(with = LabelStyleStyleModifier.Serializer::class)
internal data class LabelStyleStyleModifier<Style>(
    val style: Style
) : ViewModifier {
//    fun body(content: AnyView) : AnyView = action(AnyView { content })

    //: Codable
    internal class Serializer<Style>(private val styleType: KType, private val styleSerializer: KSerializer<Style>) : KSerializer<LabelStyleStyleModifier<Style>> {
        override val descriptor: SerialDescriptor =
            buildClassSerialDescriptor("LabelStyleStyleModifier") {
                element<String>("style")
            }

        override fun serialize(encoder: Encoder, value: LabelStyleStyleModifier<Style>) =
            encoder.encodeStructure(descriptor) {
                val styleKey = PType.typeKey(styleType)
                encodeStringElement(descriptor, 0, styleKey)
            }

        override fun deserialize(decoder: Decoder): LabelStyleStyleModifier<Style> =
            decoder.decodeStructure(descriptor) {
                lateinit var styleKey: String
                while (true) {
                    when (val index = decodeElementIndex(descriptor)) {
                        0 -> styleKey = decodeStringElement(descriptor, 0)
                        CompositeDecoder.DECODE_DONE -> break
                        else -> error("Unexpected index: $index")
                    }
                }
                LabelStyleStyleModifier(PType.findAction<() -> Style>(styleKey, "style")!!())
            }
    }

    companion object {
        //: Register
        fun register() {
            PType.register<LabelStyleStyleModifier<Any>>()
            PType.register<DefaultLabelStyle>(actions = hashMapOf("style" to ::DefaultLabelStyle))
            PType.register<IconOnlyLabelStyle>(actions = hashMapOf("style" to ::IconOnlyLabelStyle))
            PType.register<TitleOnlyLabelStyle>(actions = hashMapOf("style" to ::TitleOnlyLabelStyle))
        }
    }
}

interface LabelStyle {
    fun makeBody(configuration: LabelStyleConfiguration): View
}

class DefaultLabelStyle : LabelStyle {
    override fun makeBody(configuration: LabelStyleConfiguration): View = configuration.title
}

class IconOnlyLabelStyle : LabelStyle {
    override fun makeBody(configuration: LabelStyleConfiguration): View = configuration.icon
}

class TitleOnlyLabelStyle : LabelStyle {
    override fun makeBody(configuration: LabelStyleConfiguration): View = configuration.title
}

class LabelStyleConfiguration {
    class Icon : View {
        override val body: View
            get() = error("Never")
    }

    class Title : View {
        override val body: View
            get() = error("Never")
    }

    var icon: Icon = Icon()
    var title: Title = Title()
}