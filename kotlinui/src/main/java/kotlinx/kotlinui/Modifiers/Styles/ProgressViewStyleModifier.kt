package kotlinx.kotlinui

import kotlinx.ptype.PType
import kotlinx.serialization.*
import kotlinx.serialization.descriptors.*
import kotlinx.serialization.encoding.*
import kotlin.reflect.KType

@Serializable(with = ProgressViewStyleModifier.Serializer::class)
@SerialName(":ProgressViewStyleModifier")
internal data class ProgressViewStyleModifier<Style : ProgressViewStyle>(
    @Polymorphic val style: Style
) : ViewModifier {
//    fun body(content: AnyView) : AnyView = action(AnyView { content })

    //: Codable
    internal class Serializer<Style : ProgressViewStyle> : KSerializer<ProgressViewStyleModifier<Style>> {
        val styleSerializer = PolymorphicSerializer(Any::class)

        override val descriptor: SerialDescriptor =
            buildClassSerialDescriptor("ProgressViewStyleModifier") {
                element<String>("style")
            }

        override fun serialize(encoder: Encoder, value: ProgressViewStyleModifier<Style>) =
            encoder.encodeStructure(descriptor) {
                encodeSerializableElement(descriptor, 0, styleSerializer, value.style)
            }

        override fun deserialize(decoder: Decoder): ProgressViewStyleModifier<Style> =
            decoder.decodeStructure(descriptor) {
                lateinit var style: Style
                while (true) {
                    when (val index = decodeElementIndex(descriptor)) {
                        0 -> style = decodeSerializableElement(descriptor, 0, styleSerializer) as Style
                        CompositeDecoder.DECODE_DONE -> break
                        else -> error("Unexpected index: $index")
                    }
                }
                ProgressViewStyleModifier(style)
            }
    }

    companion object {
        //: Register
        fun register() {
            PType.register<ProgressViewStyleModifier<ProgressViewStyle>>()
            PType.register<CircularProgressViewStyle>(actions = hashMapOf("style" to ::CircularProgressViewStyle))
            PType.register<DefaultProgressViewStyle>(actions = hashMapOf("style" to ::DefaultProgressViewStyle))
            PType.register<LinearProgressViewStyle>(actions = hashMapOf("style" to ::LinearProgressViewStyle))
        }
    }
}

interface ProgressViewStyle {
    fun makeBody(configuration: ProgressViewStyleConfiguration): View
}

@Serializable
@SerialName(":CircularProgressViewStyle")
class CircularProgressViewStyle : ProgressViewStyle {
    override fun equals(other: Any?): Boolean = other is CircularProgressViewStyle
    override fun hashCode(): Int = javaClass.hashCode()
    override fun makeBody(configuration: ProgressViewStyleConfiguration): View = configuration.label
}

@Serializable
@SerialName(":DefaultProgressViewStyle")
class DefaultProgressViewStyle : ProgressViewStyle {
    override fun equals(other: Any?): Boolean = other is DefaultProgressViewStyle
    override fun hashCode(): Int = javaClass.hashCode()
    override fun makeBody(configuration: ProgressViewStyleConfiguration): View = configuration.label
}

@Serializable
@SerialName(":LinearProgressViewStyle")
class LinearProgressViewStyle : ProgressViewStyle {
    override fun equals(other: Any?): Boolean = other is LinearProgressViewStyle
    override fun hashCode(): Int = javaClass.hashCode()
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