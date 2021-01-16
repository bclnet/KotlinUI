@file:OptIn(ExperimentalStdlibApi::class)

package kotlinx.kotlinuijson

import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.*
import kotlinx.serialization.encoding.*
import kotlinx.serialization.serializer
import kotlinx.system.*
import kotlin.Exception
import kotlin.reflect.KType
import kotlin.reflect.typeOf

val typeParseTokens_breaks: CharArray = arrayOf('<', '(', ',', ')', '>').toCharArray()

class DynaTypeNotFoundException(named: String) : Exception(named)
class DynaTypeParseErrorException(key: String) : Exception(key)
class DynaTypeNameErrorException(actual: String, expected: String) : Exception("actual: $actual, expected: $expected")
class DynaTypeNotCodableException(mode: String, key: String) : Exception("mode: $mode, key: $key")

@Serializable(with = DynaTypeWithNilSerializer::class)
class DynaTypeWithNil(val type: DynaType, val hasNil: Boolean)

internal object DynaTypeWithNilSerializer : KSerializer<DynaTypeWithNil> {
    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("DynaTypeWithNil", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, value: DynaTypeWithNil) =
        encoder.encodeString(if (value.hasNil) value.type.underlyingKey else "$value.type.underlyingKey:nil")

    override fun deserialize(decoder: Decoder): DynaTypeWithNil {
        val raw = decoder.decodeString()
        return if (!raw.endsWith(":nil")) DynaTypeWithNil(DynaType.find(raw), false)
        else DynaTypeWithNil(DynaType.find(raw.substring(0 until raw.length - 4)), true)
    }
}

fun CompositeEncoder.encodeSuper(descriptor: SerialDescriptor, index: Int, value: Pair<KType, Any>) {
    val hasNil = false
    val dynaTypeWithNil = DynaTypeWithNil(DynaType.typeFor(value.first), hasNil)
    this.encodeSerializableElement(descriptor, index, DynaTypeWithNilSerializer, dynaTypeWithNil)
//            if hasNil { return }
//            let unwrap = Mirror.optional(any: value)!
//            guard let encodable = unwrap as? Encodable else {
//                let newValue = try DynaType.convert(value: unwrap)
//                guard let encodable2 = newValue as? Encodable else {
//                    throw DynaTypeError.typeNotCodable("encodeDynaSuper", key: DynaType.typeKey(for: unwrap))
//                }
//                try encodable2.encode(to: self)
//                    return
//                }
//            try encodable.encode(to: self)
//            }
}


@Serializable(with = DynaTypeSerializer::class)
sealed class DynaType {
    data class type(val type: KType, val key: String) : DynaType()
    data class tuple(val type: KType, val key: String, val components: Array<DynaType>) : DynaType()
    data class generic(
        val type: KType,
        val key: String,
        val any: String,
        val components: Array<DynaType>
    ) : DynaType()

    val underlyingKey: String
        get() = when (this) {
            is type -> this.key
            is tuple -> this.key
            is generic -> this.key
        }

    val underlyingAny: String
        get() = when (this) {
            is type -> this.key
            is tuple -> this.key
            is generic -> this.any
        }

    val underlyingType: KType
        get() = when (this) {
            is type -> this.type
            is tuple -> this.type
            is generic -> this.type
        }

    operator fun get(index: Int): DynaType =
        if (index > -1) {
            when (this) {
                is type -> this
                is tuple -> this.components[index]
                is generic -> this.components[index]
            }
        } else this

