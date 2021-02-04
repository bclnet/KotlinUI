package kotlinx.kotlinui

import android.icu.util.DateInterval
import kotlinx.ptype.PType
import kotlinx.serialization.*
import kotlinx.serialization.descriptors.*
import kotlinx.serialization.encoding.*

@Serializable(with = ZStack.Serializer::class)
class ZStack<Content : View>(
    alignment: Alignment = Alignment.center,
    content: ViewBuilder.() -> Content
) : View, IAnyView {
    override fun equals(other: Any?): Boolean = other is ZStack<*> && _tree == other._tree
    override fun hashCode(): Int = _tree.hashCode()

    val _tree: _VariadicView_Tree<_ZStackLayout, Content> =
        _VariadicView_Tree(_ZStackLayout(alignment), content(ViewBuilder))

    override val body: View
        get() = error("Not Implemented")

    override val anyView: AnyView
        get() = AnyView(this)

    //: Codable
    internal class Serializer<Content : View> : KSerializer<ZStack<Content>> {
        val contentSerializer = PolymorphicSerializer(Any::class)

        override val descriptor: SerialDescriptor =
            buildClassSerialDescriptor(":ZStack") {
                element<_ZStackLayout>("root")
                element<View>("content")
            }

        override fun serialize(encoder: Encoder, value: ZStack<Content>) =
            encoder.encodeStructure(descriptor) {
                val tree = value._tree
                val root = tree.root
                if (root.alignment != Alignment.center) encodeSerializableElement(descriptor, 0, _ZStackLayout.Serializer, root)
                encodeSerializableElement(descriptor, 1, contentSerializer, tree.content)
            }

        override fun deserialize(decoder: Decoder): ZStack<Content> =
            decoder.decodeStructure(descriptor) {
                var root = _ZStackLayout()
                lateinit var content: Content
                while (true) {
                    when (val index = decodeElementIndex(descriptor)) {
                        0 -> root = decodeSerializableElement(descriptor, 0, _ZStackLayout.Serializer)
                        1 -> content = decodeSerializableElement(descriptor, 1, contentSerializer) as Content
                        CompositeDecoder.DECODE_DONE -> break
                        else -> error("Unexpected index: $index")
                    }
                }
                ZStack(root.alignment) { content }
            }
    }

    companion object {
        //: Register
        fun register() {
            PType.register<ZStack<AnyView>>()
        }
    }
}

@Serializable(with = _ZStackLayout.Serializer::class)
data class _ZStackLayout(
    val alignment: Alignment = Alignment.center
) : _VariadicView_UnaryViewRoot {
    //: Codable
    internal object Serializer : KSerializer<_ZStackLayout> {
        override val descriptor: SerialDescriptor =
            buildClassSerialDescriptor(":_ZStackLayout") {
                element<Alignment>("alignment")
            }

        override fun serialize(encoder: Encoder, value: _ZStackLayout) =
            encoder.encodeStructure(descriptor) {
                encodeSerializableElement(descriptor, 0, Alignment.Serializer, value.alignment)
            }

        override fun deserialize(decoder: Decoder): _ZStackLayout =
            decoder.decodeStructure(descriptor) {
                lateinit var alignment: Alignment
                while (true) {
                    when (val index = decodeElementIndex(descriptor)) {
                        0 -> alignment = decodeSerializableElement(descriptor, 0, Alignment.Serializer)
                        CompositeDecoder.DECODE_DONE -> break
                        else -> error("Unexpected index: $index")
                    }
                }
                _ZStackLayout(alignment)
            }
    }
}

//internal fun <Content : View> ZStack<Content>._makeView(view: _GraphValue<ZStack<Content>>, inputs: _ViewInputs): _ViewOutputs = error("Not Implemented")