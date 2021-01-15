@file:OptIn(ExperimentalStdlibApi::class)

package kotlinx.kotlinui

import kotlinx.kotlinuijson.DynaType
import kotlinx.system.Tuple2
import org.junit.Test
import org.junit.Assert.*
import kotlin.reflect.typeOf

class DynaTypeTest {
    @Test
    fun register_isCorrect() {
        DynaType.register<DynaTypeTest>()
        assertEquals(4, 2 + 2)
    }

    @Test
    fun typeKey_isCorrect() {
        assertEquals("#String", DynaType.typeKey(typeOf<String>()))
        assertEquals(":Edge", DynaType.typeKey(typeOf<Edge>()))
        assertEquals(":Edge\$Set", DynaType.typeKey(typeOf<Edge.Set>()))
        assertEquals("#Tuple2<#String,#String>", DynaType.typeKey(typeOf<Tuple2<String, String>>()))
//        assertEquals("#Tuple2<#String,#String>", DynaType.typeKey(typeOf<Tuple2<String, String>>(), namespace = "kotlinx.kotlinui"))
        assertEquals(":ZStack<:Text>", DynaType.typeKey(typeOf<ZStack<Text>>()))
    }

    @Test
    fun find_isCorrect() {
        DynaType.find("")
        assertEquals(4, 2 + 2)
    }
}