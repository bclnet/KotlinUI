@file: OptIn(ExperimentalSerializationApi::class)
package kotlinx.kotlinui

import android.graphics.ColorSpace
import kotlinx.serialization.*
import kotlinx.serialization.descriptors.*
import kotlinx.serialization.encoding.*
import android.graphics.Color as CGColor
//import androidx.compose.ui.graphics.Color as UXColor

@Serializer(forClass = CGColor::class)
object CGColorSerializer : KSerializer<CGColor> {
    override val descriptor: SerialDescriptor =
        buildClassSerialDescriptor("CGColor") {
            element<String>("colorSpace")
            element<String>("components")
        }

    override fun serialize(encoder: Encoder, value: CGColor) =
        encoder.encodeStructure(descriptor) {
            encodeStringElement(descriptor, 0, value.colorSpace.name)
            encodeSerializableElement(descriptor, 1, serializer(), value.components)
        }

    override fun deserialize(decoder: Decoder): CGColor =
        decoder.decodeStructure(descriptor) {
            lateinit var colorSpace: ColorSpace
            lateinit var components: FloatArray
            while (true) {
                when (val index = decodeElementIndex(descriptor)) {
                    0 -> colorSpace = ColorSpace.get(ColorSpace.Named.valueOf(decodeStringElement(descriptor, 0)))
                    1 -> components = decodeSerializableElement(descriptor, 1, serializer())
                    CompositeDecoder.DECODE_DONE -> break
                    else -> error("Unexpected index: $index")
                }
            }
            CGColor.valueOf(components, colorSpace)
        }
}

//@Serializer(forClass = UXColor::class)
//object UXColorSerializer : KSerializer<UXColor> {
//    override val descriptor: SerialDescriptor =
//        buildClassSerialDescriptor("UXColor", serializer<ByteArray>().descriptor)
//
//    override fun serialize(encoder: Encoder, value: UXColor) =
//        error("")
//
//    override fun deserialize(decoder: Decoder): UXColor =
//        error("")
//}
