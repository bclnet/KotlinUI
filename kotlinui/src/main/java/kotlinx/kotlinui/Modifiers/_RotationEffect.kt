package kotlinx.kotlinui

import kotlinx.ptype.PType
import kotlinx.serialization.*
import kotlinx.serialization.descriptors.*
import kotlinx.serialization.encoding.*
import java.util.*

@Serializable(with = _RotationEffect.Serializer::class)
class _RotationEffect(
    val angle: Angle,
    val anchor: UnitPoint
) : ViewModifier {
//    fun body(content: AnyView): AnyView { AnyView(content.modifier(self)) }

    //: Codable
    internal object Serializer : KSerializer<_RotationEffect> {
        override val descriptor: SerialDescriptor =
            buildClassSerialDescriptor("_RotationEffect") {
                element<Angle>("angle")
                element<UnitPoint>("anchor")
            }

        override fun serialize(encoder: Encoder, value: _RotationEffect) =
            encoder.encodeStructure(descriptor) {
                encodeSerializableElement(descriptor, 0, Angle.Serializer, value.angle)
                encodeSerializableElement(descriptor, 1, UnitPoint.Serializer, value.anchor)
            }

        override fun deserialize(decoder: Decoder): _RotationEffect =
            decoder.decodeStructure(descriptor) {
                lateinit var angle: Angle
                lateinit var anchor: UnitPoint
                while (true) {
                    when (val index = decodeElementIndex(descriptor)) {
                        0 -> angle = decodeSerializableElement(descriptor, 0, Angle.Serializer)
                        1 -> anchor = decodeSerializableElement(descriptor, 1, UnitPoint.Serializer)
                        CompositeDecoder.DECODE_DONE -> break
                        else -> error("Unexpected index: $index")
                    }
                }
                _RotationEffect(angle, anchor)
            }
    }

    companion object {
        //: Register
        fun register() {
            PType.register<_RotationEffect>()
        }
    }
}
