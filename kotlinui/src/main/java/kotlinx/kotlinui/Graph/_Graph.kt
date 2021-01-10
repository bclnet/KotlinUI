package kotlinx.kotlinui

import kotlinx.system.KeyPath
import kotlin.system.exitProcess

internal class _Graph

internal class _GraphInputs

internal class _ViewInputs

internal class _ViewListInputs

internal class _ViewOutputs

internal class _ViewListOutputs

internal class _GraphValue<Value>(var value: Value) {
    fun <U> subscript(keyPath: KeyPath<Value, U>): _GraphValue<U> {
        exitProcess(0)
    }

    override fun equals(o: Any?): Boolean {
        if (o !is _GraphValue<*>) return false
        val s = o as _GraphValue<Value>
        return value!!.equals(s.value)
    }
}