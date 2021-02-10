package kotlinx.kotlinui

import kotlinx.ptype.PType
import kotlinx.serialization.*
import kotlinx.serialization.descriptors.*
import kotlinx.serialization.encoding.*

@Serializable(with = _OverlayModifier.Serializer::class)
data class _OverlayModifier<Overlay : View>(
    var overlay: Overlay,
    var alignment: Alignment
) : ViewModifier {
    //: Codable
    internal class Serializer<Overlay : View> : KSerializer<_OverlayModifier<Overlay>> {
        val overlaySerializer = PolymorphicSerializer(Any::class)

        override val descriptor: SerialDescriptor =
            buildClassSerialDescriptor(":_OverlayModifier") {
                element("overlay", overlaySerializer.descriptor)
                element<Alignment>("alignment")
            }

        override fun serialize(encoder: Encoder, value: _OverlayModifier<Overlay>) =
            encoder.encodeStructure(descriptor) {
                encodeSerializableElement(descriptor, 0, overlaySerializer, value.overlay)
                encodeSerializableElement(descriptor, 1, Alignment.Serializer, value.alignment)
            }

        override fun deserialize(decoder: Decoder): _OverlayModifier<Overlay> =
            decoder.decodeStructure(descriptor) {
                var overlay: Overlay? = null
                lateinit var alignment: Alignment
                while (true) {
                    when (val index = decodeElementIndex(descriptor)) {
                        0 -> overlay = decodeSerializableElement(descriptor, 0, overlaySerializer) as Overlay
                        1 -> alignment = decodeSerializableElement(descriptor, 1, Alignment.Serializer)
                        CompositeDecoder.DECODE_DONE -> break
                        else -> error("Unexpected index: $index")
                    }
                }
                _OverlayModifier(overlay!!, alignment)
            }
    }

    companion object {
        //: Register
        fun register() {
            PType.register<_OverlayModifier<AnyView>>()
        }
    }
}

fun <Overlay : View> View.overlay(overlay: Overlay, alignment: Alignment = Alignment.center): View =
    modifier(_OverlayModifier(overlay, alignment))
