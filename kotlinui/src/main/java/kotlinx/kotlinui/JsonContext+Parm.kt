@file:OptIn(ExperimentalStdlibApi::class)

package kotlinx.kotlinui

import kotlin.reflect.typeOf

// MARK: - Variable
fun JsonContext.nextKey(key: String?): Pair<String, Int> =
    Pair("${slots.count()}", 1)

fun JsonContext.let(value: Boolean, key: String? = null): Boolean {
    val (id, _) = nextKey(key)
    slots[id] = JsonContext.Slot(typeOf<Boolean>(), value)
    return false
}

fun JsonContext.let(value: String, key: String? = null): String {
    val (id, _) = nextKey(key)
    slots[id] = JsonContext.Slot(typeOf<String>(), value)
    return "#$id#"
}

fun JsonContext.let(value: Double, key: String? = null): Double {
    val (id, slot) = nextKey(key)
    slots[id] = JsonContext.Slot(typeOf<Double>(), value)
    return slot.toDouble()
}

fun JsonContext.let(value: Float, key: String? = null): Float {
    val (id, slot) = nextKey(key)
    slots[id] = JsonContext.Slot(typeOf<Float>(), value)
    return slot.toFloat()
}

fun JsonContext.let(value: Byte, key: String? = null): Byte {
    val (id, slot) = nextKey(key)
    slots[id] = JsonContext.Slot(typeOf<Byte>(), value)
    return slot.toByte()
}

fun JsonContext.let(value: Short, key: String? = null): Short {
    val (id, slot) = nextKey(key)
    slots[id] = JsonContext.Slot(typeOf<Short>(), value)
    return slot.toShort()
}

fun JsonContext.let(value: Int, key: String? = null): Int {
    val (id, slot) = nextKey(key)
    slots[id] = JsonContext.Slot(typeOf<Int>(), value)
    return slot.toInt()
}

fun JsonContext.let(value: Long, key: String? = null): Long {
    val (id, slot) = nextKey(key)
    slots[id] = JsonContext.Slot(typeOf<Long>(), value)
    return slot.toLong()
}

fun JsonContext.let(value: UByte, key: String? = null): UByte {
    val (id, slot) = nextKey(key)
    slots[id] = JsonContext.Slot(typeOf<UByte>(), value)
    return slot.toUByte()
}

fun JsonContext.let(value: UShort, key: String? = null): UShort {
    val (id, slot) = nextKey(key)
    slots[id] = JsonContext.Slot(typeOf<UShort>(), value)
    return slot.toUShort()
}

fun JsonContext.let(value: UInt, key: String? = null): UInt {
    val (id, slot) = nextKey(key)
    slots[id] = JsonContext.Slot(typeOf<UInt>(), value)
    return slot.toUInt()
}

fun JsonContext.let(value: ULong, key: String? = null): ULong {
    val (id, slot) = nextKey(key)
    slots[id] = JsonContext.Slot(typeOf<ULong>(), value)
    return slot.toULong()
}

inline fun <reified T> JsonContext.let(value: T, key: String? = null): T {
    val (id, _) = nextKey(key)
    slots[id] = JsonContext.Slot(typeOf<T>(), value as Any)
    error("Not Implemented")
}
