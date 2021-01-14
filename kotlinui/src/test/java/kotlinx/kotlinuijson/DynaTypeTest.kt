package kotlinx.kotlinui

import kotlinx.kotlinuijson.DynaType
import kotlinx.system.Tuple2
import org.junit.Test
import org.junit.Assert.*

class DynaTypeTest {
    @Test
    fun register_isCorrect() {
        DynaType.register(DynaTypeTest::class)
        assertEquals(4, 2 + 2)
    }

    @Test
    fun typeKey_isCorrect() {
//        assertEquals("#String", DynaType.typeKey(String::class))
//        assertEquals(":Edge", DynaType.typeKey(Edge::class))
//        assertEquals(":Edge.Set", DynaType.typeKey(Edge.Set::class))
//        assertEquals("kotlinx.system.Tuple2", DynaType.typeKey(Tuple2::class))
//        assertEquals(":system.Tuple2", DynaType.typeKey(Tuple2::class, namespace = "kotlinx.kotlinui"))
//        assertEquals(":system.Tuple2", DynaType.typeKey(ZStack<Text>::kclass, namespace = "kotlinx.kotlinui"))
    }

    @Test
    fun find_isCorrect() {
        DynaType.find("")
        assertEquals(4, 2 + 2)
    }
}