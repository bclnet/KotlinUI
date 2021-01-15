@file:OptIn(ExperimentalStdlibApi::class)
package kotlinx.kotlinuijson

import kotlinx.serialization.*
import kotlinx.serialization.builtins.*
import kotlinx.serialization.descriptors.*
import kotlinx.serialization.encoding.*
import kotlinx.serialization.serializer
import java.util.*
import kotlin.reflect.KType

@Serializable(with = JsonContextSerializer::class)
class JsonContext {
    // MARK: - Static
    companion object {
        var cachedContexts = WeakHashMap<String, JsonContext>()

        fun remove(index: Any) {
            val key = index::class.qualifiedName
            cachedContexts.remove(key); print("delContext: [$key]")
        }

        operator fun get(index: Any): JsonContext {
            val key = index::class.qualifiedName
            val context = cachedContexts[key]
            if (context != null)
                return context
            val newContext = JsonContext(); print("newContext: [$key]")
            cachedContexts[key] = newContext
            return newContext
        }
    }

    // MARK: - Slot
    @Serializable(with = SlotSerializer::class)
    class Slot private constructor(val type: DynaType, val value: Any) {
        constructor(type: KType, value: Any) : this(DynaType.typeFor(type), value) {}
    }

    object SlotSerializer : KSerializer<Slot> {
        override val descriptor: SerialDescriptor =
            buildClassSerialDescriptor("Slot") {
                element<DynaType>("type")
                element<Any>("default")
            }

        override fun serialize(encoder: Encoder, value: Slot) =
            encoder.encodeStructure(descriptor) {
//                encodeFloatElement(descriptor, 0, value.type)
//                encodeFloatElement(descriptor, 1, value.value)
            }

        override fun deserialize(decoder: Decoder): Slot =
            decoder.decodeStructure(descriptor) {
                error("")
//                Slot(
//                    decodeFloatElement(descriptor, 0),
//                    decodeFloatElement(descriptor, 1),
//                )
            }
    }

    // MARK: - Instance
    //private var state: [AnyHashable:[AnyHashable:Any]] = [AnyHashable:[AnyHashable:Any]]()
    var slots = hashMapOf<String, Slot>()
    internal var contexts = hashMapOf<String, JsonContext>()

//    operator fun get(index: Any): HashMap<Any, Any> {
//        let key = index as!AnyHashable
//        guard let state = state[key] else {
//            let newState =[AnyHashable:Any]()
//            self.state[key] = newState
//            return newState
//        }
//        return state
//    }

//    fun encodeDynaSuper(value: Any, to: Encoder) {
////        guard let anyState = value as? _AnyStateWrapper else {
////            try encoder.encodeDynaSuper(value)
////                return
////            }
////        try encoder.encodeDynaSuper(anyState.content)
//    }
//
//    fun dynaSuperInit(from: Decoder, dynaType: DynaType): Any {
//        //try decoder.dynaSuperInit(for: dynaType)
//    }
//
//    fun decodeDynaSuper(from: Decoder): Any {
//        //try decoder.decodeDynaSuper()
//    }
}

object JsonContextSerializer : KSerializer<JsonContext> {
    override val descriptor: SerialDescriptor = mapSerialDescriptor(String.serializer().descriptor, buildClassSerialDescriptor("Any"))

    override fun serialize(encoder: Encoder, value: JsonContext) {
        val size = if (value.slots.isEmpty()) 0 else 1 + value.contexts.count()
        if (size <= 0) return
        val keySerializer = String.serializer()
        var index = 0
        val composite = encoder.beginCollection(descriptor, size)
        if (!value.slots.isEmpty()) {
            composite.encodeSerializableElement(descriptor, index++, keySerializer, "slots")
            composite.encodeSerializableElement(descriptor, index++, serializer(), value.slots)
        }
        if (!value.contexts.isEmpty())
            value.contexts.forEach {
                composite.encodeSerializableElement(descriptor, index++, keySerializer, it.key)
                composite.encodeSerializableElement(descriptor, index++, JsonContextSerializer, it.value)
            }
        composite.endStructure(descriptor)
    }

    override fun deserialize(decoder: Decoder): JsonContext {
        error("")
    }
}