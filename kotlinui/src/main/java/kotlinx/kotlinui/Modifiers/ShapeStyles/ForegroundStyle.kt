package kotlinx.kotlinui

import kotlinx.ptype.PType
import kotlinx.serialization.*
import kotlinx.serialization.descriptors.*
import kotlinx.serialization.encoding.*

@Serializable(with = ForegroundStyle.Serializer::class)
class ForegroundStyle : ShapeStyle {
    //: Codable
    internal object Serializer : KSerializer<ForegroundStyle> {
        override val descriptor: SerialDescriptor =
            PrimitiveSerialDescriptor("ForegroundStyle", PrimitiveKind.STRING)

        override fun serialize(encoder: Encoder, value: ForegroundStyle) {}

        override fun deserialize(decoder: Decoder): ForegroundStyle =
            ForegroundStyle()
    }

    companion object {
        //: Register
        fun register() {
            PType.register<ForegroundStyle>()
        }
    }
}

//internal fun <S : Shape> ForegroundStyle._makeView(view: _GraphValue<_ShapeView<S, ForegroundStyle>>, inputs: _ViewInputs): _ViewOutputs = error("Not Implemented")