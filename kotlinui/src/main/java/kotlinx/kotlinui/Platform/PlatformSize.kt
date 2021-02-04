@file: OptIn(ExperimentalSerializationApi::class)

package kotlinx.kotlinui

import android.graphics.RectF
import android.util.SizeF
import kotlinx.serialization.*
import kotlinx.serialization.descriptors.*
import kotlinx.serialization.encoding.*
import java.util.*

@Serializer(forClass = SizeF::class)
object SizeFSerializer : KSerializer<SizeF> {
    override val descriptor: SerialDescriptor =
        buildClassSerialDescriptor("SizeF") {}

    override fun serialize(encoder: Encoder, value: SizeF) =
        encoder.encodeSerializableValue(serializer(), arrayOf(value.width, value.height).toFloatArray())

    override fun deserialize(decoder: Decoder): SizeF =
        decoder.decodeSerializableValue(serializer<FloatArray>()).let { SizeF(it[0], it[1]) }
}

@Serializer(forClass = RectF::class)
object RectFSerializer : KSerializer<RectF> {
    override val descriptor: SerialDescriptor =
        buildClassSerialDescriptor("RectF") {}

    override fun serialize(encoder: Encoder, value: RectF) =
        encoder.encodeSerializableValue(serializer(), arrayOf(value.left, value.top, value.right - value.left, value.bottom - value.top).toFloatArray())

    override fun deserialize(decoder: Decoder): RectF =
        decoder.decodeSerializableValue(serializer<FloatArray>()).let { RectF(it[0], it[1], it[0] + it[2], it[1] - it[3]) }
}