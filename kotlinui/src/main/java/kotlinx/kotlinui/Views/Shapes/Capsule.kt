package kotlinx.kotlinui

import android.graphics.Rect
import kotlinx.ptype.PType
import kotlinx.serialization.*
import kotlinx.serialization.descriptors.*
import kotlinx.serialization.encoding.*

@Serializable(with = Capsule.Serializer::class)
data class Capsule(
    val style: RoundedCornerStyle
) : Shape {
    override fun path(rect: Rect): Path = error("Never")

    override val body: View
        get() = error("Never")

    //: Codable
    internal class Serializer : KSerializer<Capsule> {
        override val descriptor: SerialDescriptor =
            buildClassSerialDescriptor(":Capsule") {
                element("style", RoundedCornerStyle.Serializer.descriptor)
            }

        override fun serialize(encoder: Encoder, value: Capsule) =
            encoder.encodeStructure(descriptor) {
                encodeSerializableElement(descriptor, 0, RoundedCornerStyle.Serializer, value.style)
            }

        override fun deserialize(decoder: Decoder): Capsule =
            decoder.decodeStructure(descriptor) {
                lateinit var style: RoundedCornerStyle
                while (true) {
                    when (val index = decodeElementIndex(descriptor)) {
                        0 -> style = decodeSerializableElement(descriptor, 0, RoundedCornerStyle.Serializer)
                        CompositeDecoder.DECODE_DONE -> break
                        else -> error("Unexpected index: $index")
                    }
                }
                Capsule(style)
            }
    }

    @Serializable(with = _Inset.Serializer::class)
    data class _Inset(
        val amount: Float
    ) : ViewModifier {
        //: Codable
        internal class Serializer : KSerializer<_Inset> {
            override val descriptor: SerialDescriptor =
                buildClassSerialDescriptor(":Capsule._Inset") {
                    element<Float>("amount")
                }

            override fun serialize(encoder: Encoder, value: _Inset) =
                encoder.encodeStructure(descriptor) {
                    encodeFloatElement(descriptor, 0, value.amount)
                }

            override fun deserialize(decoder: Decoder): _Inset =
                decoder.decodeStructure(descriptor) {
                    var amount = 0f
                    while (true) {
                        when (val index = decodeElementIndex(descriptor)) {
                            0 -> amount = decodeFloatElement(descriptor, 0)
                            CompositeDecoder.DECODE_DONE -> break
                            else -> error("Unexpected index: $index")
                        }
                    }
                    _Inset(amount)
                }
        }
    }

    companion object {
        //: Register
        fun register() {
            PType.register<Capsule>()
            PType.register<_Inset>()
        }
    }
}
