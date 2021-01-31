@file:OptIn(ExperimentalStdlibApi::class)

package kotlinx.ptype

import kotlinx.kotlinui.*
import kotlinx.serialization.*
import kotlinx.serialization.descriptors.*
import kotlinx.serialization.encoding.*
import kotlinx.serialization.modules.*
import kotlinx.system.*
import java.io.File
import java.net.URL
import kotlin.Exception
import kotlin.reflect.*

val typeParseTokens_breaks: CharArray = arrayOf('<', '(', ',', ')', '>').toCharArray()

class PTypeNotFoundException(named: String) : Exception(named)
class PTypeParseErrorException(key: String) : Exception(key)
class PTypeNameErrorException(actual: String, expected: String) : Exception("actual: $actual, expected: $expected")
class PTypeNotCodableException(mode: String, key: String) : Exception("mode: $mode, key: $key")

//fun CompositeEncoder.encodeSuper(descriptor: SerialDescriptor, index: Int, value: Pair<KType, Any>) {
//    val hasNil = false
//    val ptypeWithNil = PTypeWithNil(PType.typeFor(value.first), hasNil)
//    this.encodeSerializableElement(descriptor, index, PTypeWithNilSerializer, ptypeWithNil)
////            if hasNil { return }
////            let unwrap = Mirror.optional(any: value)!
////            guard let encodable = unwrap as? Encodable else {
////                let newValue = try PType.convert(value: unwrap)
////                guard let encodable2 = newValue as? Encodable else {
////                    throw PTypeError.typeNotCodable("encodeDynaSuper", key: PType.typeKey(for: unwrap))
////                }
////                try encodable2.encode(to: self)
////                    return
////                }
////            try encodable.encode(to: self)
////            }
//}

interface PConvertible {
    fun init(any: Any)
}

@Serializable(with = PTypeSerializer::class)
sealed class PType {
    data class standard(
        val type_: KType,
        val key_: String
    ) : PType()

    data class tuple(
        val type_: KType,
        val key_: String,
        val components: Array<PType>
    ) : PType()

    data class generic(
        val type_: KType,
        val key_: String,
        val any_: String,
        val components: Array<PType>
    ) : PType()

    val type: KType
        get() = when (this) {
            is standard -> type_
            is tuple -> type_
            is generic -> type_
        }

    val key: String
        get() = when (this) {
            is standard -> key_
            is tuple -> key_
            is generic -> key_
        }

    val any: String
        get() = when (this) {
            is standard -> key_
            is tuple -> key_
            is generic -> any_
        }

    operator fun get(index: Int): PType =
        if (index > -1) {
            when (this) {
                is standard -> this
                is tuple -> components[index]
                is generic -> components[index]
            }
        } else this

