package kotlinx.kotlinui

abstract class AnyLocationBase

open class AnyLocation<Value>(var value: Value) : AnyLocationBase()

class _DynamicPropertyBuffer

class State<Value>(value: Value) {
    var _value: Value
    var _location: AnyLocation<Value>?

    init {
        _value = value
        _location = AnyLocation(value)
    }

    var wrappedValue: Value
        get() = _location!!.value
        set(newValue) {
            _location!!.value = newValue
        }

    val projectedValue: Binding<Value>
        get() = Binding<Value>({ wrappedValue }, { newValue -> wrappedValue = newValue });
}

//internal fun <Value, V> State<Value>._makeProperty(buffer: _DynamicPropertyBuffer, container: _GraphValue<V>, fieldOffset: Int, inputs: _GraphInputs) {}
