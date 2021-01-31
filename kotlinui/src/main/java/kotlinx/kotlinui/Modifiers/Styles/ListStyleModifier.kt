package kotlinx.kotlinui

import kotlinx.ptype.PType
import kotlinx.serialization.*
import kotlinx.serialization.descriptors.*
import kotlinx.serialization.encoding.*
import kotlin.reflect.KType

@Serializable(with = ListStyleModifier.Serializer::class)
@SerialName(":ListStyleModifier")
internal data class ListStyleModifier<Style : ListStyle>(
    @Polymorphic val style: Style
) : ViewModifier {
//    fun body(content: AnyView) : AnyView = action(AnyView { content })

    //: Codable
    internal class Serializer<Style : ListStyle> : KSerializer<ListStyleModifier<Style>> {
        val styleSerializer = PolymorphicSerializer(Any::class)

        override val descriptor: SerialDescriptor =
            buildClassSerialDescriptor("ListStyleModifier") {
                element<String>("style")
            }

        override fun serialize(encoder: Encoder, value: ListStyleModifier<Style>) =
            encoder.encodeStructure(descriptor) {
                encodeSerializableElement(descriptor, 0, styleSerializer, value.style)
            }

        override fun deserialize(decoder: Decoder): ListStyleModifier<Style> =
            decoder.decodeStructure(descriptor) {
                lateinit var style: Style
                while (true) {
                    when (val index = decodeElementIndex(descriptor)) {
                        0 -> style = decodeSerializableElement(descriptor, 0, styleSerializer) as Style
                        CompositeDecoder.DECODE_DONE -> break
                        else -> error("Unexpected index: $index")
                    }
                }
                ListStyleModifier(style)
            }
    }

    companion object {
        //: Register
        fun register() {
            PType.register<ListStyleModifier<ListStyle>>()
            PType.register<DefaultListStyle>(actions = hashMapOf("style" to ::DefaultListStyle))
            PType.register<GroupedListStyle>(actions = hashMapOf("style" to ::GroupedListStyle))
            PType.register<InsetGroupedListStyle>(actions = hashMapOf("style" to ::InsetGroupedListStyle))
            PType.register<InsetListStyle>(actions = hashMapOf("style" to ::InsetListStyle))
            PType.register<PlainListStyle>(actions = hashMapOf("style" to ::PlainListStyle))
            PType.register<SidebarListStyle>(actions = hashMapOf("style" to ::SidebarListStyle))
            PType.register<CarouselListStyle>(actions = hashMapOf("style" to ::CarouselListStyle))
            PType.register<EllipticalListStyle>(actions = hashMapOf("style" to ::EllipticalListStyle))
        }
    }
}

interface ListStyle

@Serializable
@SerialName(":DefaultListStyle")
class DefaultListStyle : ListStyle {
    override fun equals(other: Any?): Boolean = other is DefaultListStyle
    override fun hashCode(): Int = javaClass.hashCode()
}

@Serializable
@SerialName(":GroupedListStyle")
class GroupedListStyle : ListStyle {
    override fun equals(other: Any?): Boolean = other is GroupedListStyle
    override fun hashCode(): Int = javaClass.hashCode()
}

@Serializable
@SerialName(":InsetGroupedListStyle")
class InsetGroupedListStyle : ListStyle {
    override fun equals(other: Any?): Boolean = other is InsetGroupedListStyle
    override fun hashCode(): Int = javaClass.hashCode()
}

@Serializable
@SerialName(":InsetListStyle")
class InsetListStyle : ListStyle {
    override fun equals(other: Any?): Boolean = other is InsetListStyle
    override fun hashCode(): Int = javaClass.hashCode()
}

@Serializable
@SerialName(":PlainListStyle")
class PlainListStyle : ListStyle {
    override fun equals(other: Any?): Boolean = other is PlainListStyle
    override fun hashCode(): Int = javaClass.hashCode()
}

@Serializable
@SerialName(":SidebarListStyle")
class SidebarListStyle : ListStyle {
    override fun equals(other: Any?): Boolean = other is SidebarListStyle
    override fun hashCode(): Int = javaClass.hashCode()
}

@Serializable
@SerialName(":CarouselListStyle")
class CarouselListStyle : ListStyle {
    override fun equals(other: Any?): Boolean = other is CarouselListStyle
    override fun hashCode(): Int = javaClass.hashCode()
}

@Serializable
@SerialName(":EllipticalListStyle")
class EllipticalListStyle : ListStyle {
    override fun equals(other: Any?): Boolean = other is EllipticalListStyle
    override fun hashCode(): Int = javaClass.hashCode()
}

