package kotlinx.kotlinuijson

sealed class DynaType {
    data class type(val type: Any, var key: String) : DynaType()
    data class tuple(val type: Any, var key: String, var components: Array<DynaType>) : DynaType()
    data class generic(
        val type: Any,
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

    var underlyingType: Any =
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
}