package kotlinx.kotlinui

import kotlinx.ptype.PType
import kotlinx.serialization.*
import kotlinx.serialization.descriptors.*
import kotlinx.serialization.encoding.*
import kotlin.reflect.KType

@Serializable //(with = GroupBoxStyleModifier.Serializer::class)
@SerialName(":GroupBoxStyleModifier")
internal data class GroupBoxStyleModifier<Style : GroupBoxStyle>(
    @Polymorphic val style: Style
) : ViewModifier {
//    fun body(content: AnyView) : AnyView = action(AnyView { content })

    //: Codable
    internal class Serializer<Style : GroupBoxStyle> : KSerializer<GroupBoxStyleModifier<Style>> {
        val styleSerializer = PolymorphicSerializer(Any::class)

        override val descriptor: SerialDescriptor =
            buildClassSerialDescriptor("GroupBoxStyleModifier") {
                element("style", styleSerializer.descriptor)
            }

        override fun serialize(encoder: Encoder, value: GroupBoxStyleModifier<Style>) =
            encoder.encodeStructure(descriptor) {
                encodeSerializableElement(descriptor, 0, styleSerializer, value.style)
            }

        override fun deserialize(decoder: Decoder): GroupBoxStyleModifier<Style> =
            decoder.decodeStructure(descriptor) {
                lateinit var style: Style
                while (true) {
                    when (val index = decodeElementIndex(descriptor)) {
                        0 -> style = decodeSerializableElement(descriptor, 0, styleSerializer) as Style
                        CompositeDecoder.DECODE_DONE -> break
                        else -> error("Unexpected index: $index")
                    }
                }
                GroupBoxStyleModifier(style)
            }
    }

    companion object {
        //: Register
        fun register() {
            PType.register<GroupBoxStyleModifier<GroupBoxStyle>>()
            PType.register<DefaultGroupBoxStyle>(actions = hashMapOf("style" to ::DefaultGroupBoxStyle))
        }
    }
}

interface GroupBoxStyle {
    fun makeBody(configuration: GroupBoxStyleConfiguration): View
}

@Serializable
@SerialName(":DefaultGroupBoxStyle")
class DefaultGroupBoxStyle : GroupBoxStyle {
    override fun equals(other: Any?): Boolean = other is DefaultGroupBoxStyle
    override fun hashCode(): Int = javaClass.hashCode()
    override fun makeBody(configuration: GroupBoxStyleConfiguration): View = configuration.label
}

class GroupBoxStyleConfiguration {
    class Content : View {
        override val body: View
            get() = error("Never")
    }

    class Label : View {
        override val body: View
            get() = error("Never")
    }

    internal var label: Label = Label()
}