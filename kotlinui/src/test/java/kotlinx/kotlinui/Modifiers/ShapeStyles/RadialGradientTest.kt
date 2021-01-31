package kotlinx.kotlinui

import android.graphics.Color as CGColor
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import kotlinx.serialization.json.Json
import org.junit.Assert
import org.junit.Test
import org.junit.Assert.*

class RadialGradientTest {
    @Test
    fun serialize() {
        val json = Json {
            prettyPrint = true
        }

        // mock
        val cgColor = mockk<CGColor>(relaxed = true)
        every { cgColor == any() } returns true
        every { cgColor.toString() } returns "color"
        mockkStatic(CGColor::class)
        every { CGColor.valueOf(any()) } returns cgColor
        every { CGColor.valueOf(any(), any(), any()) } returns cgColor

        // RadialGradient
        val orig_rg = RadialGradient(Gradient(arrayOf(Color.red)), UnitPoint.center, 1f, 2f)
        val data_rg = json.encodeToString(RadialGradient.Serializer, orig_rg)
        val json_rg = json.decodeFromString(RadialGradient.Serializer, data_rg)
        Assert.assertEquals(orig_rg, json_rg)
        Assert.assertEquals(
            """{
    "gradient": [
        {
            "color": "clear"
        }
    ],
    "center": "center",
    "startRadius": 1.0,
    "endRadius": 2.0
}""".trimIndent(), data_rg
        )
    }
}