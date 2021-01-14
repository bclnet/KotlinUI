package kotlinx.kotlinuijson

import kotlinx.kotlinui.EdgeInsets
import kotlinx.kotlinui.ZStackSerializer
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.buildClassSerialDescriptor
import kotlinx.serialization.descriptors.element
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.encoding.decodeStructure
import kotlinx.serialization.encoding.encodeStructure
import java.util.*
import kotlin.reflect.KClass
import kotlin.reflect.KType

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
        constructor(type: KClass<out Any>, value: Any) : this(DynaType.typeFor(type), value) {}
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
    var slots = HashMap<String, Slot>()
    private var contexts = HashMap<String, JsonContext>()

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