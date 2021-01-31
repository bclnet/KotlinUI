package kotlinx.kotlinui

import kotlinx.ptype.PType
import kotlinx.serialization.*
import kotlinx.serialization.descriptors.*
import kotlinx.serialization.encoding.*
import kotlinx.system.*

@Serializable(with = _EnvironmentKeyWritingModifier.Serializer::class)
data class _EnvironmentKeyWritingModifier<Value>(
    val keyPath: WritableKeyPath<EnvironmentValues, Value>,
    val value: Value
) : ViewModifier {
//    fun body(content: AnyView) -> AnyView { AnyView(content.environment(keyPath, value)) }

    //: Codable
    internal class Serializer<Value> : KSerializer<_EnvironmentKeyWritingModifier<Value>> {
        val valueSerializer = PolymorphicSerializer(Any::class)

        override val descriptor: SerialDescriptor =
            buildClassSerialDescriptor("_EnvironmentKeyWritingModifier") {
                element<String>("keyPath")
                element("value", valueSerializer.descriptor)
            }

        override fun serialize(encoder: Encoder, value: _EnvironmentKeyWritingModifier<Value>) =
            encoder.encodeStructure(descriptor) {
                val action: String = EnvironmentValues.find(value.keyPath)!!
                encodeStringElement(descriptor, 0, action)
                encodeSerializableElement(descriptor, 1, valueSerializer, value.value as Any)
            }

        override fun deserialize(decoder: Decoder): _EnvironmentKeyWritingModifier<Value> =
            decoder.decodeStructure(descriptor) {
                lateinit var action: String
                var value: Value? = null
                while (true) {
                    when (val index = decodeElementIndex(descriptor)) {
                        0 -> action = decodeStringElement(descriptor, 0)
                        1 -> value = decodeSerializableElement(descriptor, 1, valueSerializer) as Value
                        CompositeDecoder.DECODE_DONE -> break
                        else -> error("Unexpected index: $index")
                    }
                }
                val keyPath = WritableKeyPath<EnvironmentValues, Value>()
                _EnvironmentKeyWritingModifier(keyPath, value!!)
                error("Not Implemented")
            }
    }

    companion object {
        //: Register
        fun register() {
            PType.register<_EnvironmentKeyWritingModifier<Boolean?>>()
            PType.register<_EnvironmentKeyWritingModifier<Color?>>()
        }
    }
}
