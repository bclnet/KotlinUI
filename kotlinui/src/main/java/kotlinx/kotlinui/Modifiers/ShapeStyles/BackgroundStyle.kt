package kotlinx.kotlinui

import kotlinx.ptype.PType
import kotlinx.serialization.*
import kotlinx.serialization.descriptors.*
import kotlinx.serialization.encoding.*

@Serializable(with = BackgroundStyle.Serializer::class)
class BackgroundStyle : ShapeStyle {
    //: Codable
    internal object Serializer : KSerializer<BackgroundStyle> {
        override val descriptor: SerialDescriptor =
            PrimitiveSerialDescriptor("BackgroundStyle", PrimitiveKind.STRING)

        override fun serialize(encoder: Encoder, value: BackgroundStyle) {}

        override fun deserialize(decoder: Decoder): BackgroundStyle =
            BackgroundStyle()
    }

    companion object {
        //: Register
        fun register() {
            PType.register<BackgroundStyle>()
        }
    }
}