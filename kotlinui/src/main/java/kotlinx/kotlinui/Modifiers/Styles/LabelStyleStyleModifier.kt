package kotlinx.kotlinui

import kotlinx.ptype.PType
import kotlinx.serialization.*
import kotlinx.serialization.descriptors.*
import kotlinx.serialization.encoding.*
import kotlin.reflect.KType

@Serializable(with = LabelStyleStyleModifier.Serializer::class)
internal data class LabelStyleStyleModifier<Style : LabelStyle>(
    @Polymorphic val style: Style
) : ViewModifier {
//    fun body(content: AnyView) : AnyView = action(AnyView { content })

    //: Codable
    internal class Serializer<Style : LabelStyle> : KSerializer<LabelStyleStyleModifier<Style>> {
        val styleSerializer = PolymorphicSerializer(Any::class)

        override val descriptor: SerialDescriptor =
            buildClassSerialDescriptor(":LabelStyleStyleModifier") {
                element<String>("style")
            }

        override fun serialize(encoder: Encoder, value: LabelStyleStyleModifier<Style>) =
            encoder.encodeStructure(descriptor) {
                encodeSerializableElement(descriptor, 0, styleSerializer, value.style)
            }

        override fun deserialize(decoder: Decoder): LabelStyleStyleModifier<Style> =
            decoder.decodeStructure(descriptor) {
                lateinit var style: Style
                while (true) {
                    when (val index = decodeElementIndex(descriptor)) {
                        0 -> style = decodeSerializableElement(descriptor, 0, styleSerializer) as Style
                        CompositeDecoder.DECODE_DONE -> break
                        else -> error("Unexpected index: $index")
                    }
                }
                LabelStyleStyleModifier(style as Style)
            }
    }

    companion object {
        //: Register
        fun register() {
            PType.register<LabelStyleStyleModifier<LabelStyle>>()
            PType.register<DefaultLabelStyle>()
            PType.register<IconOnlyLabelStyle>()
            PType.register<TitleOnlyLabelStyle>()
        }
    }
}

interface LabelStyle {
    fun makeBody(configuration: LabelStyleConfiguration): View
}

@Serializable
@SerialName(":DefaultLabelStyle")
class DefaultLabelStyle : LabelStyle {
    override fun equals(other: Any?): Boolean = other is DefaultLabelStyle
    override fun hashCode(): Int = javaClass.hashCode()
    override fun makeBody(configuration: LabelStyleConfiguration): View = configuration.title
}

@Serializable
@SerialName(":IconOnlyLabelStyle")
class IconOnlyLabelStyle : LabelStyle {
    override fun equals(other: Any?): Boolean = other is IconOnlyLabelStyle
    override fun hashCode(): Int = javaClass.hashCode()
    override fun makeBody(configuration: LabelStyleConfiguration): View = configuration.icon
}

@Serializable
@SerialName(":TitleOnlyLabelStyle")
class TitleOnlyLabelStyle : LabelStyle {
    override fun equals(other: Any?): Boolean = other is TitleOnlyLabelStyle
    override fun hashCode(): Int = javaClass.hashCode()
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