package kotlinx.kotlinui

class Tuple2<T1, T2>(val v1: T1, val v2: T2) {
    override fun hashCode(): Int {
        return v1.hashCode() xor
                v2.hashCode()
    }

    override fun equals(o: Any?): Boolean {
        if (o !is Tuple2<*, *>) return false
        val s = o as Tuple2<*, *>
        return v1!!.equals(s.v1) &&
                v2!!.equals(s.v2)
    }
}

class Tuple3<T1, T2, T3>(val v1: T1, val v2: T2, val v3: T3) {
    override fun hashCode(): Int {
        return v1.hashCode() xor
                v2.hashCode() xor
                v3.hashCode()
    }

    override fun equals(o: Any?): Boolean {
        if (o !is Tuple3<*, *, *>) return false
        val s = o as Tuple3<*, *, *>
        return v1!!.equals(s.v1) &&
                v2!!.equals(s.v2) &&
                v3!!.equals(s.v3)
    }
}

class Tuple4<T1, T2, T3, T4>(val v1: T1, val v2: T2, val v3: T3, val v4: T4) {
    override fun hashCode(): Int {
        return v1.hashCode() xor
                v2.hashCode() xor
                v3.hashCode() xor
                v4.hashCode()
    }

    override fun equals(o: Any?): Boolean {
        if (o !is Tuple4<*, *, *, *>) return false
        val s = o as Tuple4<*, *, *, *>
        return v1!!.equals(s.v1) &&
                v2!!.equals(s.v2) &&
                v3!!.equals(s.v3) &&
                v4!!.equals(s.v4)
    }
}

class Tuple5<T1, T2, T3, T4, T5>(val v1: T1, val v2: T2, val v3: T3, val v4: T4, val v5: T5) {
    override fun hashCode(): Int {
        return v1.hashCode() xor
                v2.hashCode() xor
                v3.hashCode() xor
                v4.hashCode() xor
                v5.hashCode()
    }

    override fun equals(o: Any?): Boolean {
        if (o !is Tuple5<*, *, *, *, *>) return false
        val s = o as Tuple5<*, *, *, *, *>
        return v1!!.equals(s.v1) &&
                v2!!.equals(s.v2) &&
                v3!!.equals(s.v3) &&
                v4!!.equals(s.v4) &&
                v5!!.equals(s.v5)
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
    override fun hashCode(): Int {
        return v1.hashCode() xor
                v2.hashCode() xor
                v3.hashCode() xor
                v4.hashCode() xor
                v5.hashCode() xor
                v6.hashCode()
    }

    override fun equals(o: Any?): Boolean {
        if (o !is Tuple6<*, *, *, *, *, *>) return false
        val s = o as Tuple6<*, *, *, *, *, *>
        return v1!!.equals(s.v1) &&
                v2!!.equals(s.v2) &&
                v3!!.equals(s.v3) &&
                v4!!.equals(s.v4) &&
                v5!!.equals(s.v5) &&
                v6!!.equals(s.v6)
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
    override fun hashCode(): Int {
        return v1.hashCode() xor
                v2.hashCode() xor
                v3.hashCode() xor
                v4.hashCode() xor
                v5.hashCode() xor
                v6.hashCode() xor
                v7.hashCode()
    }

    override fun equals(o: Any?): Boolean {
        if (o !is Tuple7<*, *, *, *, *, *, *>) return false
        val s = o as Tuple7<*, *, *, *, *, *, *>
        return v1!!.equals(s.v1) &&
                v2!!.equals(s.v2) &&
                v3!!.equals(s.v3) &&
                v4!!.equals(s.v4) &&
                v5!!.equals(s.v5) &&
                v6!!.equals(s.v6) &&
                v7!!.equals(s.v7)
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
    override fun hashCode(): Int {
        return v1.hashCode() xor
                v2.hashCode() xor
                v3.hashCode() xor
                v4.hashCode() xor
                v5.hashCode() xor
                v6.hashCode() xor
                v7.hashCode() xor
                v8.hashCode()
    }

    override fun equals(o: Any?): Boolean {
        if (o !is Tuple8<*, *, *, *, *, *, *, *>) return false
        val s = o as Tuple8<*, *, *, *, *, *, *, *>
        return v1!!.equals(s.v1) &&
                v2!!.equals(s.v2) &&
                v3!!.equals(s.v3) &&
                v4!!.equals(s.v4) &&
                v5!!.equals(s.v5) &&
                v6!!.equals(s.v6) &&
                v7!!.equals(s.v7) &&
                v8!!.equals(s.v8)
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
    override fun hashCode(): Int {
        return v1.hashCode() xor
                v2.hashCode() xor
                v3.hashCode() xor
                v4.hashCode() xor
                v5.hashCode() xor
                v6.hashCode() xor
                v7.hashCode() xor
                v8.hashCode() xor
                v9.hashCode()
    }

    override fun equals(o: Any?): Boolean {
        if (o !is Tuple9<*, *, *, *, *, *, *, *, *>) return false
        val s = o as Tuple9<*, *, *, *, *, *, *, *, *>
        return v1!!.equals(s.v1) &&
                v2!!.equals(s.v2) &&
                v3!!.equals(s.v3) &&
                v4!!.equals(s.v4) &&
                v5!!.equals(s.v5) &&
                v6!!.equals(s.v6) &&
                v7!!.equals(s.v7) &&
                v8!!.equals(s.v8) &&
                v9!!.equals(s.v9)
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
    override fun hashCode(): Int {
        return v1.hashCode() xor
                v2.hashCode() xor
                v3.hashCode() xor
                v4.hashCode() xor
                v5.hashCode() xor
                v6.hashCode() xor
                v7.hashCode() xor
                v8.hashCode() xor
                v9.hashCode() xor
                vA.hashCode()
    }

    override fun equals(o: Any?): Boolean {
        if (o !is TupleA<*, *, *, *, *, *, *, *, *, *>) return false
        val s = o as TupleA<*, *, *, *, *, *, *, *, *, *>
        return v1!!.equals(s.v1) &&
                v2!!.equals(s.v2) &&
                v3!!.equals(s.v3) &&
                v4!!.equals(s.v4) &&
                v5!!.equals(s.v5) &&
                v6!!.equals(s.v6) &&
                v7!!.equals(s.v7) &&
                v8!!.equals(s.v8) &&
                v9!!.equals(s.v9) &&
                vA!!.equals(s.vA)
    }
}