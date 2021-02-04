package kotlinx.kotlinui

import kotlinx.ptype.PType
import kotlinx.serialization.*
import kotlinx.serialization.descriptors.*
import kotlinx.serialization.encoding.*
import kotlinx.system.*
import kotlin.reflect.typeOf

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
            buildClassSerialDescriptor(":_EnvironmentKeyWritingModifier") {
                element<String>("keyPath")
                element<String>("key")
                element("value", valueSerializer.descriptor)
            }

        override fun serialize(encoder: Encoder, value: _EnvironmentKeyWritingModifier<Value>) =
            encoder.encodeStructure(descriptor) {
                val action = EnvironmentValues.find(value.keyPath)!!
                val key = PType.typeKey(value.keyPath.mutableType)
                encodeStringElement(descriptor, 0, action)
                encodeStringElement(descriptor, 1, key)
                encodeSerializableElement(descriptor, 2, valueSerializer, value.value as Any)
            }

        override fun deserialize(decoder: Decoder): _EnvironmentKeyWritingModifier<Value> =
            decoder.decodeStructure(descriptor) {
                lateinit var action: String
                lateinit var key: String
                var value: Value? = null
                while (true) {
                    when (val index = decodeElementIndex(descriptor)) {
                        0 -> action = decodeStringElement(descriptor, 0)
                        1 -> key = decodeStringElement(descriptor, 1)
                        2 -> value = decodeSerializableElement(descriptor, 2, valueSerializer) as Value
                        CompositeDecoder.DECODE_DONE -> break
                        else -> error("Unexpected index: $index")
                    }
                }
                val getKeyPath = PType.findAction<(() -> WritableKeyPath<EnvironmentValues, Value>)>(key, action)!!
                _EnvironmentKeyWritingModifier(getKeyPath(), value!!)
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
