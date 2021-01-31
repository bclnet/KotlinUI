package kotlinx.kotlinui

data class PropertyList(val elements: Element? = null) {
    class Tracker
    class Element
}

abstract class AnyLocationBase

open class AnyLocation<Value>(var value: Value) : AnyLocationBase()

class StoredLocation<Value>(value: Value) : AnyLocation<Value>(value)

class _DynamicPropertyBuffer

class Transaction(var plist: PropertyList = PropertyList())

data class State<Value>(
    val value: Value,
    val location: AnyLocation<Value> = AnyLocation(value)
) {
    var wrappedValue: Value
        get() = location.value
        set(newValue) {
            location.value = newValue
        }

    val projectedValue: Binding<Value>
        get() = Binding<Value>({ wrappedValue }, { newValue -> wrappedValue = newValue });
}

//internal fun <Value, V> State<Value>._makeProperty(buffer: _DynamicPropertyBuffer, container: _GraphValue<V>, fieldOffset: Int, inputs: _GraphInputs) {}
