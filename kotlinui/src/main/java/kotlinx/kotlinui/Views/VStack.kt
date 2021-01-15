package kotlinx.kotlinui

import kotlinx.kotlinuijson.DynaType
import kotlinx.serialization.*
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.descriptors.*
import kotlinx.serialization.encoding.*

@Serializable(with = VStackSerializer::class)
class VStack<Content : View>(
    alignment: HorizontalAlignment = HorizontalAlignment.center,
    spacing: Float? = null,
    content: ViewBuilder.() -> Content
) : View {
    val _tree: _VariadicView_Tree<_VStackLayout, Content> =
        _VariadicView_Tree(_VStackLayout(alignment, spacing), content(ViewBuilder()))

    override val body: View
        get() = error("Not Implemented")

    companion object {
        fun register() {
            DynaType.register<VStack<AnyView>>()
        }
    }
}

class VStackSerializer<Content : View>(private val contentSerializer: KSerializer<Content>) : KSerializer<VStack<Content>> {
    override val descriptor: SerialDescriptor =
        buildClassSerialDescriptor("VStack") {
            element<_VStackLayout>("root")
            element<View>("content")
        }

    override fun serialize(encoder: Encoder, value: VStack<Content>) =
        encoder.encodeStructure(descriptor) {
            val tree = value._tree
            val root = tree.root
            if (root.alignment != HorizontalAlignment.center || root.spacing != null)
                encodeSerializableElement(descriptor, 0, _VStackLayoutSerializer, root)
            encodeSerializableElement(descriptor, 1, contentSerializer, tree.content)
        }

    @ExperimentalSerializationApi
    override fun deserialize(decoder: Decoder): VStack<Content> =
        decoder.decodeStructure(descriptor) {
            val root = decodeNullableSerializableElement(descriptor, 0, _VStackLayoutSerializer as KSerializer<_VStackLayout?>)
                ?: _VStackLayout(HorizontalAlignment.center, null)
            val content = decodeSerializableElement(descriptor, 1, contentSerializer)
            VStack(root.alignment, root.spacing) { content }
        }
}

internal fun <Content : View> VStack<Content>._makeView(view: _GraphValue<VStack<Content>>, inputs: _ViewInputs): _ViewOutputs = error("Not Implemented")

@Serializable(with = _VStackLayoutSerializer::class)
class _VStackLayout(
    var alignment: HorizontalAlignment = HorizontalAlignment.center,
    var spacing: Float? = null
) : _VariadicView_UnaryViewRoot

object _VStackLayoutSerializer : KSerializer<_VStackLayout> {
    override val descriptor: SerialDescriptor =
        buildClassSerialDescriptor("_VStackLayout") {
            element<HorizontalAlignment>("alignment")
            element<Float?>("spacing")
        }

    @ExperimentalSerializationApi
    override fun serialize(encoder: Encoder, value: _VStackLayout) =
        encoder.encodeStructure(descriptor) {
            encodeSerializableElement(descriptor, 0, HorizontalAlignmentSerializer, value.alignment)
            encodeNullableSerializableElement(descriptor, 1, Float.serializer(), value.spacing)
        }

    @ExperimentalSerializationApi
    override fun deserialize(decoder: Decoder): _VStackLayout =
        decoder.decodeStructure(descriptor) {
            _VStackLayout(
                decodeSerializableElement(descriptor, 0, HorizontalAlignmentSerializer),
                decodeNullableSerializableElement(descriptor, 1, Float.serializer() as KSerializer<Float?>)
            )
        }
}
