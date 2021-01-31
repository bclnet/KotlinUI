package kotlinx.kotlinui

import kotlinx.ptype.PType
import kotlinx.serialization.*
import kotlinx.serialization.descriptors.*
import kotlinx.serialization.encoding.*
import java.util.EnumSet

@Serializable(with = _SafeAreaIgnoringLayout.Serializer::class)
data class _SafeAreaIgnoringLayout(
    val edges: EnumSet<Edge>
) : ViewModifier {
    //: Codable
    internal object Serializer : KSerializer<_SafeAreaIgnoringLayout> {
        override val descriptor: SerialDescriptor =
            buildClassSerialDescriptor("_SafeAreaIgnoringLayout") {
                element("edges", Edge.SetSerializer.descriptor)
            }

        override fun serialize(encoder: Encoder, value: _SafeAreaIgnoringLayout) =
            encoder.encodeStructure(descriptor) {
                encodeSerializableElement(descriptor, 0, Edge.SetSerializer, value.edges)
            }

        override fun deserialize(decoder: Decoder): _SafeAreaIgnoringLayout =
            decoder.decodeStructure(descriptor) {
                lateinit var edges: EnumSet<Edge>
                while (true) {
                    when (val index = decodeElementIndex(descriptor)) {
                        0 -> edges = decodeSerializableElement(descriptor, 0, Edge.SetSerializer)
                        CompositeDecoder.DECODE_DONE -> break
                        else -> error("Unexpected index: $index")
                    }
                }
                _SafeAreaIgnoringLayout(edges)
            }
    }

    companion object {
        //: Register
        fun register() {
            PType.register<_SafeAreaIgnoringLayout>()
            PType.register<_SafeAreaRegionsIgnoringLayout>()
        }
    }
}

fun View.edgesIgnoringSafeArea(edges: EnumSet<Edge>): View =
    modifier(_SafeAreaIgnoringLayout(edges))

@Serializable(with = _SafeAreaRegionsIgnoringLayout.Serializer::class)
class _SafeAreaRegionsIgnoringLayout : ViewModifier {
//    fun body(content: AnyView): AnyView { AnyView(content.modifier(self)) }

    //: Codable
    internal object Serializer : KSerializer<_SafeAreaRegionsIgnoringLayout> {
        override val descriptor: SerialDescriptor =
            buildClassSerialDescriptor("_SafeAreaRegionsIgnoringLayout") {
            }

        override fun serialize(encoder: Encoder, value: _SafeAreaRegionsIgnoringLayout) =
            encoder.encodeStructure(descriptor) {
                error("Not Implemented")
            }

        override fun deserialize(decoder: Decoder): _SafeAreaRegionsIgnoringLayout =
            decoder.decodeStructure(descriptor) {
                error("Not Implemented")
            }
    }
}