package kotlinx.kotlinui

import kotlinx.ptype.PType
import kotlinx.serialization.*
import kotlinx.serialization.descriptors.*
import kotlinx.serialization.encoding.*
import kotlin.reflect.KType

@Serializable(with = _TabViewStyleWriter.Serializer::class)
@SerialName(":_TabViewStyleWriter")
internal data class _TabViewStyleWriter<Style : TabViewStyle>(
    @Polymorphic val style: Style
) : ViewModifier {
//    fun body(content: AnyView) : AnyView = action(AnyView { content })

    //: Codable
    internal class Serializer<Style : TabViewStyle> : KSerializer<_TabViewStyleWriter<Style>> {
        val styleSerializer = PolymorphicSerializer(Any::class)

        override val descriptor: SerialDescriptor =
            buildClassSerialDescriptor("ButtonStyleModifier") {
                element("style", styleSerializer.descriptor)
            }

        override fun serialize(encoder: Encoder, value: _TabViewStyleWriter<Style>) =
            encoder.encodeStructure(descriptor) {
                encodeSerializableElement(descriptor, 0, styleSerializer, value.style)
            }

        override fun deserialize(decoder: Decoder): _TabViewStyleWriter<Style> =
            decoder.decodeStructure(descriptor) {
                lateinit var style: Style
                while (true) {
                    when (val index = decodeElementIndex(descriptor)) {
                        0 -> style = decodeSerializableElement(descriptor, 0, styleSerializer) as Style
                        CompositeDecoder.DECODE_DONE -> break
                        else -> error("Unexpected index: $index")
                    }
                }
                _TabViewStyleWriter(style)
            }
    }

    companion object {
        //: Register
        fun register() {
            PType.register<_TabViewStyleWriter<TabViewStyle>>()
            PType.register<DefaultTabViewStyle>()
            PType.register<PageTabViewStyle>()
            PType.register<CarouselTabViewStyle>()
        }
    }
}

interface TabViewStyle

@Serializable
@SerialName(":DefaultTabViewStyle")
class DefaultTabViewStyle : TabViewStyle {
    override fun equals(other: Any?): Boolean = other is DefaultTabViewStyle
    override fun hashCode(): Int = javaClass.hashCode()
}

@Serializable
@SerialName(":PageTabViewStyle")
class PageTabViewStyle : TabViewStyle {
    override fun equals(other: Any?): Boolean = other is PageTabViewStyle
    override fun hashCode(): Int = javaClass.hashCode()
}

@Serializable
@SerialName(":CarouselTabViewStyle")
class CarouselTabViewStyle : TabViewStyle {
    override fun equals(other: Any?): Boolean = other is CarouselTabViewStyle
    override fun hashCode(): Int = javaClass.hashCode()
}