package kotlinx.kotlinui

import kotlinx.serialization.*
import kotlinx.serialization.descriptors.*
import kotlinx.serialization.encoding.*

@Serializable(with = Binding.Serializer::class)
class Binding<Value> private constructor(
    get: () -> Value
) {
    var transaction: Transaction = Transaction()
    var location: AnyLocation<Value> = AnyLocation<Value>(get())
    private var _value: Value = get()

    constructor(get: () -> Value, set: (Value) -> Unit) : this(get) {
        set(_value)
    }

    constructor(get: () -> Value, set: (Value, Transaction) -> Unit) : this(get) {
        set(_value, transaction)
    }

    //where Value == V?
//    constructor(base: Binding<V>) : this() {
//        error("Not Implemented")
//    }
    //constructor(base: Binding<Value?>) : this() {
//    error("Not Implemented")
//}

    internal class Serializer<Value>() : KSerializer<Binding<Value>> {
        val valueSerializer = PolymorphicSerializer(Any::class)

        override val descriptor: SerialDescriptor =
            buildClassSerialDescriptor("Binding") {
            }

        override fun serialize(encoder: Encoder, value: Binding<Value>) =
            encoder.encodeStructure(descriptor) {
            }

        @ExperimentalSerializationApi
        override fun deserialize(decoder: Decoder): Binding<Value> =
            decoder.decodeStructure(descriptor) {
                error("Not Implemented")
            }
    }

    companion object {
        fun <Value> constant(value: Value): Binding<Value> =
            Binding { value }
    }

    var wrappedValue: Value
        get() = location.value
        set(newValue) {
            location.value = newValue
        }

    var projectedValue: Binding<Value> = this

    fun transaction(transaction: Transaction): Binding<Value>? = error("Not Implemented")

    //public subscript<Subject>(dynamicMember keyPath: WritableKeyPath<Value, Subject>) -> Binding<Subject> {
    //    error("Not Implemented")
    //}
}

//fun <Value, V> Binding<Value>._makeProperty<V>(buffer: _DynamicPropertyBuffer, container: _GraphValue<V>, fieldOffset: Int, inputs: _GraphInputs) = error("Not Implemented")

