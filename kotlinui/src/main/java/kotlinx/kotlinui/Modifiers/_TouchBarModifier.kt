package kotlinx.kotlinui

import kotlinx.ptype.PType
import kotlinx.serialization.*
import kotlinx.serialization.descriptors.*
import kotlinx.serialization.encoding.*

@Serializable(with = _TouchBarModifier.Serializer::class)
data class _TouchBarModifier<Content : View>(
    val touchBar: TouchBar<Content>,
) : ViewModifier {
    //    fun body(content: AnyView): AnyView { AnyView(content.modifier(self)) }

    //: Codable
    internal class Serializer<Content : View> : KSerializer<_TouchBarModifier<Content>> {
        val contentSerializer = PolymorphicSerializer(Any::class)

        override val descriptor: SerialDescriptor =
            buildClassSerialDescriptor("_TouchBarModifier") {
                element<View>("content")
            }

        override fun serialize(encoder: Encoder, value: _TouchBarModifier<Content>) =
            encoder.encodeStructure(descriptor) {
                encodeSerializableElement(descriptor, 0, contentSerializer, value.touchBar)
            }

        override fun deserialize(decoder: Decoder): _TouchBarModifier<Content> =
            decoder.decodeStructure(descriptor) {
                lateinit var touchBar: TouchBar<Content>
                while (true) {
                    when (val index = decodeElementIndex(descriptor)) {
                        0 -> touchBar = decodeSerializableElement(descriptor, 0, contentSerializer) as TouchBar<Content>
                        CompositeDecoder.DECODE_DONE -> break
                        else -> error("Unexpected index: $index")
                    }
                }
                _TouchBarModifier(touchBar)
            }
    }

    companion object {
        //: Register
        fun register() {
            PType.register<_TouchBarModifier<View>>()
        }
    }
}