    // MARK: - Register
    companion object {
        var knownTypes = hashMapOf<String, DynaType>()
        var knownGenerics = hashMapOf<String, Pair<HashMap<String, KType>, Array<KType?>?>>()
        var optionalTypes = hashMapOf<Any, KType>()

        //var convertTypes = HashMap<String,Convertible.Type>()
        var actionTypes = HashMap<String, HashMap<String, Any>>()

        inline fun <reified T> register(any: Array<KType?>? = null, namespace: String? = null, actions: HashMap<String, Any>? = null, alias: String? = null) {
            val type = typeOf<T>()
            val key = typeKey(type, namespace)
            if (knownTypes[key] == null) {
                // register
                val typeOptional = typeOf<T?>()
                val keyOptional = typeKey(typeOptional, namespace)
                val genericIdx = key.indexOf('<')
                val baseKey = if (genericIdx == -1) key else key.substring(0 until genericIdx)
                knownTypes[key] = type(type, key)
                knownTypes[keyOptional] = type(typeOptional, keyOptional)
                if (alias != null) knownTypes[alias] = type(type, key)
                optionalTypes[typeOptional] = type
                // generic
                if (genericIdx != -1) {
                    val genericKey = if (baseKey != ":TupleView") baseKey else "$baseKey:${key.split(',').size}"
                    if (knownGenerics[genericKey] == null) knownGenerics[genericKey] = Pair(hashMapOf<String, KType>(), any)
                    knownGenerics[genericKey]!!.first[if (any != null) key else ""] = type
                }
//                // convert
//                if let convert = type as? Convertible.Type {
//                    convertTypes[key] = convert
//                }
            }
//
//            // actions
//            guard let actions = actions else { return }
//            guard var set = actionTypes[key] else {
//                actionTypes[key] = actions
//                return
//            }
//            for (k, v) in actions {
//                set.updateValue(v, forKey: k)
//            }
        }

        // MARK: - Lookup
        fun typeFor(type: KType): DynaType {
            val z = registered
            return find(typeKey(type))
        }

        private fun optional(type: KType): KType {
            val z = registered
            return optionalTypes[type] ?: type
        }

        // MARK: - Parse/Build

        fun typeKey(value: KType, namespace: String? = null): String =
            typeKey(value.toString(), namespace)

//        fun typeKey(value: Any, namespace: String? = null): String =
//            typeKey(value.toString(), namespace)

        fun typeKey(value: String, namespace: String? = null): String {
            val key = typeKeyRemoveExtra(value)
                .replace(" ", "")
                .replace("kotlin.Optional", "!")
                .replace("java.lang.", "#")
                .replace("kotlinx.kotlinui.", ":")
                // kotlin
                .replace("kotlinx.system.Tuple", "#Tuple")
            if (namespace == null) return key
            val keyIdx = if (!key.startsWith("!<")) 0 else 2
            val genericIdx = key.indexOf("<", keyIdx)
            val baseKey = key.substring(if (genericIdx == -1) keyIdx until key.length else keyIdx until genericIdx)
            val newBaseKey = typeKey("$namespace${baseKey.substring(baseKey.indexOf(".") until baseKey.length)}")
            return key.replace(baseKey, newBaseKey)
        }

        private fun typeKeyRemoveExtra(key: String): String =
            key.replace("(Kotlin reflection is not available)", "")

        private fun typeName(value: String): String =
            value.replace("!", "kotlin.Optional")
                .replace("#", "java.lang.")
                .replace(":", "koltinx.kotlinui.")
                // kotlin
                .replace("java.lang.Tuple", "koltinx.system.Tuple")

        private fun typeParseTokens(raw: String): List<Pair<Char, String>> {
            val name = raw.replace(" ", "")
            val tokens = mutableListOf<Pair<Char, String>>()
            var nameidx = 0
            var idx = name.indexOfAny(typeParseTokens_breaks, nameidx)
            while (idx >= 0) {
                if (nameidx != idx)
                    tokens.add(Pair('n', name.substring(nameidx until idx)))
                tokens.add(Pair(name[idx], ""))
                nameidx = idx + 1
                idx = name.indexOfAny(typeParseTokens_breaks, nameidx)
            }
            if (tokens.isEmpty())
                tokens.add(Pair('n', name))
            return tokens
        }

        fun find(forKey: String): DynaType {
            val z = registered
            val knownType = knownTypes[forKey]
            if (knownType != null)
                return knownType
            val tokens = typeParseTokens(forKey)
            var typ: DynaType = type(typeOf<Any>(), "Any")
            var key: String = forKey
            var any: String = ""
            val keys = mutableListOf<String>()
            val anys = mutableListOf<String>()
            val typs = mutableListOf<DynaType>()
            val stack = mutableListOf<Tuple4<Char, Any, String, String>>()
            for (token in tokens) {
                if (token.first == ')' || token.first == '>') {
                    var last: Tuple4<Char, Any, String, String>
                    val lastOp = if (token.first == ')') '(' else if (token.first == '>') '<' else ""
                    keys.clear(); anys.clear(); typs.clear()
                    keys.add(token.first.toString()); anys.add(token.first.toString())
                    do {
                        last = stack.removeLast()
                        when (last.v1) {
                            ',' -> {
                                typs.add(0, typ)
                                keys.add(0, key); keys.add(0, ",")
                                anys.add(0, any); anys.add(0, ",")
                            }
                            't' -> {
                                key = last.v3; any = last.v4; typ = last.v2 as DynaType
                            }
                            'n' -> {
                                key = last.v2 as String; any = key; typ = findType(key)
                            }
                            '(' -> {
                                typs.add(0, typ)
                                keys.add(0, key); keys.add(0, "("); key = keys.joinToString()
                                anys.add(0, any); anys.add(0, "("); any = anys.joinToString()
                                stack.add(Tuple4('t', findTypeTuple<Any>(key, typs), key, any))
                            }
                            '<' -> {
                                val generic = stack.removeLast()
                                val genericName = generic.v2 as String
                                typs.add(0, typ)
                                keys.add(0, key); keys.add(0, "<"); keys.add(0, genericName); key = keys.joinToString()
                                anys.add(0, any); anys.add(0, "<"); anys.add(0, genericName)
                                val knownGeneric = knownGenerics[genericName]
                                if (knownGeneric != null) {
                                    val genericAnys = knownGeneric.second
                                    if (genericAnys != null)
                                        for (i in genericAnys.indices) {
                                            val type = genericAnys[i] ?: continue
                                            anys[2 + (i * 2)] = typeKey(type)
                                        }
                                }
                                any = anys.joinToString()
                                stack.add(Tuple4('t', findTypeGeneric(key, any, genericName, typs), key, any))
                            }
                            else -> error("$last.v1")
                        }
                    } while (last.v1 != lastOp)
                } else stack.add(Tuple4(token.first, token.second, "", ""))
            }
            if (stack.size != 1) throw DynaTypeParseErrorException(key)
            val first = stack.first()
            if (first.v1 != 't') throw DynaTypeParseErrorException(key)
            if (forKey != key) throw DynaTypeNameErrorException(forKey, key)
            typ = first.v2 as DynaType
            knownTypes[forKey] = typ
            return typ
        }

        private fun findType(key: String): DynaType {
            val knownType = knownTypes[key]
            if (knownType != null) return knownType
            throw DynaTypeNotFoundException(key)
        }

        private fun <T> findTypeTuple(key: String, tuple: MutableList<DynaType>): DynaType {
            val knownType = knownTypes[key]
            if (knownType != null) return knownType
            val type: KType
            when (tuple.size) {
//                1 -> type = T::class.createType()
                2 -> type = typeOf<Tuple2<T, T>>()
                3 -> type = typeOf<Tuple3<T, T, T>>()
                4 -> type = typeOf<Tuple4<T, T, T, T>>()
                5 -> type = typeOf<Tuple5<T, T, T, T, T>>()
                6 -> type = typeOf<Tuple6<T, T, T, T, T, T>>()
                7 -> type = typeOf<Tuple7<T, T, T, T, T, T, T>>()
                8 -> type = typeOf<Tuple8<T, T, T, T, T, T, T, T>>()
                9 -> type = typeOf<Tuple9<T, T, T, T, T, T, T, T, T>>()
                10 -> type = typeOf<TupleA<T, T, T, T, T, T, T, T, T, T>>()
                else -> error("$tuple.size")
            }
            knownTypes[key] = DynaType.tuple(type, key, tuple.toTypedArray())
            return knownTypes[key]!!
        }

//        internal static func buildType<Element>(tuple dataType: Self, for s: [Element]) -> Any {
//            switch s.count {
//                case 01: return (s[0])
//                case 02: return (s[0], s[1])
//                case 03: return (s[0], s[1], s[2])
//                case 04: return (s[0], s[1], s[2], s[3])
//                case 05: return (s[0], s[1], s[2], s[3], s[4])
//                case 06: return (s[0], s[1], s[2], s[3], s[4], s[5])
//                case 07: return (s[0], s[1], s[2], s[3], s[4], s[5], s[6])
//                case 08: return (s[0], s[1], s[2], s[3], s[4], s[5], s[6], s[7])
//                case 09: return (s[0], s[1], s[2], s[3], s[4], s[5], s[6], s[7], s[8])
//                case 10: return (s[0], s[1], s[2], s[3], s[4], s[5], s[6], s[7], s[8], s[9])
//                case let unrecognized: fatalError("\(unrecognized)")
//            }
//        }

        private fun findTypeGeneric(key: String, any: String, genericName: String, generic: MutableList<DynaType>): DynaType {
            error("")
//            if let knownType = knownTypes[key] { return knownType }
//            let genericKey: String
//                switch generic[0] {
//                case .tuple(_, _, let componets) where genericName == ":TupleView": genericKey = "\(genericName):\(componets.count)"
//                default: genericKey = genericName
//            }
//            guard let v = knownGenerics[genericKey] else {
//                throw DynaTypeNotFound( key)
//            }
//            let types = v.types
//                guard let type = types[any] ?? types[""] else {
//                throw DynaTypeNotFound( key)
//            }
//            knownTypes[key] = .generic(type, key, any, generic)
//            return knownTypes[key]!
        }

        // MARK: - Register
        private val registered: Boolean = registerDefault()

        private fun registerDefault(): Boolean {
            register<String>()
            register<Int>()
            register<Boolean>()
            return true
        }
    }
}

internal object DynaTypeSerializer : KSerializer<DynaType> {
    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("DynaType", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, value: DynaType) =
        encoder.encodeString(value.underlyingKey)

    override fun deserialize(decoder: Decoder): DynaType =
        DynaType.find(decoder.decodeString())
}