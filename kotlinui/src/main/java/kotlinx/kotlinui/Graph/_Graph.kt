package kotlinx.kotlinui

import kotlinx.system.KeyPath

internal class _Graph

internal class _GraphInputs

internal class _ViewInputs

internal class _ViewListInputs

internal class _ViewOutputs

internal class _ViewListOutputs

internal class _GraphValue<Value>(var value: Value) {
    operator fun <U> get(keyPath: KeyPath<Value, U>): _GraphValue<U> = error("Not Implemented")

    override fun equals(other: Any?): Boolean {
        if (other !is _GraphValue<*>) return false
        val s = other as _GraphValue<Value>
        return value!!.equals(s.value)
    }

    override fun hashCode(): Int {
        return value?.hashCode() ?: 0
    }
}