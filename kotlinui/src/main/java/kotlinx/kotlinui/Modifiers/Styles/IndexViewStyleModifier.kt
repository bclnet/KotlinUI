package kotlinx.kotlinui

import kotlinx.ptype.PType
import kotlinx.serialization.*
import kotlinx.serialization.descriptors.*
import kotlinx.serialization.encoding.*
import kotlin.reflect.KType

@Serializable //(with = IndexViewStyleModifier.Serializer::class)
@SerialName(":IndexViewStyleModifier")
internal data class IndexViewStyleModifier<Style : IndexViewStyle>(
    @Polymorphic val style: Style
) : ViewModifier {
//    fun body(content: AnyView) : AnyView = action(AnyView { content })

    //: Codable
    internal class Serializer<Style : IndexViewStyle> : KSerializer<IndexViewStyleModifier<Style>> {
        val styleSerializer = PolymorphicSerializer(Any::class)

        override val descriptor: SerialDescriptor =
            buildClassSerialDescriptor("IndexViewStyleModifier") {
                element<String>("style")
            }

        override fun serialize(encoder: Encoder, value: IndexViewStyleModifier<Style>) =
            encoder.encodeStructure(descriptor) {
                encodeSerializableElement(descriptor, 0, styleSerializer, value.style)
            }

        override fun deserialize(decoder: Decoder): IndexViewStyleModifier<Style> =
            decoder.decodeStructure(descriptor) {
                lateinit var style: Style
                while (true) {
                    when (val index = decodeElementIndex(descriptor)) {
                        0 -> style = decodeSerializableElement(descriptor, 0, styleSerializer) as Style
                        CompositeDecoder.DECODE_DONE -> break
                        else -> error("Unexpected index: $index")
                    }
                }
                IndexViewStyleModifier(style)
            }
    }

    companion object {
        //: Register
        fun register() {
            PType.register<IndexViewStyleModifier<IndexViewStyle>>()
            PType.register<PageIndexViewStyle>(actions = hashMapOf("style" to ::PageIndexViewStyle))
        }
    }
}

interface IndexViewStyle

@Serializable
@SerialName(":PageIndexViewStyle")
class PageIndexViewStyle : IndexViewStyle {
    override fun equals(other: Any?): Boolean = other is PageIndexViewStyle
    override fun hashCode(): Int = javaClass.hashCode()
}
