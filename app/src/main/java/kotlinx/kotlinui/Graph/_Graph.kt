package kotlinx.kotlinui

import kotlin.system.exitProcess

class _Graph

class _GraphInputs

class _ViewInputs

class _ViewListInputs

class _ViewOutputs

class _ViewListOutputs

class _GraphValue<Value>(var value: Value) {
    fun <U> subscript(keyPath: KeyPath<Value, U>): _GraphValue<U> {
        exitProcess(0)
    }

    override fun equals(o: Any?): Boolean {
        if (o !is _GraphValue<*>) return false
        val s = o as _GraphValue<Value>
        return value!!.equals(s.value)
    }
}