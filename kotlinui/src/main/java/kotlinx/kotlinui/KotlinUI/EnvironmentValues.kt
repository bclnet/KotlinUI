package kotlinx.kotlinui

import kotlinx.ptype.PType
import kotlinx.system.KeyPath
import kotlinx.system.WritableKeyPath
import java.util.HashMap

interface EnvironmentKey {
    var defaultValue: Any?
}

object EnvironmentValues {
    var values: HashMap<String, Any?> = HashMap<String, Any?>()

    operator fun <V> get(key: EnvironmentKey): V = (values[key::class.simpleName!!] ?: key.defaultValue) as V
    operator fun <V> set(key: EnvironmentKey, newValue: V) {
        values[key::class.simpleName!!] = newValue
    }

    fun <Values, Value> find(keyPath: KeyPath<Values, Value>): String? =
        when (keyPath.property) {
            EnvironmentValues::font -> "font"
            EnvironmentValues::foregroundColor -> "foregroundColor"
            EnvironmentValues::colorScheme -> "colorScheme"
            else -> null
        }

    //: Register
    fun register() {
//        PType.register<EnvironmentValues>()
//        PType.register<WritableKeyPath<EnvironmentValues, Boolean?>>(actions = hashMapOf())
        PType.register<WritableKeyPath<EnvironmentValues, Font?>>(actions = hashMapOf("font" to { WritableKeyPath.get<EnvironmentValues, Font?>(EnvironmentValues::font) }))
        PType.register<WritableKeyPath<EnvironmentValues, Color?>>(actions = hashMapOf("foregroundColor" to { WritableKeyPath.get<EnvironmentValues, Color?>(EnvironmentValues::foregroundColor) }))
        PType.register<WritableKeyPath<EnvironmentValues, ColorScheme>>(actions = hashMapOf("colorScheme" to { WritableKeyPath.get<EnvironmentValues, ColorScheme>(EnvironmentValues::colorScheme) }))
    }
}
