package kotlinx.kotlinuijson

import kotlin.reflect.KClass

val typeParseTokens_breaks = arrayOf("<", "(", ",", ")", ">")

sealed class DynaType {
    data class type(val type: KClass<out Any>, var key: String) : DynaType()
    data class tuple(val type: KClass<out Any>, var key: String, var components: Array<DynaType>) : DynaType()
    data class generic(
        val type: KClass<out Any>,
        var key: String,
        var any: String,
        var components: Array<DynaType>
    ) : DynaType()

    var underlyingKey: String =
        when (this) {
            is type -> this.key
            is tuple -> this.key
            is generic -> this.key
        }

    var underlyingAny: String =
        when (this) {
            is type -> this.key
            is tuple -> this.key
            is generic -> this.any
        }

    var underlyingType: KClass<out Any> =
        when (this) {
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

        var knownTypes = HashMap<String, DynaType>()
        var knownGenerics = HashMap<String, Pair<HashMap<String, Any>, Array<Any?>?>>()
        var optionalTypes = HashMap<Any, KClass<out Any>>()

        //var convertTypes = HashMap<String,Convertible.Type>()
        var actionTypes = HashMap<String, HashMap<String, Any>>()

        fun register(type: KClass<out Any>, any: Array<KClass<out Any>?>? = null, namespace: String? = null, actions: HashMap<String, Any>? = null, alias: String? = null) {
            val key = typeKey(type, namespace)
            if (knownTypes[key] == null) {
                // register
//                let typeOptional = Optional<T>.self
//                let keyOptional = typeKey(for: typeOptional, namespace: namespace)
//                let genericIdx = key.firstIndex(of: "<")
//                let baseKey = genericIdx == nil ? key : String(key[..<genericIdx!])
//                knownTypes[key] = .type(type, key)
//                knownTypes[keyOptional] = .type(typeOptional, keyOptional)
//                if alias != nil { knownTypes[alias!] = .type(type, key) }
//                optionalTypes[ObjectIdentifier(typeOptional)] = type
//                // generic
//                if genericIdx != nil {
//                    let genericKey = baseKey != ":TupleView" ? baseKey : "\(baseKey):\(key.components(separatedBy: ",").count)"
//                    if knownGenerics[genericKey] == nil { knownGenerics[genericKey] = ([String:T.Type](), any) }
//                    knownGenerics[genericKey]!.types[any != nil ? key : ""] = type
//                }
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
        fun typeFor(type: KClass<out Any>): DynaType {
            val z = registered
            return find(typeKey(type))
        }

        private fun optional(type: KClass<out Any>): KClass<out Any> {
            val z = registered
            return optionalTypes[type] ?: type
        }

        // MARK: - Parse/Build

        fun typeKey(value: KClass<out Any>, namespace: String? = null): String =
            typeKey(value.qualifiedName!!, namespace)

        fun typeKey(value: Any, namespace: String? = null): String =
            typeKey(value.toString(), namespace)

        fun typeKey(value: String, namespace: String? = null): String {
            val key = typeKeyRemoveExtra(value)
                .replace(" ", "")
                .replace("kotlin.Optional", "!")
                .replace("kotlin.", "#")
                .replace("kotlinx.kotlinui.", ":")
            if (namespace == null) return key
            val keyIdx = if (!key.startsWith("!<")) 0 else 2
            val genericIdx = key.indexOf("<", keyIdx)
            val baseKey = key.substring(if (genericIdx == -1) keyIdx until key.length else keyIdx until genericIdx)
            val newBaseKey = typeKey("$namespace${baseKey.substring(baseKey.indexOf(".") until baseKey.length)}")
            return key.replace(baseKey, newBaseKey)
        }

        private fun typeKeyRemoveExtra(key: String): String =
            key

        private fun typeName(value: String): String =
            value.replace("!", "kotlin.Optional")
                .replace("#", "kotlin.")
                .replace(":", "koltinx.kotlinui.")

        private fun typeParseTokens(raw: String): List<Pair<String, String>> {
            val name = raw.replace(" ", "")
            val tokens = mutableListOf<Pair<String, String>>()
//            var nameidx = name.startIndex
//            while let idx = name[nameidx...].firstIndex(where: { typeParseTokens_breaks.contains(String($0)) }) {
//                if nameidx != idx {
//                    tokens.append((op: "n", value: String(name[nameidx...name.index(idx, offsetBy: -1)])))
//                }
//                tokens.append((op: String(name[idx]), value: ""))
//                nameidx = name.index(idx, offsetBy: 1)
//            }
//            if tokens.isEmpty {
//                tokens.append((op: "n", value: name))
//            }
            return tokens
        }

        fun find(forKey: String): DynaType {
            val z = registered
            val knownType = knownTypes[forKey]
            if (knownType != null) return knownType
            val tokens = typeParseTokens(forKey)
            var type: DynaType = type(Any::class, "Any")
            error("")
        }

        // MARK: - Register
        private val registered: Boolean = registerDefault()

        private fun registerDefault(): Boolean {
            register(String::class)
            register(Int::class)
            register(Boolean::class)
            return true
        }
    }
}