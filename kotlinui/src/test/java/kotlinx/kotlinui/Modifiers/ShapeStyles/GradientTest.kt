package kotlinx.kotlinui

import android.graphics.Color as CGColor
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import kotlinx.serialization.json.Json
import org.junit.Assert
import org.junit.Test
import org.junit.Assert.*

class GradientTest {
    @Test
    fun serialize() {
        val json = Json {
            prettyPrint = true
        }

        // mock
        val cgColor = mockk<CGColor>(relaxed = true)
        every { cgColor == any() } returns true
        mockkStatic(CGColor::class)
        every { CGColor.valueOf(any()) } returns cgColor
        every { CGColor.valueOf(any(), any(), any()) } returns cgColor

        // Gradient
        val orig_g = Gradient(arrayOf(Color.red))
        val data_g = json.encodeToString(Gradient.Serializer, orig_g)
        val json_g = json.decodeFromString(Gradient.Serializer, data_g)
        Assert.assertEquals(orig_g, json_g)
        Assert.assertEquals(
            """[
    {
        "color": "clear"
    }
]""".trimIndent(), data_g
        )
    }
}