@file:OptIn(ExperimentalStdlibApi::class)

package kotlinx.kotlinuijson

import kotlin.reflect.typeOf

// MARK: - Variable
fun JsonContext.nextKey(key: String?): Pair<String, Int> =
    Pair("${slots.count()}", 1)

fun JsonContext.`var`(value: Boolean, key: String? = null): Boolean {
    val (id, _) = nextKey(key)
    slots[id] = JsonContext.Slot(typeOf<Boolean>(), value)
    return false
}

fun JsonContext.`var`(value: String, key: String? = null): String {
    val (id, _) = nextKey(key)
    slots[id] = JsonContext.Slot(typeOf<String>(), value)
    return "#$id#"
}

fun JsonContext.`var`(value: Double, key: String? = null): Double {
    val (id, slot) = nextKey(key)
    slots[id] = JsonContext.Slot(typeOf<Double>(), value)
    return slot.toDouble()
}

fun JsonContext.`var`(value: Float, key: String? = null): Float {
    val (id, slot) = nextKey(key)
    slots[id] = JsonContext.Slot(typeOf<Float>(), value)
    return slot.toFloat()
}

fun JsonContext.`var`(value: UInt, key: String? = null): UInt {
    val (id, slot) = nextKey(key)
    slots[id] = JsonContext.Slot(typeOf<UInt>(), value)
    return slot.toUInt()
}

fun JsonContext.`var`(value: UByte, key: String? = null): UByte {
    val (id, slot) = nextKey(key)
    slots[id] = JsonContext.Slot(typeOf<UByte>(), value)
    return slot.toUByte()
}

fun JsonContext.`var`(value: UShort, key: String? = null): UShort {
    val (id, slot) = nextKey(key)
    slots[id] = JsonContext.Slot(typeOf<UShort>(), value)
    return slot.toUShort()
}

fun JsonContext.`var`(value: ULong, key: String? = null): ULong {
    val (id, slot) = nextKey(key)
    slots[id] = JsonContext.Slot(typeOf<ULong>(), value)
    return slot.toULong()
}

inline fun <reified T> JsonContext.`var`(value: T, key: String? = null): T {
    val (id, _) = nextKey(key)
    slots[id] = JsonContext.Slot(typeOf<T>(), value as Any)
    error("Not Implemented")
}
