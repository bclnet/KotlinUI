package kotlinx.system

data class Tuple2<T1, T2>(val v1: T1, val v2: T2)
data class Tuple3<T1, T2, T3>(val v1: T1, val v2: T2, val v3: T3)
data class Tuple4<T1, T2, T3, T4>(val v1: T1, val v2: T2, val v3: T3, val v4: T4)
data class Tuple5<T1, T2, T3, T4, T5>(val v1: T1, val v2: T2, val v3: T3, val v4: T4, val v5: T5)
data class Tuple6<T1, T2, T3, T4, T5, T6>(val v1: T1, val v2: T2, val v3: T3, val v4: T4, val v5: T5, val v6: T6)
data class Tuple7<T1, T2, T3, T4, T5, T6, T7>(val v1: T1, val v2: T2, val v3: T3, val v4: T4, val v5: T5, val v6: T6, val v7: T7)
data class Tuple8<T1, T2, T3, T4, T5, T6, T7, T8>(val v1: T1, val v2: T2, val v3: T3, val v4: T4, val v5: T5, val v6: T6, val v7: T7, val v8: T8)
data class Tuple9<T1, T2, T3, T4, T5, T6, T7, T8, T9>(val v1: T1, val v2: T2, val v3: T3, val v4: T4, val v5: T5, val v6: T6, val v7: T7, val v8: T8, val v9: T9)
data class TupleA<T1, T2, T3, T4, T5, T6, T7, T8, T9, TA>(val v1: T1, val v2: T2, val v3: T3, val v4: T4, val v5: T5, val v6: T6, val v7: T7, val v8: T8, val v9: T9, val vA: TA)


//    override fun hashCode(): Int =
//        v1.hashCode() xor
//            v2.hashCode()
//
//    override fun equals(other: Any?): Boolean {
//        if (other !is Tuple2<*, *>) return false
//        return v1!! == other.v1 &&
//            v2!! == other.v2
//    }