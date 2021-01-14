package kotlinx.system

import kotlin.reflect.KClass

abstract class KTypeCompanion(base: KClass<out Any>) {
    //val kclass: String = "${::T1::class}"
    fun <T> p(input: T) = print(input)
}

open class KTypeBase {
    companion object : KTypeCompanion(KTypeBase::class)
}

open class KTypeBase1<T1> {
    companion object : KTypeCompanion(KTypeBase::class)
}

open class KTypeBase2<T1, T2> {
    companion object : KTypeCompanion(KTypeBase::class)
}

open class KTypeBase3<T1, T2, T3> {
    companion object : KTypeCompanion(KTypeBase::class)
}