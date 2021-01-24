package kotlinx.kotlinui

import kotlinx.ptype.PType
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.*
import kotlinx.serialization.encoding.*
import kotlin.reflect.KType

@Serializable(with = ListStyleModifier.Serializer::class)
internal data class ListStyleModifier<Style>(
    val style: Style
) : ViewModifier {
//    fun body(content: AnyView) : AnyView = action(AnyView { content })

    //: Codable
    internal class Serializer<Style>(private val styleType: KType, private val styleSerializer: KSerializer<Style>) : KSerializer<ListStyleModifier<Style>> {
        override val descriptor: SerialDescriptor =
            buildClassSerialDescriptor("ListStyleModifier") {
                element<String>("style")
            }

        override fun serialize(encoder: Encoder, value: ListStyleModifier<Style>) =
            encoder.encodeStructure(descriptor) {
                val styleKey = PType.typeKey(styleType)
                encodeStringElement(descriptor, 0, styleKey)
            }

        override fun deserialize(decoder: Decoder): ListStyleModifier<Style> =
            decoder.decodeStructure(descriptor) {
                lateinit var styleKey: String
                while (true) {
                    when (val index = decodeElementIndex(descriptor)) {
                        0 -> styleKey = decodeStringElement(descriptor, 0)
                        CompositeDecoder.DECODE_DONE -> break
                        else -> error("Unexpected index: $index")
                    }
                }
                ListStyleModifier(PType.findAction<() -> Style>(styleKey, "style")!!())
            }
    }

    companion object {
        //: Register
        fun register() {
            PType.register<ListStyleModifier<Any>>()
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

class DefaultListStyle : ListStyle
class GroupedListStyle : ListStyle
class InsetGroupedListStyle : ListStyle
class InsetListStyle : ListStyle
class PlainListStyle : ListStyle
class SidebarListStyle : ListStyle
class CarouselListStyle : ListStyle
class EllipticalListStyle : ListStyle

