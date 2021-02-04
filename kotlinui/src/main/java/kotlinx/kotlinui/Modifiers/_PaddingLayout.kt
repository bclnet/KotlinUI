package kotlinx.kotlinui

import kotlinx.ptype.PType
import kotlinx.serialization.*
import kotlinx.serialization.descriptors.*
import kotlinx.serialization.encoding.*
import java.util.*

@Serializable(with = _PaddingLayout.Serializer::class)
data class _PaddingLayout(
    val edges: EnumSet<Edge>,
    val insets: EdgeInsets?
) : ViewModifier {
//    fun body(content: AnyView): AnyView =
//        insets == nil ? AnyView(content.padding(edges, nil))
//            : insets!.isEqual ? AnyView(content.padding(edges, insets!.top == 0 ? nil : insets!.top))
//            : AnyView(content.padding(insets!))

    //: Codable
    internal object Serializer : KSerializer<_PaddingLayout> {
        override val descriptor: SerialDescriptor =
            buildClassSerialDescriptor(":_PaddingLayout") {
                element("edges", Edge.SetSerializer.descriptor)
                element<Float>("length")
                element<EdgeInsets>("insets")
            }

        override fun serialize(encoder: Encoder, value: _PaddingLayout) =
            encoder.encodeStructure(descriptor) {
                if (value.edges != Edge.Set.all) encodeSerializableElement(descriptor, 0, Edge.SetSerializer, value.edges)
                val insets = value.insets ?: return
                if (insets.isEqual) {
                    if (insets.top != 0f) encodeFloatElement(descriptor, 1, insets.top)
                    return
                }
                encodeSerializableElement(descriptor, 2, EdgeInsets.Serializer, insets)
            }

        override fun deserialize(decoder: Decoder): _PaddingLayout =
            decoder.decodeStructure(descriptor) {
                var edges: EnumSet<Edge> = Edge.Set.all
                var insets: EdgeInsets? = null
                while (true) {
                    when (val index = decodeElementIndex(descriptor)) {
                        0 -> edges = decodeSerializableElement(descriptor, 0, Edge.SetSerializer)
                        1 -> {
                            val length = decodeFloatElement(descriptor, 1)
                            insets = EdgeInsets(length, length, length, length)
                        }
                        2 -> insets = decodeSerializableElement(descriptor, 2, EdgeInsets.Serializer)
                        CompositeDecoder.DECODE_DONE -> break
                        else -> error("Unexpected index: $index")
                    }
                }
                _PaddingLayout(edges, insets)
            }
    }

    companion object {
        //: Register
        fun register() {
            PType.register<_PaddingLayout>()
        }
    }
}
