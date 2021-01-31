package kotlinx.kotlinui

import kotlinx.ptype.PType
import kotlinx.serialization.*
import kotlinx.serialization.descriptors.*
import kotlinx.serialization.encoding.*
import kotlin.reflect.KType

@Serializable(with = NavigationViewStyleModifier.Serializer::class)
@SerialName(":NavigationViewStyleModifier")
internal data class NavigationViewStyleModifier<Style : NavigationViewStyle>(
    @Polymorphic val style: Style
) : ViewModifier {
//    fun body(content: AnyView) : AnyView = action(AnyView { content })

    //: Codable
    internal class Serializer<Style : NavigationViewStyle> : KSerializer<NavigationViewStyleModifier<Style>> {
        val styleSerializer = PolymorphicSerializer(Any::class)

        override val descriptor: SerialDescriptor =
            buildClassSerialDescriptor("NavigationViewStyleModifier") {
                element<String>("style")
            }

        override fun serialize(encoder: Encoder, value: NavigationViewStyleModifier<Style>) =
            encoder.encodeStructure(descriptor) {
                encodeSerializableElement(descriptor, 0, styleSerializer, value.style)
            }

        override fun deserialize(decoder: Decoder): NavigationViewStyleModifier<Style> =
            decoder.decodeStructure(descriptor) {
                lateinit var style: Style
                while (true) {
                    when (val index = decodeElementIndex(descriptor)) {
                        0 -> style = decodeSerializableElement(descriptor, 0, styleSerializer) as Style
                        CompositeDecoder.DECODE_DONE -> break
                        else -> error("Unexpected index: $index")
                    }
                }
                NavigationViewStyleModifier(style)
            }
    }

    companion object {
        //: Register
        fun register() {
            PType.register<NavigationViewStyleModifier<NavigationViewStyle>>()
            PType.register<DefaultNavigationViewStyle>(actions = hashMapOf("style" to ::DefaultNavigationViewStyle))
            PType.register<DoubleColumnNavigationViewStyle>(actions = hashMapOf("style" to ::DoubleColumnNavigationViewStyle))
            PType.register<StackNavigationViewStyle>(actions = hashMapOf("style" to ::StackNavigationViewStyle))
        }
    }
}

interface NavigationViewStyle

@Serializable
@SerialName(":DefaultNavigationViewStyle")
class DefaultNavigationViewStyle : NavigationViewStyle {
    override fun equals(other: Any?): Boolean = other is DefaultNavigationViewStyle
    override fun hashCode(): Int = javaClass.hashCode()
}

@Serializable
@SerialName(":DoubleColumnNavigationViewStyle")
class DoubleColumnNavigationViewStyle : NavigationViewStyle {
    override fun equals(other: Any?): Boolean = other is DoubleColumnNavigationViewStyle
    override fun hashCode(): Int = javaClass.hashCode()
}

@Serializable
@SerialName(":StackNavigationViewStyle")
class StackNavigationViewStyle : NavigationViewStyle {
    override fun equals(other: Any?): Boolean = other is StackNavigationViewStyle
    override fun hashCode(): Int = javaClass.hashCode()
}

