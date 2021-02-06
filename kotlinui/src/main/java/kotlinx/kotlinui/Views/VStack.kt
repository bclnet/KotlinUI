package kotlinx.kotlinui

import kotlinx.ptype.PType
import kotlinx.serialization.*
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.descriptors.*
import kotlinx.serialization.encoding.*

@Serializable(with = VStack.Serializer::class)
class VStack<Content : View>(
    alignment: HorizontalAlignment = HorizontalAlignment.center,
    spacing: Float? = null,
    content: ViewBuilder.() -> Content
) : View, IAnyView {
    override fun equals(other: Any?): Boolean = other is VStack<*> && _tree == other._tree
    override fun hashCode(): Int = _tree.hashCode()

    val _tree: _VariadicView_Tree<_VStackLayout, Content> =
        _VariadicView_Tree(_VStackLayout(alignment, spacing), content(ViewBuilder()))

    override val body: View
        get() = error("Not Implemented")

    override val anyView: AnyView
        get() = AnyView(this)

    //: Codable
    internal class Serializer<Content : View> : KSerializer<VStack<Content>> {
        val contentSerializer = PolymorphicSerializer(Any::class)

        override val descriptor: SerialDescriptor =
            buildClassSerialDescriptor(":VStack") {
                element<_VStackLayout>("root")
                element("content", contentSerializer.descriptor)
            }

        override fun serialize(encoder: Encoder, value: VStack<Content>) =
            encoder.encodeStructure(descriptor) {
                val tree = value._tree
                val root = tree.root
                if (root.alignment != HorizontalAlignment.center || root.spacing != null) encodeSerializableElement(descriptor, 0, _VStackLayout.Serializer, root)
                encodeSerializableElement(descriptor, 1, contentSerializer, tree.content)
            }

        override fun deserialize(decoder: Decoder): VStack<Content> =
            decoder.decodeStructure(descriptor) {
                var root = _VStackLayout()
                lateinit var content: Content
                while (true) {
                    when (val index = decodeElementIndex(descriptor)) {
                        0 -> root = decodeSerializableElement(descriptor, 0, _VStackLayout.Serializer)
                        1 -> content = decodeSerializableElement(descriptor, 1, contentSerializer) as Content
                        CompositeDecoder.DECODE_DONE -> break
                        else -> error("Unexpected index: $index")
                    }
                }
                VStack(root.alignment, root.spacing) { content }
            }
    }

    companion object {
        //: Register
        fun register() {
            PType.register<VStack<AnyView>>()
        }
    }
}

@Serializable(with = _VStackLayout.Serializer::class)
data class _VStackLayout(
    val alignment: HorizontalAlignment = HorizontalAlignment.center,
    val spacing: Float? = null
) : _VariadicView_UnaryViewRoot {
    //: Codable
    internal object Serializer : KSerializer<_VStackLayout> {
        override val descriptor: SerialDescriptor =
            buildClassSerialDescriptor(":_VStackLayout") {
                element<HorizontalAlignment>("alignment")
                element<Float?>("spacing")
            }

        override fun serialize(encoder: Encoder, value: _VStackLayout) =
            encoder.encodeStructure(descriptor) {
                encodeSerializableElement(descriptor, 0, HorizontalAlignment.Serializer, value.alignment)
                if (value.spacing != null) encodeSerializableElement(descriptor, 1, Float.serializer(), value.spacing!!)
            }

        override fun deserialize(decoder: Decoder): _VStackLayout =
            decoder.decodeStructure(descriptor) {
                lateinit var alignment: HorizontalAlignment
                var spacing: Float? = null
                while (true) {
                    when (val index = decodeElementIndex(descriptor)) {
                        0 -> alignment = decodeSerializableElement(descriptor, 0, HorizontalAlignment.Serializer)
                        1 -> spacing = decodeFloatElement(descriptor, 1)
                        CompositeDecoder.DECODE_DONE -> break
                        else -> error("Unexpected index: $index")
                    }
                }
                _VStackLayout(alignment, spacing)
            }
    }
}

//internal fun <Content : View> VStack<Content>._makeView(view: _GraphValue<VStack<Content>>, inputs: _ViewInputs): _ViewOutputs = error("Not Implemented")
