package kotlinx.kotlinui

import android.content.Context
import android.widget.LinearLayout
import kotlinx.serialization.KSerializer
import kotlinx.serialization.PolymorphicSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.buildClassSerialDescriptor
import kotlinx.serialization.descriptors.element
import kotlinx.serialization.encoding.*
import android.view.View as XView

@Serializable(with = HStack.Serializer::class)
class HStack<Content : View>(
    alignment: VerticalAlignment = VerticalAlignment.center,
    spacing: Float? = null,
    content: ViewBuilder.() -> Content
) : View, ViewBuildable {
    val _tree: _VariadicView_Tree<_HStackLayout, Content> =
        _VariadicView_Tree(_HStackLayout(alignment, spacing), content(ViewBuilder))

    override val body: Never
        get() = error("Never")

    //: Codable
    internal class Serializer<Content : View> : KSerializer<HStack<Content>> {
        val contentSerializer = PolymorphicSerializer(Any::class)

        override val descriptor: SerialDescriptor =
            buildClassSerialDescriptor(":HStack") {
                element<_HStackLayout>("root")
                element("content", contentSerializer.descriptor)
            }

        override fun serialize(encoder: Encoder, value: HStack<Content>) =
            encoder.encodeStructure(descriptor) {
                val tree = value._tree
                val root = tree.root
                if (root.alignment != VerticalAlignment.center || root.spacing != null) encodeSerializableElement(descriptor, 0, _HStackLayout.Serializer, root)
                encodeSerializableElement(descriptor, 1, contentSerializer, tree.content)
            }

        override fun deserialize(decoder: Decoder): HStack<Content> =
            decoder.decodeStructure(descriptor) {
                var root = _HStackLayout()
                lateinit var content: Content
                while (true) {
                    when (val index = decodeElementIndex(descriptor)) {
                        0 -> root = decodeSerializableElement(descriptor, 0, _HStackLayout.Serializer)
                        1 -> content = decodeSerializableElement(descriptor, 1, contentSerializer) as Content
                        CompositeDecoder.DECODE_DONE -> break
                        else -> error("Unexpected index: $index")
                    }
                }
                HStack(root.alignment, root.spacing) { content }
            }
    }

    //: ViewBuildable
    override fun buildView(context: Context?): XView {
        val root = _tree.root
        val view = LinearLayout(context)
        view.orientation = LinearLayout.HORIZONTAL
        _tree.content.buildViews(context).forEach {
            view.addView(it)
        }
        return view
    }
}

//internal fun <Content : View> HStack<Content>._makeView(view: _GraphValue<HStack<Content>>, inputs: _ViewInputs): _ViewOutputs = error("Not Implemented")

@Serializable(with = _HStackLayout.Serializer::class)
data class _HStackLayout(
    val alignment: VerticalAlignment = VerticalAlignment.center,
    val spacing: Float? = null
) : _VariadicView_UnaryViewRoot {
    //: Codable
    internal object Serializer : KSerializer<_HStackLayout> {
        override val descriptor: SerialDescriptor =
            buildClassSerialDescriptor(":_HStackLayout") {
                element<HorizontalAlignment>("alignment")
                element<Float?>("spacing")
            }

        override fun serialize(encoder: Encoder, value: _HStackLayout) =
            encoder.encodeStructure(descriptor) {
                encodeSerializableElement(descriptor, 0, VerticalAlignment.Serializer, value.alignment)
                if (value.spacing != null) encodeSerializableElement(descriptor, 1, Float.serializer(), value.spacing!!)
            }

        override fun deserialize(decoder: Decoder): _HStackLayout =
            decoder.decodeStructure(descriptor) {
                lateinit var alignment: VerticalAlignment
                var spacing: Float? = null
                while (true) {
                    when (val index = decodeElementIndex(descriptor)) {
                        0 -> alignment = decodeSerializableElement(descriptor, 0, VerticalAlignment.Serializer)
                        1 -> spacing = decodeFloatElement(descriptor, 1)
                        CompositeDecoder.DECODE_DONE -> break
                        else -> error("Unexpected index: $index")
                    }
                }
                _HStackLayout(alignment, spacing)
            }
    }
}
