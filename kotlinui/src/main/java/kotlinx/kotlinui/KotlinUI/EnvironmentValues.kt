package kotlinx.kotlinui

import kotlinx.ptype.PType
import kotlinx.system.KeyPath
import java.util.HashMap

interface EnvironmentKey {
    var key: String
    var defaultValue: Any?
}

object EnvironmentValues {
    var values: HashMap<String, Any?> = HashMap<String, Any?>()

    operator fun <V> get(key: EnvironmentKey): V = (values[key.key] ?: key.defaultValue) as V

    operator fun <V> set(key: EnvironmentKey, newValue: V) {
        values[key.key] = newValue
    }

    fun <Root, Value> find(keyPath: KeyPath<Root, Value>): String {
        return ""
    }

    //: Register
    fun register() {
    }
}
