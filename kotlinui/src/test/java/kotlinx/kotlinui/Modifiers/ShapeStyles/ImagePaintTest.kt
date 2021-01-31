package kotlinx.kotlinui

import android.graphics.Color as CGColor
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import kotlinx.serialization.json.Json
import org.junit.Assert
import org.junit.Test
import org.junit.Assert.*

class ImagePaintTest {
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

        // AngularGradient
        val orig_ag = AngularGradient(Gradient(arrayOf(Color.red)), UnitPoint.center, Angle(1.0), Angle(2.0))
        val data_ag = json.encodeToString(AngularGradient.Serializer, orig_ag)
        val json_ag = json.decodeFromString(AngularGradient.Serializer, data_ag)
        Assert.assertEquals(orig_ag, json_ag)
        Assert.assertEquals(
            """{
    "gradient": [
        {
            "color": "clear"
        }
    ],
    "center": "center",
    "startAngle": 1.0,
    "endAngle": 2.0
}""".trimIndent(), data_ag
        )
    }
}