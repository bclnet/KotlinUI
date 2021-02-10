package kotlinx.kotlinui

import kotlinx.ptype.PType
import kotlinx.serialization.*
import kotlinx.serialization.descriptors.*
import kotlinx.serialization.encoding.*

@Serializable(with = ForegroundStyle.Serializer::class)
class ForegroundStyle : ShapeStyle {
    override fun equals(other: Any?): Boolean = other is ForegroundStyle
    override fun hashCode(): Int = javaClass.hashCode()
    override fun makeView(): Shape = error("Not Implemented")

    //: Codable
    internal object Serializer : KSerializer<ForegroundStyle> {
        override val descriptor: SerialDescriptor =
            buildClassSerialDescriptor(":ForegroundStyle") {}

        override fun serialize(encoder: Encoder, value: ForegroundStyle) =
            encoder.encodeStructure(descriptor) { }

        override fun deserialize(decoder: Decoder): ForegroundStyle =
            decoder.decodeStructure(descriptor) { ForegroundStyle() }
    }

    companion object {
        //: Register
        fun register() {
            PType.register<ForegroundStyle>()
        }
    }
}

//internal fun <S : Shape> ForegroundStyle._makeView(view: _GraphValue<_ShapeView<S, ForegroundStyle>>, inputs: _ViewInputs): _ViewOutputs = error("Not Implemented")