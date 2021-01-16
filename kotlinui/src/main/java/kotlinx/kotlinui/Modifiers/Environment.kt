package kotlinx.kotlinui

import kotlinx.system.KeyPath
import kotlinx.system.WritableKeyPath

internal interface DynamicProperty

class Environment<Value> : DynamicProperty {
    sealed class Content {
        data class keyPath<Value>(var keyPath: KeyPath<EnvironmentValues, Value>) : Content()
        data class value<Value>(var value: Value) : Content()
    }

    private val content: Content

    constructor(keyPath: KeyPath<EnvironmentValues, Value>) {
        content = Content.keyPath(keyPath)
    }

    constructor(value: Value) {
        content = Content.value(value)
    }

    val wrappedValue: Value
        get() =
            when (content) {
//                is Content.keyPath<*> -> EnvironmentValues[content.keyPath]
                is Content.value<*> -> content.value as Value
                else -> error("$content")
            }

    internal fun error(): Never = error("Not Implemented")
}

class _EnvironmentKeyWritingModifier<Value>(var keyPath: WritableKeyPath<EnvironmentValues, Value>, var value: Value) : ViewModifier

fun <V> View.environment(keyPath: WritableKeyPath<EnvironmentValues, V>, value: V): View =
    modifier(_EnvironmentKeyWritingModifier(keyPath, value))
