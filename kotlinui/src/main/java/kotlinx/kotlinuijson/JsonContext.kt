@file:OptIn(ExperimentalStdlibApi::class)

package kotlinx.kotlinuijson

import kotlinx.kotlinui.EdgeInsetsSerializer
import kotlinx.ptype.PType
import kotlinx.ptype.PTypeSerializer
import kotlinx.serialization.*
import kotlinx.serialization.builtins.*
import kotlinx.serialization.descriptors.*
import kotlinx.serialization.encoding.*
import kotlinx.serialization.serializer
import java.util.*
import kotlin.collections.HashMap
import kotlin.reflect.KType

@Serializable(with = JsonContextSerializer::class)
data class JsonContext(
    val slots: HashMap<String, Slot> = hashMapOf(),
    val contexts: HashMap<String, JsonContext> = hashMapOf()
) {
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
    data class Slot internal constructor(val type: PType, val value: Any) {
        constructor(type: KType, value: Any) : this(PType.typeFor(type), value)
    }

    internal object SlotSerializer : KSerializer<Slot> {
        override val descriptor: SerialDescriptor =
            buildClassSerialDescriptor("Slot") {
                element<PType>("type")
                element("default", buildClassSerialDescriptor("Any"))
            }

        override fun serialize(encoder: Encoder, value: Slot) {
            encoder.encodeStructure(EdgeInsetsSerializer.descriptor) {
                encodeSerializableElement(descriptor, 0, PTypeSerializer, value.type)
                encodeSerializableElement(descriptor, 1, serializer(value.type.type), value.value)
            }
        }

        override fun deserialize(decoder: Decoder): Slot =
            decoder.decodeStructure(descriptor) {
                lateinit var type: PType
                lateinit var value: Any
                while (true) {
                    when (val index = decodeElementIndex(descriptor)) {
                        0 -> type = decodeSerializableElement(descriptor, 0, PTypeSerializer)
                        1 -> value = decodeSerializableElement(descriptor, 1, serializer(type.type))!!
                        CompositeDecoder.DECODE_DONE -> break
                        else -> error("Unexpected index: $index")
                    }
                }
                Slot(type, value)
            }
    }

    // MARK: - Instance
    //private var state: [AnyHashable:[AnyHashable:Any]] = [AnyHashable:[AnyHashable:Any]]()

//    operator fun get(index: Any): HashMap<Any, Any> {
//        let key = index as!AnyHashable
//        guard let state = state[key] else {
//            let newState =[AnyHashable:Any]()
//            self.state[key] = newState
//            return newState
//        }
//        return state
//    }

//    fun encodeSuper(encoder: CompositeEncoder, descriptor: SerialDescriptor, index: Int, value: Pair<KType, Any>) =
//        encoder.encodeSuper(descriptor, index, value)

//    fun dynaSuperInit(from: Decoder, ptype: PType): Any {
//        //try decoder.dynaSuperInit(for: ptype)
//    }
//
//    fun decodeDynaSuper(from: Decoder): Any {
//        //try decoder.decodeDynaSuper()
//    }
}

@ExperimentalSerializationApi
internal object JsonContextSerializer : KSerializer<JsonContext> {
    override val descriptor: SerialDescriptor = mapSerialDescriptor(String.serializer().descriptor, buildClassSerialDescriptor("Any"))

    override fun serialize(encoder: Encoder, value: JsonContext) {
        val size = (if (value.slots.isEmpty()) 0 else 1) + value.contexts.size
        if (size <= 0) {
            encoder.encodeStructure(descriptor) {}
            return
        }
        val slotSerializer = serializer<HashMap<String, JsonContext.Slot>>()
        var index = 0
        val composite = encoder.beginCollection(descriptor, size)
        if (!value.slots.isEmpty()) {
            composite.encodeStringElement(descriptor, index++, "slots")
            composite.encodeSerializableElement(descriptor, index++, slotSerializer, value.slots)
        }
        if (!value.contexts.isEmpty())
            value.contexts.forEach {
                composite.encodeStringElement(descriptor, index++, it.key)
                composite.encodeSerializableElement(descriptor, index++, JsonContextSerializer, it.value)
            }
        composite.endStructure(descriptor)
    }

    override fun deserialize(decoder: Decoder): JsonContext {
        val slotSerializer = serializer<HashMap<String, JsonContext.Slot>>()
        var slots: HashMap<String, JsonContext.Slot>? = null
        val contexts = hashMapOf<String, JsonContext>()
        val composite = decoder.beginStructure(descriptor)
        while (true) {
            val index = composite.decodeElementIndex(descriptor)
            if (index == CompositeDecoder.DECODE_DONE) break
            val key = composite.decodeStringElement(descriptor, index)
            val vIndex = composite.decodeElementIndex(descriptor).also {
                require(it == index + 1) { "Value must follow key in a map, index for key: $index, returned index for value: $it" }
            }
            when (key) {
                "slots" -> slots = composite.decodeSerializableElement(descriptor, vIndex, slotSerializer)
                else -> contexts[key] = composite.decodeSerializableElement(descriptor, vIndex, JsonContextSerializer)
            }
        }
        composite.endStructure(descriptor)
        return JsonContext(slots ?: hashMapOf(), contexts)
    }
}