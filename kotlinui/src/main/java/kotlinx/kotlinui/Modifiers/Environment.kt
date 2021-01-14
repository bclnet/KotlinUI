package kotlinx.kotlinui

import kotlinx.system.KeyPath
import kotlinx.system.WritableKeyPath

internal interface DynamicProperty

class Environment<Value> : DynamicProperty {
    enum class ContentType { keyPath, value }
    class Content<Value>(var type: ContentType) {
        var keyPath: KeyPath<EnvironmentValues, Value>? = null
        var value: Value? = null
    }

    var content: Content<Value> = Content(ContentType.keyPath);

    constructor(keyPath: KeyPath<EnvironmentValues, Value>) {
        content = Content(ContentType.keyPath)
        content.keyPath = keyPath
    }

    constructor(value: Value) {
        content = Content(ContentType.value)
        content.value = value
    }

    val wrappedValue: Value
        get() =
            when (content.type) {
//            ContentType.keyPath -> EnvironmentValues()[content.keyPath]
                ContentType.value -> content.value!!
                else -> error("${content.type}")
            }

    internal fun error(): Never = error("Not Implemented")
}

class _EnvironmentKeyWritingModifier<Value>(var keyPath: WritableKeyPath<EnvironmentValues, Value>, var value: Value) : ViewModifier

fun <V> View.environment(keyPath: WritableKeyPath<EnvironmentValues, V>, value: V): View =
    modifier(_EnvironmentKeyWritingModifier(keyPath, value))
