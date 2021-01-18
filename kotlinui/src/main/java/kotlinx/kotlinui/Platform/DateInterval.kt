package kotlinx.kotlinui

import android.icu.util.DateInterval
import kotlinx.serialization.*
import kotlinx.serialization.descriptors.*
import kotlinx.serialization.encoding.*

internal object DateIntervalSerializer : KSerializer<DateInterval> {
    override val descriptor: SerialDescriptor =
        buildClassSerialDescriptor("DateInterval") {
        }

    override fun serialize(encoder: Encoder, value: DateInterval) =
        encoder.encodeStructure(descriptor) {
        }

    override fun deserialize(decoder: Decoder): DateInterval =
        decoder.decodeStructure(descriptor) {
//            var main = false
//            var path = ""
//            while (true) {
//                when (val index = decodeElementIndex(descriptor)) {
//                    0 -> main = decodeBooleanElement(descriptor, 0)
//                    1 -> path = decodeStringElement(descriptor, 1)
//                    CompositeDecoder.DECODE_DONE -> break
//                    else -> error("Unexpected index: $index")
//                }
//            }
            return DateInterval(0, 0)
        }
}