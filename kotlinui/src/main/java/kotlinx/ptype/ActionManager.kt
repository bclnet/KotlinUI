package kotlinx.ptype

import kotlinx.serialization.descriptors.*
import kotlinx.serialization.encoding.*
import java.util.*

object ActionManager {
    var mocked = false
    val actionKeys = hashMapOf<Any, String>()
    val actionValues = hashMapOf<String, Any>()
    val funcKeys = hashMapOf<Any, String>()
    val funcValues = hashMapOf<String, Any>()

    fun action0Add(value: () -> Unit, key: String): String {
        val id = value as Any
        val kv = actionKeys[id]
        if (kv == null) {
            actionKeys[id] = key
            actionValues[key] = value
            return key
        }
        return kv
    }

    fun <T> action1Add(value: (T) -> Unit, key: String): String {
        val id = value as Any
        val kv = actionKeys[id]
        if (kv == null) {
            actionKeys[id] = key
            actionValues[key] = value
            return key
        }
        return kv
    }

    fun <T1, T2> action2Add(value: (T1, T2) -> Unit, key: String): String {
        val id = value as Any
        val kv = actionKeys[id]
        if (kv == null) {
            actionKeys[id] = key
            actionValues[key] = value
            return key
        }
        return kv
    }

    fun <Result> func0Add(value: () -> Result, key: String): String {
        val id = value as Any
        val kv = actionKeys[id]
        if (kv == null) {
            funcKeys[id] = key
            funcValues[key] = value
            return key
        }
        return kv
    }

    fun <T, Result> func1Add(value: (T) -> Result, key: String): String {
        val id = value as Any
        val kv = actionKeys[id]
        if (kv == null) {
            funcKeys[id] = key
            funcValues[key] = value
            return key
        }
        return kv
    }

    fun <T1, T2, Result> func2Add(value: (T1, T2) -> Result, key: String): String {
        val id = value as Any
        val kv = actionKeys[id]
        if (kv == null) {
            funcKeys[id] = key
            funcValues[key] = value
            return key
        }
        return kv
    }

    fun action0Find(key: String): () -> Unit {
        val kv = actionValues[key] ?: error("")
        return kv as () -> Unit
    }

    fun <T> action1Find(key: String): (T) -> Unit {
        val kv = actionValues[key] ?: error("")
        return kv as (T) -> Unit
    }

    fun <T1, T2> action2Find(key: String): (T1, T2) -> Unit {
        val kv = actionValues[key] ?: error("")
        return kv as (T1, T2) -> Unit
    }

    fun <Result> func0Find(key: String): () -> Result {
        val kv = funcValues[key] ?: error("")
        val foo = kv as () -> Result
        return { -> foo() }
    }

    fun <T, Result> func1Find(key: String): (T) -> Result {
        val kv = funcValues[key] ?: error("")
        val foo = kv as (T) -> Result
        return { t -> foo(t) }
    }

    fun <T1, T2, Result> func2Find(key: String): (T1, T2) -> Result {
        val kv = funcValues[key] ?: error("")
        val foo = kv as (T1, T2) -> Result
        return { t1, t2 -> foo(t1, t2) }
    }

    internal fun nextId(): String =
        if (!mocked) UUID.randomUUID().toString()
        else "#" + (actionValues.size + funcValues.size).toString()
}

// region Encoder / Decoder

fun CompositeEncoder.encodeAction0Element(descriptor: SerialDescriptor, index: Int, value: () -> Unit) =
    encodeStringElement(descriptor, index, ActionManager.action0Add(value, ActionManager.nextId()))

fun <T> CompositeEncoder.encodeAction1Element(descriptor: SerialDescriptor, index: Int, value: (T) -> Unit) =
    encodeStringElement(descriptor, index, ActionManager.action1Add(value, ActionManager.nextId()))

fun <T1, T2> CompositeEncoder.encodeAction2Element(descriptor: SerialDescriptor, index: Int, value: (T1, T2) -> Unit) =
    encodeStringElement(descriptor, index, ActionManager.action2Add(value, ActionManager.nextId()))

fun <Result> CompositeEncoder.encodeFunc0Element(descriptor: SerialDescriptor, index: Int, value: () -> Result) =
    encodeStringElement(descriptor, index, ActionManager.func0Add(value, ActionManager.nextId()))

fun <T, Result> CompositeEncoder.encodeFunc1Element(descriptor: SerialDescriptor, index: Int, value: (T) -> Result) =
    encodeStringElement(descriptor, index, ActionManager.func1Add(value, ActionManager.nextId()))

fun <T1, T2, Result> CompositeEncoder.encodeFunc2Element(descriptor: SerialDescriptor, index: Int, value: (T1, T2) -> Result) =
    encodeStringElement(descriptor, index, ActionManager.func2Add(value, ActionManager.nextId()))

fun CompositeDecoder.decodeAction0Element(descriptor: SerialDescriptor, index: Int): () -> Unit =
    ActionManager.action0Find(decodeStringElement(descriptor, index))

fun <T> CompositeDecoder.decodeAction1Element(descriptor: SerialDescriptor, index: Int): (T) -> Unit =
    ActionManager.action1Find(decodeStringElement(descriptor, index))

fun <T1, T2> CompositeDecoder.decodeAction2Element(descriptor: SerialDescriptor, index: Int): (T1, T2) -> Unit =
    ActionManager.action2Find(decodeStringElement(descriptor, index))

fun <Result> CompositeDecoder.decodeFunc0Element(descriptor: SerialDescriptor, index: Int): () -> Result =
    ActionManager.func0Find(decodeStringElement(descriptor, index))

fun <T, Result> CompositeDecoder.decodeFunc1Element(descriptor: SerialDescriptor, index: Int): (T) -> Result =
    ActionManager.func1Find(decodeStringElement(descriptor, index))

fun <T1, T2, Result> CompositeDecoder.decodeFunc2Element(descriptor: SerialDescriptor, index: Int): (T1, T2) -> Result =
    ActionManager.func2Find(decodeStringElement(descriptor, index))

// endregion
