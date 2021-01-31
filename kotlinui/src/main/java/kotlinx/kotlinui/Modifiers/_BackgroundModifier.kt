package kotlinx.kotlinui

import kotlinx.ptype.PType
import kotlinx.serialization.*
import kotlinx.serialization.descriptors.*
import kotlinx.serialization.encoding.*

@Serializable(with = _BackgroundModifier.Serializer::class)
data class _BackgroundModifier<Background : View>(
    val background: Background,
    val alignment: Alignment
) : ViewModifier {
    //: Codable
    internal class Serializer<Background : View> : KSerializer<_BackgroundModifier<Background>> {
        val backgroundSerializer = PolymorphicSerializer(Any::class)

        override val descriptor: SerialDescriptor =
            buildClassSerialDescriptor("_BackgroundModifier") {
                element("background", backgroundSerializer.descriptor)
                element<Alignment>("alignment")
            }

        override fun serialize(encoder: Encoder, value: _BackgroundModifier<Background>) =
            encoder.encodeStructure(descriptor) {
                encodeSerializableElement(descriptor, 0, backgroundSerializer, value.background)
                encodeSerializableElement(descriptor, 1, Alignment.Serializer, value.alignment)
            }

        override fun deserialize(decoder: Decoder): _BackgroundModifier<Background> =
            decoder.decodeStructure(descriptor) {
                lateinit var background: Background
                lateinit var alignment: Alignment
                while (true) {
                    when (val index = decodeElementIndex(_ZStackLayout.Serializer.descriptor)) {
                        0 -> background = decodeSerializableElement(descriptor, 0, backgroundSerializer) as Background
                        1 -> alignment = decodeSerializableElement(descriptor, 1, Alignment.Serializer)
                        CompositeDecoder.DECODE_DONE -> break
                        else -> error("Unexpected index: $index")
                    }
                }
                _BackgroundModifier(background, alignment)
            }
    }

    companion object {
        //: Register
        fun register() {
            PType.register<_BackgroundModifier<AnyView>>()
        }
    }
}

fun <Background : View> View.background(background: Background, alignment: Alignment = Alignment.center): View =
    modifier(_BackgroundModifier(background, alignment))

//internal fun <Background : View> _BackgroundModifier<Background>._makeView(modifier: _GraphValue<_BackgroundModifier<Background>>, inputs: _ViewInputs, body: (_Graph, _ViewInputs) -> _ViewOutputs): _ViewOutputs = error("Not Implemented")