    companion object {
        // MARK: - SerializersModule
        fun hookSerializersModule(base: SerializersModule): SerializersModule {
            val kclass = Class.forName("kotlinx.serialization.modules.SerialModuleImpl")
            kclass.getDeclaredField("class2Serializer").apply {
                isAccessible = true
                set(base, class2Serializer)
            }
            kclass.getDeclaredField("polyBase2Serializers").apply {
                isAccessible = true
                set(base, polyBase2Serializers)
            }
            kclass.getDeclaredField("polyBase2NamedSerializers").apply {
                isAccessible = true
                set(base, polyBase2NamedSerializers)
            }
            return base
        }

        // MARK: - TypeKey
        fun typeKey(value: KType, namespace: String? = null): String =
            typeKey(value.toString(), namespace)

        inline fun <reified T> typeKey(value: T, namespace: String? = null): String =
            typeKey(typeOf<T>().toString(), namespace)

        fun typeKey(value: String, namespace: String? = null): String {
            val key = typeKeyPlatform(value)
                .replace(" ", "")
                .replace("kotlin.Optional", "!")
                .replace("java.lang.", "#")
                .replace("java.util.", "#")
                .replace("kotlinx.kotlinui.", ":")
            if (namespace == null) return key
            val keyIdx = if (!key.startsWith("!<")) 0 else 2
            val genericIdx = key.indexOf("<", keyIdx)
            val baseKey = key.substring(if (genericIdx == -1) keyIdx until key.length else keyIdx until genericIdx)
            val newBaseKey = typeKey("$namespace${baseKey.substring(baseKey.indexOf(".") until baseKey.length)}")
            return key.replace(baseKey, newBaseKey)
        }

        private fun typeKeyPlatform(value: String): String {
            var key = value.replace("(Kotlin reflection is not available)", "")
                .replace("$", ".")
            // transform tuples
            while (true) {
                val idx = key.indexOf("kotlinx.system.Tuple")
                if (idx == -1)
                    return key
                val chars = key.removeRange(idx until idx + 21).toCharArray()
                chars[idx] = '('
                var depth = 0
                for (i in idx + 1 until key.length)
                    when (chars[i]) {
                        '<' -> depth++
                        '>' -> {
                            if (depth == 0) {
                                chars[i] = ')'
                                break
                            }
                            depth--
                        }
                    }
                key = String(chars)
            }
        }

        private fun typeName(value: String): String =
            value.replace("!", "kotlin.Optional")
                .replace("#", "java.lang.")
                .replace(":", "koltinx.kotlinui.")

        // MARK: - Register
        val class2Serializer = mapOf<KClass<*>, KSerializer<*>>()
        val polyBase2SerializersAny = mutableMapOf<KClass<*>, KSerializer<*>>()
        val polyBase2NamedSerializersAny = mutableMapOf<String, KSerializer<*>>()
        val polyBase2Serializers = mapOf<KClass<*>, Map<KClass<*>, KSerializer<*>>>(Any::class to polyBase2SerializersAny)
        val polyBase2NamedSerializers = mapOf<KClass<*>, Map<String, KSerializer<*>>>(Any::class to polyBase2NamedSerializersAny)

//            polyBase2Serializers[Any::class] = mapOf(
//                DefaultTabViewStyle::class to DefaultTabViewStyle.serializer(),
//                DefaultButtonStyle::class to DefaultButtonStyle.serializer(),
//                DefaultDatePickerStyle::class to DefaultDatePickerStyle.serializer(),
//                DefaultGroupBoxStyle::class to DefaultGroupBoxStyle.serializer(),
//                PageIndexViewStyle::class to PageIndexViewStyle.serializer(),
//                DefaultLabelStyle::class to DefaultLabelStyle.serializer(),
//                DefaultListStyle::class to DefaultListStyle.serializer(),
//                DefaultMenuStyle::class to DefaultMenuStyle.serializer(),
//                DefaultNavigationViewStyle::class to DefaultNavigationViewStyle.serializer(),
//                DefaultPickerStyle::class to DefaultPickerStyle.serializer(),
//                DefaultProgressViewStyle::class to DefaultProgressViewStyle.serializer(),
//                DefaultTextFieldStyle::class to DefaultTextFieldStyle.serializer(),
//                DefaultToggleStyle::class to DefaultToggleStyle.serializer(),
//            )
//            val polyBase2NamedSerializers = mutableMapOf<KClass<*>, Map<String, KSerializer<*>>>()
//            polyBase2NamedSerializers[Any::class] = mapOf(
//                ":DefaultTabViewStyle" to DefaultTabViewStyle.serializer(),
//                ":DefaultButtonStyle" to DefaultButtonStyle.serializer(),
//                ":DefaultDatePickerStyle" to DefaultDatePickerStyle.serializer(),
//                ":DefaultGroupBoxStyle" to DefaultGroupBoxStyle.serializer(),
//                ":PageIndexViewStyle" to PageIndexViewStyle.serializer(),
//                ":DefaultLabelStyle" to DefaultLabelStyle.serializer(),
//                ":DefaultListStyle" to DefaultListStyle.serializer(),
//                ":DefaultMenuStyle" to DefaultMenuStyle.serializer(),
//                ":DefaultNavigationViewStyle" to DefaultNavigationViewStyle.serializer(),
//                ":DefaultPickerStyle" to DefaultPickerStyle.serializer(),
//                ":DefaultProgressViewStyle" to DefaultProgressViewStyle.serializer(),
//                ":DefaultTextFieldStyle" to DefaultTextFieldStyle.serializer(),
//                ":DefaultToggleStyle" to DefaultToggleStyle.serializer(),
//                )


        var module = hookSerializersModule(SerializersModule { })
        var knownTypes = hashMapOf<String, PType>()
        var knownGenerics = hashMapOf<String, Pair<HashMap<String, KType>, Array<KType?>?>>()
        var optionalTypes = hashMapOf<Any, KType>()
        var convertTypes = hashMapOf<String, PConvertible>()
        var actionTypes = hashMapOf<String, HashMap<String, Any>>()

        inline fun <reified T> register(any: Array<KType?>? = null, namespace: String? = null, actions: HashMap<String, Any>? = null, alias: String? = null) {
            val type = typeOf<T>()
            val klass = T::class as KClass<*>
            val key = typeKey(type, namespace)
            val annotation = klass.annotations.find { it is Serializable } as? Serializable
            if (annotation != null) {
                val with = annotation.with as KClass<*>
                val serializer: KSerializer<*> = if (with === KSerializer::class) serializer(type)
                else (with.objectInstance ?: with.constructors.first().call()) as KSerializer<*>
                polyBase2SerializersAny[klass] = serializer
                polyBase2NamedSerializersAny[key] = serializer
            }
            if (knownTypes[key] == null) {
                // register
                val typeOptional = typeOf<T?>()
                val keyOptional = typeKey(typeOptional, namespace)
                val genericIdx = key.indexOf('<')
                val baseKey = if (genericIdx == -1) key else key.substring(0 until genericIdx)
                knownTypes[key] = standard(type, key)
                knownTypes[keyOptional] = standard(typeOptional, keyOptional)
                if (alias != null) knownTypes[alias] = standard(type, key)
                optionalTypes[typeOptional] = type
                // generic
                if (genericIdx != -1) {
                    val genericKey = if (baseKey != ":TupleView") baseKey else "$baseKey:${key.split(',').size}"
                    if (knownGenerics[genericKey] == null) knownGenerics[genericKey] = Pair(hashMapOf<String, KType>(), any)
                    knownGenerics[genericKey]!!.first[if (any != null) key else ""] = type
                }
                // convert
                val convert = type as? PConvertible
                if (convert != null)
                    convertTypes[key] = convert
            }

            // actions
            if (actions == null)
                return
            val set = actionTypes[key]
            if (set == null) {
                actionTypes[key] = actions
                return
            }
            for ((k, v) in actions)
                set[k] = v
        }

        // MARK: - Lookup
        fun typeFor(type: KType): PType {
            val z = registered
            return find(typeKey(type))
        }

        private fun optional(type: KType): KType {
            val z = registered
            return optionalTypes[type] ?: type
        }

        // MARK: - FindAction
        fun <Action> findAction(key: String, action: String): Action? =
            actionTypes[key]!![action] as? Action

        fun <Action> findActionAndType(key: String, action: String): Tuple2<Action?, PType> =
            Tuple2(findAction(key, action), find(key))

        // MARK: - Find
        private fun typeParseTokens(raw: String): List<Pair<Char, String>> {
            val name = raw.replace(" ", "")
            val tokens = mutableListOf<Pair<Char, String>>()
            var nameIndex = 0
            var index = name.indexOfAny(typeParseTokens_breaks, nameIndex)
            while (index >= 0) {
                if (nameIndex != index)
                    tokens.add(Pair('n', name.substring(nameIndex until index)))
                tokens.add(Pair(name[index], ""))
                nameIndex = index + 1
                index = name.indexOfAny(typeParseTokens_breaks, nameIndex)
            }
            if (tokens.isEmpty())
                tokens.add(Pair('n', name))
            return tokens
        }

        fun find(forKey: String): PType {
            val z = registered
            val knownType = knownTypes[forKey]
            if (knownType != null)
                return knownType
            val tokens = typeParseTokens(forKey)
            var typ: PType = standard(typeOf<Any>(), "Any")
            var key: String = forKey
            var any: String = ""
            val keys = mutableListOf<String>()
            val anys = mutableListOf<String>()
            val typs = mutableListOf<PType>()
            val stack = mutableListOf<Tuple4<Char, Any, String, String>>()
            for (token in tokens) {
                if (token.first != ')' && token.first != '>') {
                    stack.add(Tuple4(token.first, token.second, "", ""))
                    continue
                }
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
                            key = last.v3; any = last.v4; typ = last.v2 as PType
                        }
                        'n' -> {
                            key = last.v2 as String; any = key; typ = findTypeStandard(key)
                        }
                        '(' -> {
                            typs.add(0, typ)
                            keys.add(0, key); keys.add(0, "("); key = keys.joinToString("")
                            anys.add(0, any); anys.add(0, "("); any = anys.joinToString("")
                            stack.add(Tuple4('t', findTypeTuple<Any>(key, typs), key, any))
                        }
                        '<' -> {
                            val generic = stack.removeLast()
                            val genericName = generic.v2 as String
                            typs.add(0, typ)
                            keys.add(0, key); keys.add(0, "<"); keys.add(0, genericName); key = keys.joinToString("")
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
                            any = anys.joinToString("")
                            stack.add(Tuple4('t', findTypeGeneric(key, any, genericName, typs.toTypedArray()), key, any))
                        }
                        else -> error("$last.v1")
                    }
                } while (last.v1 != lastOp)
            }
            if (stack.size != 1) throw PTypeParseErrorException(key)
            val first = stack.first()
            if (first.v1 != 't') throw PTypeParseErrorException(key)
            if (forKey != key) throw PTypeNameErrorException(forKey, key)
            typ = first.v2 as PType
            knownTypes[forKey] = typ
            return typ
        }

        private fun findTypeStandard(key: String): PType {
            val knownType = knownTypes[key]
            if (knownType != null) return knownType
            throw PTypeNotFoundException(key)
        }

        private fun <T> findTypeTuple(key: String, tuple: MutableList<PType>): PType {
            val knownType = knownTypes[key]
            if (knownType != null) return knownType
            val type = when (tuple.size) {
                2 -> typeOf<Tuple2<T, T>>()
                3 -> typeOf<Tuple3<T, T, T>>()
                4 -> typeOf<Tuple4<T, T, T, T>>()
                5 -> typeOf<Tuple5<T, T, T, T, T>>()
                6 -> typeOf<Tuple6<T, T, T, T, T, T>>()
                7 -> typeOf<Tuple7<T, T, T, T, T, T, T>>()
                8 -> typeOf<Tuple8<T, T, T, T, T, T, T, T>>()
                9 -> typeOf<Tuple9<T, T, T, T, T, T, T, T, T>>()
                10 -> typeOf<TupleA<T, T, T, T, T, T, T, T, T, T>>()
                else -> error("$tuple.size")
            }
            val ptype = tuple(type, key, tuple.toTypedArray())
            knownTypes[key] = ptype
            return ptype
        }

        internal fun <Element> buildTypeTuple(type: PType, s: Array<Element>): Any =
            when (s.size) {
                2 -> Tuple2(s[0], s[1])
                3 -> Tuple3(s[0], s[1], s[2])
                4 -> Tuple4(s[0], s[1], s[2], s[3])
                5 -> Tuple5(s[0], s[1], s[2], s[3], s[4])
                6 -> Tuple6(s[0], s[1], s[2], s[3], s[4], s[5])
                7 -> Tuple7(s[0], s[1], s[2], s[3], s[4], s[5], s[6])
                8 -> Tuple8(s[0], s[1], s[2], s[3], s[4], s[5], s[6], s[7])
                9 -> Tuple9(s[0], s[1], s[2], s[3], s[4], s[5], s[6], s[7], s[8])
                10 -> TupleA(s[0], s[1], s[2], s[3], s[4], s[5], s[6], s[7], s[8], s[9])
                else -> error("$(unrecognized)")
            }

        private fun findTypeGeneric(key: String, any: String, genericName: String, generic: Array<PType>): PType {
            val knownType = knownTypes[key]
            if (knownType != null)
                return knownType
            val first = generic[0]
            val genericKey = if (first is tuple && genericName == ":TupleView") first.components.size else genericName
            val v = knownGenerics[genericKey] ?: throw PTypeNotFoundException(key)
            val types = v.first
            val type = types[any] ?: types[""] ?: throw PTypeNotFoundException(key)
            val ptype = generic(type, key, any, generic)
            knownTypes[key] = ptype
            return ptype
        }

        // MARK: - Register
        private val registered: Boolean = registerDefault()

        private fun registerDefault(): Boolean {
            register<List<*>>()
            register<String>()
            register<Int>()
            register<Boolean>()
            return true
        }
    }
}

internal object PTypeSerializer : KSerializer<PType> {
    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("PType", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, value: PType) =
        encoder.encodeString(value.key)

    override fun deserialize(decoder: Decoder): PType =
        PType.find(decoder.decodeString())
}

@Serializable(with = PTypeWithNilSerializer::class)
class PTypeWithNil(val type: PType, val hasNil: Boolean)

internal object PTypeWithNilSerializer : KSerializer<PTypeWithNil> {
    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("PTypeWithNil", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, value: PTypeWithNil) =
        encoder.encodeString(if (!value.hasNil) value.type.key else "${value.type.key}:nil")

    override fun deserialize(decoder: Decoder): PTypeWithNil {
        val raw = decoder.decodeString()
        return if (!raw.endsWith(":nil")) PTypeWithNil(PType.find(raw), false)
        else PTypeWithNil(PType.find(raw.substring(0 until raw.length - 4)), true)
    }
}