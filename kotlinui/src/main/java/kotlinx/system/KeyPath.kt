@file: OptIn(ExperimentalStdlibApi::class)

package kotlinx.system

import kotlin.reflect.*

open class KeyPath<Values, Value>(val type: KType, val property: KProperty<Value>) {
    override fun equals(other: Any?): Boolean = other is KeyPath<*, *> && this.type == other.type
    override fun hashCode(): Int = type.hashCode()

    companion object {
        inline operator fun <reified Values, reified Value> get(property: KProperty<Value>): KeyPath<Values, Value> = KeyPath(typeOf<KeyPath<Values, Value>>(), property)
    }
}

class WritableKeyPath<Values, Value>(val mutableType: KType, val mutableProperty: KMutableProperty<Value>) : KeyPath<Values, Value>(mutableType, mutableProperty) {
    override fun equals(other: Any?): Boolean = other is WritableKeyPath<*, *> && this.mutableType == other.mutableType
    override fun hashCode(): Int = mutableType.hashCode()

    companion object {
        inline operator fun <reified Values, reified Value> get(property: KMutableProperty<Value>): WritableKeyPath<Values, Value> = WritableKeyPath(typeOf<WritableKeyPath<Values, Value>>(), property)
    }
}