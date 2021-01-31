@file: OptIn(ExperimentalSerializationApi::class)
package kotlinx.kotlinui

import android.media.Image
import kotlinx.serialization.*
import kotlinx.serialization.descriptors.*
import kotlinx.serialization.encoding.*
import java.util.*

@Serializer(forClass = ResourceBundle::class)
object ResourceBundleSerializer : KSerializer<ResourceBundle> {
    override val descriptor: SerialDescriptor =
        buildClassSerialDescriptor("Bundle") {
            element<Boolean>("main")
            element<String>("path")
        }

    override fun serialize(encoder: Encoder, value: ResourceBundle) =
        encoder.encodeStructure(descriptor) {
            if (value.baseBundleName == null) {
                encodeBooleanElement(descriptor, 0, true)
                return
            }
            encodeStringElement(descriptor, 1, value.baseBundleName)
        }

    override fun deserialize(decoder: Decoder): ResourceBundle =
        decoder.decodeStructure(descriptor) {
            var main = false
            var path = ""
            while (true) {
                when (val index = decodeElementIndex(descriptor)) {
                    0 -> main = decodeBooleanElement(descriptor, 0)
                    1 -> path = decodeStringElement(descriptor, 1)
                    CompositeDecoder.DECODE_DONE -> break
                    else -> error("Unexpected index: $index")
                }
            }
            if (main)
                return ResourceBundle.getBundle(null)
            return ResourceBundle.getBundle(path)
        }
}