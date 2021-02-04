package kotlinx.ptype

import kotlinx.serialization.*
import kotlinx.serialization.descriptors.*
import kotlinx.serialization.encoding.*

open class KSerializerUserInfo<T>: KSerializer<T> {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("Never", PrimitiveKind.INT)
    override fun serialize(encoder: Encoder, value: T)  = error("Not Supported")
    override fun deserialize(decoder: Decoder): T = error("Not Supported")
}
