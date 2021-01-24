package kotlinx.kotlinui

import kotlinx.ptype.PType
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.*
import kotlinx.serialization.encoding.*
import kotlin.reflect.KType

@Serializable(with = _TabViewStyleWriter.Serializer::class)
internal data class _TabViewStyleWriter<Style>(
    val style: Style
) : ViewModifier {
//    fun body(content: AnyView) : AnyView = action(AnyView { content })

    //: Codable
    internal class Serializer<Style>(private val styleType: KType, private val styleSerializer: KSerializer<Style>) : KSerializer<_TabViewStyleWriter<Style>> {
        override val descriptor: SerialDescriptor =
            buildClassSerialDescriptor("_TabViewStyleWriter") {
                element<String>("style")
            }

        override fun serialize(encoder: Encoder, value: _TabViewStyleWriter<Style>) =
            encoder.encodeStructure(descriptor) {
                val styleKey = PType.typeKey(styleType)
                encodeStringElement(descriptor, 0, styleKey)
            }

        override fun deserialize(decoder: Decoder): _TabViewStyleWriter<Style> =
            decoder.decodeStructure(descriptor) {
                lateinit var styleKey: String
                while (true) {
                    when (val index = decodeElementIndex(descriptor)) {
                        0 -> styleKey = decodeStringElement(descriptor, 0)
                        CompositeDecoder.DECODE_DONE -> break
                        else -> error("Unexpected index: $index")
                    }
                }
                _TabViewStyleWriter(PType.findAction<() -> Style>(styleKey, "style")!!())
            }
    }

    companion object {
        //: Register
        fun register() {
            PType.register<_TabViewStyleWriter<Any>>()
            PType.register<DefaultTabViewStyle>(actions = hashMapOf("style" to ::DefaultTabViewStyle))
            PType.register<PageTabViewStyle>(actions = hashMapOf("style" to ::PageTabViewStyle))
            PType.register<CarouselTabViewStyle>(actions = hashMapOf("style" to ::CarouselTabViewStyle))
        }
    }
}

interface TabViewStyle

class DefaultTabViewStyle : TabViewStyle
class PageTabViewStyle : TabViewStyle
class CarouselTabViewStyle : TabViewStyle