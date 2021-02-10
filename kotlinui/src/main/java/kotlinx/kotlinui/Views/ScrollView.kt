package kotlinx.kotlinui

import android.content.Context
import android.widget.ScrollView as XScrollView
import android.view.View as XView
import kotlinx.ptype.PType
import kotlinx.serialization.*
import kotlinx.serialization.descriptors.*
import kotlinx.serialization.encoding.*
import java.util.*

@Serializable(with = ScrollView.Serializer::class)
data class ScrollView<Content : View> private constructor(
    val configuration: ScrollViewConfiguration,
    val content: Content
) : View, ViewBuildable {
    constructor(
        axes: EnumSet<Axis> = Axis.Set.vertical,
        showsIndicators: Boolean = true,
        content: ViewBuilder.() -> Content
    ) : this(ScrollViewConfiguration(axes, showsIndicators, false, EdgeInsets(), false), content(ViewBuilder))

    override val body: View
        get() = error("Never")

    //: Codable
    internal class Serializer<Content : View> : KSerializer<ScrollView<Content>> {
        val contentSerializer = PolymorphicSerializer(Any::class)

        override val descriptor: SerialDescriptor =
            buildClassSerialDescriptor(":ScrollView") {
                element("axes", Axis.SetSerializer.descriptor)
                element<Boolean>("showsIndicators")
                element("content", contentSerializer.descriptor)
            }

        override fun serialize(encoder: Encoder, value: ScrollView<Content>) =
            encoder.encodeStructure(descriptor) {
                val configuration = value.configuration
                if (configuration.axes != Axis.Set.vertical) encodeSerializableElement(descriptor, 0, Axis.SetSerializer, configuration.axes)
                if (!configuration.showsIndicators) encodeBooleanElement(descriptor, 1, configuration.showsIndicators)
                encodeSerializableElement(descriptor, 2, contentSerializer, value.content)
            }

        override fun deserialize(decoder: Decoder): ScrollView<Content> =
            decoder.decodeStructure(descriptor) {
                var axes: EnumSet<Axis> = Axis.Set.vertical
                var showsIndicators: Boolean = true
                lateinit var content: Content
                while (true) {
                    when (val index = decodeElementIndex(descriptor)) {
                        0 -> axes = decodeSerializableElement(descriptor, 0, Axis.SetSerializer)
                        1 -> showsIndicators = decodeBooleanElement(descriptor, 1)
                        2 -> content = decodeSerializableElement(descriptor, 2, contentSerializer) as Content
                        CompositeDecoder.DECODE_DONE -> break
                        else -> error("Unexpected index: $index")
                    }
                }
                ScrollView(axes, showsIndicators) { content }
            }
    }

    //: ViewBuildable
    override fun buildView(context: Context?): XView {
        val view = XScrollView(context!!)
        view.addView(content.builder.buildView(context))
        return view
    }

    data class ScrollViewConfiguration(
        val axes: EnumSet<Axis>,
        val showsIndicators: Boolean,
        val automaticallyAdjustsContentInsets: Boolean,
        val contentInsets: EdgeInsets,
        val alwaysBounceAxes: Boolean
    )

    companion object {
        //: Register
        fun register() {
            PType.register<ScrollView<AnyView>>()
        }
    }
}
