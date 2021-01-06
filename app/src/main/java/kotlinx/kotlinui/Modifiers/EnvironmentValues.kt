package kotlinx.kotlinui

import java.util.HashMap

interface EnvironmentKey {
    var key: String
    var defaultValue: Any?
}

class EnvironmentValues {
    var values: HashMap<String, Any?> = HashMap<String, Any?>()

    operator fun <V> get(key: EnvironmentKey): V = (values[key.key] ?: key.defaultValue) as V

    operator fun <V> set(key: EnvironmentKey, newValue: V) {
        values[key.key] = newValue
    }
}
