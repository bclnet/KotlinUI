package kotlinx.kotlinui

import kotlinx.kotlinuijson.DynaType
import kotlinx.serialization.*
import kotlinx.serialization.descriptors.*
import kotlinx.serialization.encoding.*

@Serializable(with = ZStackSerializer::class)
class ZStack<Content : View>(
    alignment: Alignment = Alignment.center,
    content: ViewBuilder.() -> Content
) : View {
    val _tree: _VariadicView_Tree<_ZStackLayout, Content> =
        _VariadicView_Tree(_ZStackLayout(alignment), content(ViewBuilder()))

    override val body: View
        get() = error("Not Implemented")

    companion object {
        fun register() {
            DynaType.register<ZStack<AnyView>>()
        }
    }
}

class ZStackSerializer<Content : View>(private val contentSerializer: KSerializer<Content>) : KSerializer<ZStack<Content>> {
    override val descriptor: SerialDescriptor =
        buildClassSerialDescriptor("ZStack") {
            element<_ZStackLayout>("root")
            element<View>("content")
        }

    override fun serialize(encoder: Encoder, value: ZStack<Content>) =
        encoder.encodeStructure(descriptor) {
            val tree = value._tree
            val root = tree.root
            if (root.alignment != Alignment.center)
                encodeSerializableElement(descriptor, 0, _ZStackLayoutSerializer, root)
            encodeSerializableElement(descriptor, 1, contentSerializer, tree.content)
        }

    @ExperimentalSerializationApi
    override fun deserialize(decoder: Decoder): ZStack<Content> =
        decoder.decodeStructure(descriptor) {
            val root = decodeNullableSerializableElement(descriptor, 0, _ZStackLayoutSerializer as KSerializer<_ZStackLayout?>)
                ?: _ZStackLayout(Alignment.center)
            val content = decodeSerializableElement(descriptor, 1, contentSerializer)
            ZStack(root.alignment) { content }
        }
}

internal fun <Content : View> ZStack<Content>._makeView(view: _GraphValue<ZStack<Content>>, inputs: _ViewInputs): _ViewOutputs = error("Not Implemented")

@Serializable(with = _ZStackLayoutSerializer::class)
class _ZStackLayout(var alignment: Alignment = Alignment.center) : _VariadicView_UnaryViewRoot

object _ZStackLayoutSerializer : KSerializer<_ZStackLayout> {
    override val descriptor: SerialDescriptor =
        buildClassSerialDescriptor("_ZStackLayout") {
            element<Alignment>("alignment")
        }

    override fun serialize(encoder: Encoder, value: _ZStackLayout) =
        encoder.encodeStructure(descriptor) {
            encodeSerializableElement(descriptor, 0, AlignmentSerializer, value.alignment)
        }

    override fun deserialize(decoder: Decoder): _ZStackLayout =
        decoder.decodeStructure(descriptor) {
            _ZStackLayout(
                decodeSerializableElement(descriptor, 0, AlignmentSerializer)
            )
        }
}
