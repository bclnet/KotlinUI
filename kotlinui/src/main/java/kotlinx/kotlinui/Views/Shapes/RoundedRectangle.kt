package kotlinx.kotlinui

import android.graphics.Matrix
import android.graphics.Rect
import android.util.SizeF
import kotlinx.ptype.PType
import kotlinx.serialization.*
import kotlinx.serialization.descriptors.*
import kotlinx.serialization.encoding.*

@Serializable(with = RoundedRectangle.Serializer::class)
data class RoundedRectangle(
    val cornerSize: SizeF,
    val style: RoundedCornerStyle
) : Shape {
    override fun path(rect: Rect): Path = error("Never")

    override val body: View
        get() = error("Never")

    //: Codable
    internal object Serializer : KSerializer<RoundedRectangle> {
        override val descriptor: SerialDescriptor =
            buildClassSerialDescriptor("RoundedRectangle") {
                element<SizeF>("cornerSize")
                element<Float>("cornerRadius")
                element<RoundedCornerStyle>("style")
            }

        override fun serialize(encoder: Encoder, value: RoundedRectangle) =
            encoder.encodeStructure(descriptor) {
                if (value.cornerSize.width != value.cornerSize.height) encodeSerializableElement(descriptor, 0, SizeFSerializer, value.cornerSize)
                else encodeFloatElement(descriptor, 1, value.cornerSize.width)
                encodeSerializableElement(descriptor, 2, RoundedCornerStyle.Serializer, value.style)
            }

        override fun deserialize(decoder: Decoder): RoundedRectangle =
            decoder.decodeStructure(descriptor) {
                lateinit var cornerSize: SizeF
                lateinit var style: RoundedCornerStyle
                while (true) {
                    when (val index = decodeElementIndex(descriptor)) {
                        0 -> cornerSize = decodeSerializableElement(descriptor, 0, SizeFSerializer)
                        1 -> cornerSize = decodeFloatElement(descriptor, 1).let { SizeF(it, it) }
                        2 -> style = decodeSerializableElement(descriptor, 1, RoundedCornerStyle.Serializer)
                        CompositeDecoder.DECODE_DONE -> break
                        else -> error("Unexpected index: $index")
                    }
                }
                RoundedRectangle(cornerSize, style)
            }
    }

    @Serializable(with = _Inset.Serializer::class)
    data class _Inset(
        val base: RoundedRectangle,
        val amount: Float
    ) : ViewModifier {
        //: Codable
        internal class Serializer : KSerializer<_Inset> {
            override val descriptor: SerialDescriptor =
                buildClassSerialDescriptor("_Inset") {
                    element<RoundedRectangle>("base")
                    element<Float>("amount")
                }

            override fun serialize(encoder: Encoder, value: _Inset) =
                encoder.encodeStructure(descriptor) {
                    encodeSerializableElement(descriptor, 0, RoundedRectangle.Serializer, value.base)
                    encodeFloatElement(descriptor, 1, value.amount)
                }

            override fun deserialize(decoder: Decoder): _Inset =
                decoder.decodeStructure(descriptor) {
                    lateinit var base: RoundedRectangle
                    var amount = 0f
                    while (true) {
                        when (val index = decodeElementIndex(descriptor)) {
                            0 -> base = decodeSerializableElement(descriptor, 0, RoundedRectangle.Serializer)
                            1 -> amount = decodeFloatElement(descriptor, 1)
                            CompositeDecoder.DECODE_DONE -> break
                            else -> error("Unexpected index: $index")
                        }
                    }
                    _Inset(base, amount)
                }
        }
    }

    companion object {
        //: Register
        fun register() {
            PType.register<RoundedRectangle>()
            PType.register<_Inset>()
        }
    }
}