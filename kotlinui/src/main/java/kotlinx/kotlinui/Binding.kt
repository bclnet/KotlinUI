package kotlinx.kotlinui

import kotlin.system.exitProcess

class Binding<Value> private constructor(get: () -> Value) {
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
//        exitProcess(0)
//    }
    //constructor(base: Binding<Value?>) : this() {
//    System.exit(0);
//}

    var wrappedValue: Value
        get() = location.value
        set(newValue) {
            location.value = newValue
        }

    var projectedValue: Binding<Value> = this

    fun transaction(transaction: Transaction): Binding<Value>? {
        exitProcess(0)
    }

    //public subscript<Subject>(dynamicMember keyPath: WritableKeyPath<Value, Subject>) -> Binding<Subject> {
    //    System.exit(0);
    //    return null;
    //}

}

fun <Value, V> Binding<Value>.constant(value: V): Binding<V> = exitProcess(0)

//fun <Value, V> Binding<Value>._makeProperty<V>(
//    buffer: _DynamicPropertyBuffer,
//    container: _GraphValue<V>,
//    fieldOffset: Int,
//    inputs: _GraphInputs
//) = exitProcess(0)

