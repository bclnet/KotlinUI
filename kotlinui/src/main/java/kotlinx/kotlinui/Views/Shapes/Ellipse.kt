package kotlinx.kotlinui

import android.graphics.Rect
import kotlinx.ptype.PType
import kotlinx.serialization.*
import kotlinx.serialization.descriptors.*
import kotlinx.serialization.encoding.*

@Serializable(with = Ellipse.Serializer::class)
class Ellipse : Shape {
    override fun equals(other: Any?): Boolean = other is Ellipse
    override fun hashCode(): Int = javaClass.hashCode()

    override fun path(rect: Rect): Path = error("Never")

    override val body: View
        get() = error("Never")

    //: Codable
    internal class Serializer : KSerializer<Ellipse> {
        override val descriptor: SerialDescriptor =
            buildClassSerialDescriptor(":Ellipse") { }

        override fun serialize(encoder: Encoder, value: Ellipse) =
            encoder.encodeStructure(descriptor) { }

        override fun deserialize(decoder: Decoder): Ellipse =
            decoder.decodeStructure(descriptor) { Ellipse() }
    }

    @Serializable(with = _Inset.Serializer::class)
    data class _Inset(
        val amount: Float
    ) : ViewModifier {
        //: Codable
        internal class Serializer : KSerializer<_Inset> {
            override val descriptor: SerialDescriptor =
                buildClassSerialDescriptor(":Ellipse._Inset") {
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
            PType.register<Ellipse>()
            PType.register<_Inset>()
        }
    }
}