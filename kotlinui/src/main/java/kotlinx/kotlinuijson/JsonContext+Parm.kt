package kotlinx.kotlinuijson

import kotlin.reflect.KClass

// MARK: - Variable
fun JsonContext.nextKey(key: String?): Pair<String, Int> =
    Pair("${slots.count()}", 1)

fun JsonContext.`var`(value: Boolean, key: String? = null): Boolean {
    val (id, _) = nextKey(key)
    slots[id] = JsonContext.Slot(Boolean::class, value)
    return false
}