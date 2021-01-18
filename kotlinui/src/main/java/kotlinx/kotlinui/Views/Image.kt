package kotlinx.kotlinui

import kotlinx.serialization.*
import kotlinx.serialization.descriptors.*
import kotlinx.serialization.encoding.*
import java.util.*

class AnyImageProviderBox

@Serializable(with = Image.Serializer::class)
data class Image private constructor(
    var _provider: AnyImageProviderBox
) : View {

    constructor(name: String, bundle: ResourceBundle? = null) : this(AnyImageProviderBox()) {
        error("Not Implemented")
    }

    constructor(name: String, bundle: ResourceBundle? = null, label: Text) : this(AnyImageProviderBox()) {
        error("Not Implemented")
    }

//    constructor(decorative: String, bundle: ResourceBundle? = null) : this(AnyImageProviderBox()) {
//        error("Not Implemented")
//    }

//    constructor(systemName: String) : this(AnyImageProviderBox()) {
//        error("Not Implemented")
//    }

//    override fun equals(other: Any?): Boolean {
//        if (other !is Image) return false
//        return _provider == other._provider
//    }
//
//    override fun hashCode(): Int {
//        var result = _provider.hashCode()
//        result = 31 * result + body.hashCode()
//        return result
//    }

    override val body: Never
        get() = error("Never")

    //: Codable
    internal object Serializer : KSerializer<Image> {
        override val descriptor: SerialDescriptor =
            buildClassSerialDescriptor("Image") {
            }

        override fun serialize(encoder: Encoder, value: Image) =
            encoder.encodeStructure(descriptor) {
            }

        override fun deserialize(decoder: Decoder): Image =
            decoder.decodeStructure(descriptor) {
                error("")
            }
    }
}
