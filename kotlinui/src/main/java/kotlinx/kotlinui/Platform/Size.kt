package kotlinx.kotlinui

import android.graphics.RectF
import android.util.SizeF
import kotlinx.serialization.*
import kotlinx.serialization.descriptors.*
import kotlinx.serialization.encoding.*

object SizeFSerializer : KSerializer<SizeF> {
    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("SizeF", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, value: SizeF) =
        encoder.encodeSerializableValue(serializer(), arrayOf(value.width, value.height))

    override fun deserialize(decoder: Decoder): SizeF =
        decoder.decodeSerializableValue(serializer<Array<Float>>()).let {
            SizeF(it[0], it[1])
        }
}

object RectFSerializer : KSerializer<RectF> {
    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("RectF", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, value: RectF) =
        encoder.encodeSerializableValue(serializer(), arrayOf(value.left, value.top, value.right - value.left, value.bottom - value.top))

    override fun deserialize(decoder: Decoder): RectF =
        decoder.decodeSerializableValue(serializer<Array<Float>>()).let {
            RectF(it[0], it[1], it[0] + it[2], it[1] - it[3])
        }
}