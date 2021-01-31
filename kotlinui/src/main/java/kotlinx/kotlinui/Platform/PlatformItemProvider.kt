@file: OptIn(ExperimentalSerializationApi::class)
package kotlinx.kotlinui

import android.content.ContentProvider as UXItemProvider
import kotlinx.serialization.*
import kotlinx.serialization.descriptors.*
import kotlinx.serialization.encoding.*
import java.util.*

@Serializer(forClass = UXItemProvider::class)
object UXItemProviderSerializer : KSerializer<UXItemProvider> {
    override val descriptor: SerialDescriptor =
        buildClassSerialDescriptor("UXItemProvider") {
        }

    override fun serialize(encoder: Encoder, value: UXItemProvider) =
        encoder.encodeStructure(descriptor) {
            error("Not Implemented")
        }

    override fun deserialize(decoder: Decoder): UXItemProvider =
        decoder.decodeStructure(descriptor) {
            error("Not Implemented")
        }
}