@file: OptIn(ExperimentalSerializationApi::class)
package kotlinx.kotlinui

import android.annotation.SuppressLint
import android.icu.util.DateInterval
import kotlinx.serialization.*
import kotlinx.serialization.descriptors.*
import kotlinx.serialization.encoding.*
import java.text.*
import java.time.format.DateTimeFormatter
import java.util.*

@Serializer(forClass = Date::class)
object DateSerializer : KSerializer<Date> {
    @SuppressLint("SimpleDateFormat")
    internal val formatter: DateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm:ss.SSS")

    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("Date", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, value: Date) =
        encoder.encodeString(formatter.format(value))

    override fun deserialize(decoder: Decoder): Date =
        formatter.parse(decoder.decodeString())!!
}

@Serializer(forClass = DateInterval::class)
object DateIntervalSerializer : KSerializer<DateInterval> {
    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("DateInterval", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, value: DateInterval) =
        encoder.encodeSerializableValue(
            serializer(), arrayOf(
                DateSerializer.formatter.format(Date(value.fromDate)),
                DateSerializer.formatter.format(Date(value.toDate))
            )
        )

    override fun deserialize(decoder: Decoder): DateInterval =
        decoder.decodeSerializableValue(serializer<Array<String>>()).let {
            DateInterval(
                DateSerializer.formatter.parse(it[0])!!.time,
                DateSerializer.formatter.parse(it[1])!!.time
            )
        }
}