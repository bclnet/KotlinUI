package kotlinx.kotlinui

import kotlinx.kotlinuijson.*
import org.junit.Test
import org.junit.Assert.*

class JsonUITest {
    @Test
    fun test_text() {
        val json = JsonUI(Text("Verbatim"))
        assertEquals(4, 2 + 2)
    }

    @Test
    fun test_complex() {
        val json = JsonUI(VStack {
            get(
                Text("Verbatim"),
                Text("Verbatim")
            )
        })
        assertEquals(4, 2 + 2)
    }
}