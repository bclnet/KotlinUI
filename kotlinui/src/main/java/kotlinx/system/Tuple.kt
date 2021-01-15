package kotlinx.system

class Tuple2<T1, T2>(val v1: T1, val v2: T2) {
    override fun hashCode(): Int =
        v1.hashCode() xor
            v2.hashCode()

    override fun equals(other: Any?): Boolean {
        if (other !is Tuple2<*, *>) return false
        return v1!! == other.v1 &&
            v2!! == other.v2
    }
}

class Tuple3<T1, T2, T3>(val v1: T1, val v2: T2, val v3: T3) {
    override fun hashCode(): Int =
        v1.hashCode() xor
            v2.hashCode() xor
            v3.hashCode()

    override fun equals(other: Any?): Boolean {
        if (other !is Tuple3<*, *, *>) return false
        return v1!! == other.v1 &&
            v2!! == other.v2 &&
            v3!! == other.v3
    }
}

class Tuple4<T1, T2, T3, T4>(val v1: T1, val v2: T2, val v3: T3, val v4: T4) {
    override fun hashCode(): Int =
        v1.hashCode() xor
            v2.hashCode() xor
            v3.hashCode() xor
            v4.hashCode()

    override fun equals(other: Any?): Boolean {
        if (other !is Tuple4<*, *, *, *>) return false
        return v1!! == other.v1 &&
            v2!! == other.v2 &&
            v3!! == other.v3 &&
            v4!! == other.v4
    }
}

class Tuple5<T1, T2, T3, T4, T5>(val v1: T1, val v2: T2, val v3: T3, val v4: T4, val v5: T5) {
    override fun hashCode(): Int =
        v1.hashCode() xor
            v2.hashCode() xor
            v3.hashCode() xor
            v4.hashCode() xor
            v5.hashCode()

    override fun equals(other: Any?): Boolean {
        if (other !is Tuple5<*, *, *, *, *>) return false
        return v1!! == other.v1 &&
            v2!! == other.v2 &&
            v3!! == other.v3 &&
            v4!! == other.v4 &&
            v5!! == other.v5
    }
}

class Tuple6<T1, T2, T3, T4, T5, T6>(
    val v1: T1,
    val v2: T2,
    val v3: T3,
    val v4: T4,
    val v5: T5,
    val v6: T6
) {
    override fun hashCode(): Int =
        v1.hashCode() xor
            v2.hashCode() xor
            v3.hashCode() xor
            v4.hashCode() xor
            v5.hashCode() xor
            v6.hashCode()

    override fun equals(other: Any?): Boolean {
        if (other !is Tuple6<*, *, *, *, *, *>) return false
        return v1!! == other.v1 &&
            v2!! == other.v2 &&
            v3!! == other.v3 &&
            v4!! == other.v4 &&
            v5!! == other.v5 &&
            v6!! == other.v6
    }
}

class Tuple7<T1, T2, T3, T4, T5, T6, T7>(
    val v1: T1,
    val v2: T2,
    val v3: T3,
    val v4: T4,
    val v5: T5,
    val v6: T6,
    val v7: T7
) {
    override fun hashCode(): Int =
        v1.hashCode() xor
            v2.hashCode() xor
            v3.hashCode() xor
            v4.hashCode() xor
            v5.hashCode() xor
            v6.hashCode() xor
            v7.hashCode()

    override fun equals(other: Any?): Boolean {
        if (other !is Tuple7<*, *, *, *, *, *, *>) return false
        return v1!! == other.v1 &&
            v2!! == other.v2 &&
            v3!! == other.v3 &&
            v4!! == other.v4 &&
            v5!! == other.v5 &&
            v6!! == other.v6 &&
            v7!! == other.v7
    }
}

class Tuple8<T1, T2, T3, T4, T5, T6, T7, T8>(
    val v1: T1,
    val v2: T2,
    val v3: T3,
    val v4: T4,
    val v5: T5,
    val v6: T6,
    val v7: T7,
    val v8: T8
) {
    override fun hashCode(): Int =
        v1.hashCode() xor
            v2.hashCode() xor
            v3.hashCode() xor
            v4.hashCode() xor
            v5.hashCode() xor
            v6.hashCode() xor
            v7.hashCode() xor
            v8.hashCode()

    override fun equals(other: Any?): Boolean {
        if (other !is Tuple8<*, *, *, *, *, *, *, *>) return false
        return v1!! == other.v1 &&
            v2!! == other.v2 &&
            v3!! == other.v3 &&
            v4!! == other.v4 &&
            v5!! == other.v5 &&
            v6!! == other.v6 &&
            v7!! == other.v7 &&
            v8!! == other.v8
    }
}

class Tuple9<T1, T2, T3, T4, T5, T6, T7, T8, T9>(
    val v1: T1,
    val v2: T2,
    val v3: T3,
    val v4: T4,
    val v5: T5,
    val v6: T6,
    val v7: T7,
    val v8: T8,
    val v9: T9
) {
    override fun hashCode(): Int =
        v1.hashCode() xor
            v2.hashCode() xor
            v3.hashCode() xor
            v4.hashCode() xor
            v5.hashCode() xor
            v6.hashCode() xor
            v7.hashCode() xor
            v8.hashCode() xor
            v9.hashCode()

    override fun equals(other: Any?): Boolean {
        if (other !is Tuple9<*, *, *, *, *, *, *, *, *>) return false
        return v1!! == other.v1 &&
            v2!! == other.v2 &&
            v3!! == other.v3 &&
            v4!! == other.v4 &&
            v5!! == other.v5 &&
            v6!! == other.v6 &&
            v7!! == other.v7 &&
            v8!! == other.v8 &&
            v9!! == other.v9
    }
}

class TupleA<T1, T2, T3, T4, T5, T6, T7, T8, T9, TA>(
    val v1: T1,
    val v2: T2,
    val v3: T3,
    val v4: T4,
    val v5: T5,
    val v6: T6,
    val v7: T7,
    val v8: T8,
    val v9: T9,
    val vA: TA
) {
    override fun hashCode(): Int =
        v1.hashCode() xor
            v2.hashCode() xor
            v3.hashCode() xor
            v4.hashCode() xor
            v5.hashCode() xor
            v6.hashCode() xor
            v7.hashCode() xor
            v8.hashCode() xor
            v9.hashCode() xor
            vA.hashCode()

    override fun equals(other: Any?): Boolean {
        if (other !is TupleA<*, *, *, *, *, *, *, *, *, *>) return false
        return v1!! == other.v1 &&
            v2!! == other.v2 &&
            v3!! == other.v3 &&
            v4!! == other.v4 &&
            v5!! == other.v5 &&
            v6!! == other.v6 &&
            v7!! == other.v7 &&
            v8!! == other.v8 &&
            v9!! == other.v9 &&
            vA!! == other.vA
    }
}