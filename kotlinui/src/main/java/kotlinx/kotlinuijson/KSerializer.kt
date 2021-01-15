package kotlinx.kotlinuijson

import kotlinx.serialization.*
import kotlinx.serialization.descriptors.*
import kotlinx.serialization.encoding.*

internal open class KSerializerUserInfo<T>: KSerializer<T> {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("UserInfo", PrimitiveKind.STRING)
    override fun serialize(encoder: Encoder, value: T)  = error("Not Supported")
    override fun deserialize(decoder: Decoder): T = error("Not Supported")
}

