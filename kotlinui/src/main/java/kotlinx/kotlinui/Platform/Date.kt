package kotlinx.kotlinui

import android.icu.util.DateInterval
import kotlinx.serialization.*
import kotlinx.serialization.descriptors.*
import kotlinx.serialization.encoding.*
import java.text.*
import java.util.*

internal object DateSerializer : KSerializer<Date> {
    private val df: DateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm:ss.SSS")
    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("Date", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, value: Date) =
        encoder.encodeString(df.format(value))

    override fun deserialize(decoder: Decoder): Date =
        df.parse(decoder.decodeString())
}

internal object DateIntervalSerializer : KSerializer<DateInterval> {
    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("UXImage", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, value: DateInterval) =
        encoder.encodeSerializableValue(serializer(), arrayOf(value.fromDate, value.toDate))

    override fun deserialize(decoder: Decoder): DateInterval =
        decoder.decodeSerializableValue(serializer<LongArray>()).let { DateInterval(it[0], it[1]) }
}