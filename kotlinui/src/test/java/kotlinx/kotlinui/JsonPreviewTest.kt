package kotlinx.kotlinui

import kotlinx.kotlinuijson.*
import org.junit.Test
import org.junit.Assert.*

class JsonPreviewTest {

    @Test
    fun test_complex() {
        val preview = JsonPreview {
            VStack {
                +Text("Verbatim")
                +Text("Verbatim")
                get()
            }
        }
        assertEquals(4, 2 + 2)
    }
}