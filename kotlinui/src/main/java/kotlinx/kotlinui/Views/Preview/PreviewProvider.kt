package kotlinx.kotlinui

import kotlinx.serialization.KSerializer
import kotlinx.serialization.PolymorphicSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.*
import kotlinx.serialization.encoding.*

interface PreviewProvider {
    val previews: View
}

@Serializable(with = PreviewLayout.Serializer::class)
sealed class PreviewLayout {
    class device : PreviewLayout()
    data class fixed(val width: Float, var height: Float) : PreviewLayout()
    class sizeThatFits : PreviewLayout()

    //: Codable
    internal object Serializer : KSerializer<PreviewLayout> {
        override val descriptor: SerialDescriptor =
            buildClassSerialDescriptor(":PreviewLayout") {
                element<String>("provider")
                element<Float>("width")
                element<Float>("height")
            }

        override fun serialize(encoder: Encoder, value: PreviewLayout) =
            encoder.encodeStructure(descriptor) {
                when (value) {
                    is device -> encodeStringElement(descriptor, 0, "device")
                    is fixed -> {
                        encodeStringElement(descriptor, 0, "fixed")
                        encodeFloatElement(descriptor, 1, value.width)
                        encodeFloatElement(descriptor, 2, value.height)
                    }
                    is sizeThatFits -> encodeStringElement(descriptor, 0, "sizeThatFits")
                    else -> error("$value")
                }
            }

        override fun deserialize(decoder: Decoder): PreviewLayout =
            decoder.decodeStructure(descriptor) {
                lateinit var provider: String
                var width = 0f
                var height = 0f
                while (true) {
                    when (val index = decodeElementIndex(descriptor)) {
                        0 -> provider = decodeStringElement(descriptor, 0)
                        1 -> width = decodeFloatElement(descriptor, 1)
                        2 -> height = decodeFloatElement(descriptor, 2)
                        CompositeDecoder.DECODE_DONE -> break
                        else -> error("Unexpected index: $index")
                    }
                }
                when (provider) {
                    "device" -> device()
                    "fixed" -> fixed(width, height)
                    "sizeThatFits" -> sizeThatFits()
                    else -> error(provider)
                }
            }
    }
}

enum class PreviewPlatform {
    android
}