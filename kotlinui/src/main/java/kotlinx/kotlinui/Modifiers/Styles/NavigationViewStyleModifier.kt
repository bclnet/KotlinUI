package kotlinx.kotlinui

import kotlinx.ptype.PType
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.*
import kotlinx.serialization.encoding.*
import kotlin.reflect.KType

@Serializable(with = NavigationViewStyleModifier.Serializer::class)
internal data class NavigationViewStyleModifier<Style>(
    val style: Style
) : ViewModifier {
//    fun body(content: AnyView) : AnyView = action(AnyView { content })

    //: Codable
    internal class Serializer<Style>(private val styleType: KType, private val styleSerializer: KSerializer<Style>) : KSerializer<NavigationViewStyleModifier<Style>> {
        override val descriptor: SerialDescriptor =
            buildClassSerialDescriptor("NavigationViewStyleModifier") {
                element<String>("style")
            }

        override fun serialize(encoder: Encoder, value: NavigationViewStyleModifier<Style>) =
            encoder.encodeStructure(descriptor) {
                val styleKey = PType.typeKey(styleType)
                encodeStringElement(descriptor, 0, styleKey)
            }

        override fun deserialize(decoder: Decoder): NavigationViewStyleModifier<Style> =
            decoder.decodeStructure(descriptor) {
                lateinit var styleKey: String
                while (true) {
                    when (val index = decodeElementIndex(descriptor)) {
                        0 -> styleKey = decodeStringElement(descriptor, 0)
                        CompositeDecoder.DECODE_DONE -> break
                        else -> error("Unexpected index: $index")
                    }
                }
                NavigationViewStyleModifier(PType.findAction<() -> Style>(styleKey, "style")!!())
            }
    }

    companion object {
        //: Register
        fun register() {
            PType.register<NavigationViewStyleModifier<Any>>()
            PType.register<DefaultNavigationViewStyle>(actions = hashMapOf("style" to ::DefaultNavigationViewStyle))
            PType.register<DoubleColumnNavigationViewStyle>(actions = hashMapOf("style" to ::DoubleColumnNavigationViewStyle))
            PType.register<StackNavigationViewStyle>(actions = hashMapOf("style" to ::StackNavigationViewStyle))
        }
    }
}

interface NavigationViewStyle

class DefaultNavigationViewStyle : NavigationViewStyle
class DoubleColumnNavigationViewStyle : NavigationViewStyle
class StackNavigationViewStyle : NavigationViewStyle

