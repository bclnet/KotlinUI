package kotlinx.kotlinui

import kotlinx.ptype.PType
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.*
import kotlinx.serialization.encoding.*
import kotlin.reflect.KType

@Serializable(with = ProgressViewStyleModifier.Serializer::class)
internal data class ProgressViewStyleModifier<Style>(
    val style: Style
) : ViewModifier {
//    fun body(content: AnyView) : AnyView = action(AnyView { content })

    //: Codable
    internal class Serializer<Style>(private val styleType: KType, private val styleSerializer: KSerializer<Style>) : KSerializer<ProgressViewStyleModifier<Style>> {
        override val descriptor: SerialDescriptor =
            buildClassSerialDescriptor("ProgressViewStyleModifier") {
                element<String>("style")
            }

        override fun serialize(encoder: Encoder, value: ProgressViewStyleModifier<Style>) =
            encoder.encodeStructure(descriptor) {
                val styleKey = PType.typeKey(styleType)
                encodeStringElement(descriptor, 0, styleKey)
            }

        override fun deserialize(decoder: Decoder): ProgressViewStyleModifier<Style> =
            decoder.decodeStructure(descriptor) {
                lateinit var styleKey: String
                while (true) {
                    when (val index = decodeElementIndex(descriptor)) {
                        0 -> styleKey = decodeStringElement(descriptor, 0)
                        CompositeDecoder.DECODE_DONE -> break
                        else -> error("Unexpected index: $index")
                    }
                }
                ProgressViewStyleModifier(PType.findAction<() -> Style>(styleKey, "style")!!())
            }
    }

    companion object {
        //: Register
        fun register() {
            PType.register<ProgressViewStyleModifier<Any>>()
            PType.register<CircularProgressViewStyle>(actions = hashMapOf("style" to ::CircularProgressViewStyle))
            PType.register<DefaultProgressViewStyle>(actions = hashMapOf("style" to ::DefaultProgressViewStyle))
            PType.register<LinearProgressViewStyle>(actions = hashMapOf("style" to ::LinearProgressViewStyle))
        }
    }
}

interface ProgressViewStyle {
    fun makeBody(configuration: ProgressViewStyleConfiguration): View
}

class CircularProgressViewStyle : ProgressViewStyle {
    override fun makeBody(configuration: ProgressViewStyleConfiguration): View = configuration.label
}

class DefaultProgressViewStyle : ProgressViewStyle {
    override fun makeBody(configuration: ProgressViewStyleConfiguration): View = configuration.label
}

class LinearProgressViewStyle : ProgressViewStyle {
    override fun makeBody(configuration: ProgressViewStyleConfiguration): View = configuration.label
}

class ProgressViewStyleConfiguration {
    class CurrentValueLabel : View {
        override val body: View
            get() = error("Never")
    }

    class Label : View {
        override val body: View
            get() = error("Never")
    }

    val currentValueLabel: CurrentValueLabel? = CurrentValueLabel()
    val label: Label = Label()
    val fractionCompleted: Double? = null
}