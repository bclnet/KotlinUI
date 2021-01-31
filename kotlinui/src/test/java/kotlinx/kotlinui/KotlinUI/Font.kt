package kotlinx.kotlinui

import io.mockk.*
import kotlinx.serialization.json.Json
import org.junit.Assert
import org.junit.Test
import android.graphics.Typeface as UXFont

class FontTest {
    @Test
    fun serialize() {
        val json = Json {
            prettyPrint = true
        }

        // Normal
        val orig_s0 = Font.title
        val data_s0 = json.encodeToString(Font.Serializer, orig_s0)
        val json_s0 = json.decodeFromString(Font.Serializer, data_s0)
        Assert.assertEquals(orig_s0, json_s0)
        Assert.assertEquals("\"title\"", data_s0)

        // NamedProvider
        val orig_np_a = Font.custom("name", 1f)
        val data_np_a = json.encodeToString(Font.Serializer, orig_np_a)
        val json_np_a = json.decodeFromString(Font.Serializer, data_np_a)
        Assert.assertEquals(orig_np_a, json_np_a)
        Assert.assertEquals(
            """{
    "provider": "named",
    "name": "name",
    "size": 1.0
}""".trimIndent(), data_np_a
        )
        val orig_np_b = Font.custom("name", 1f)
        val data_np_b = json.encodeToString(Font.Serializer, orig_np_b)
        val json_np_b = json.decodeFromString(Font.Serializer, data_np_b)
        Assert.assertEquals(orig_np_b, json_np_b)
        Assert.assertEquals(
            """{
    "provider": "named",
    "name": "name",
    "size": 1.0
}""".trimIndent(), data_np_b
        )
        val orig_np_c = Font.custom("name", 1f, Font.TextStyle.body)
        val data_np_c = json.encodeToString(Font.Serializer, orig_np_c)
        val json_np_c = json.decodeFromString(Font.Serializer, data_np_c)
        Assert.assertEquals(orig_np_c, json_np_c)
        Assert.assertEquals(
            """{
    "provider": "named",
    "name": "name",
    "size": 1.0,
    "textStyle": "body"
}""".trimIndent(), data_np_c
        )

        // PlatformFontProvider
        mockkStatic(UXFont::class)
        val uxfont = mockk<UXFont>(relaxed = true)
        every { uxfont.toString() } returns "font"
        every { UXFont.create("font", any()) } returns uxfont
        val orig_pfp = Font(uxfont)
        val data_pfp = json.encodeToString(Font.Serializer, orig_pfp)
        val json_pfp = json.decodeFromString(Font.Serializer, data_pfp)
        Assert.assertEquals(orig_pfp, json_pfp)
        Assert.assertEquals(
            """{
    "provider": "platform",
    "font": "font",
    "size": 0.0
}""".trimIndent(), data_pfp
        )

        // SystemProvider
        val orig_sp = Font.system(1f, Font.Weight.regular, Font.Design.serif)
        val data_sp = json.encodeToString(Font.Serializer, orig_sp)
        val json_sp = json.decodeFromString(Font.Serializer, data_sp)
        Assert.assertEquals(orig_sp, json_sp)
        Assert.assertEquals(
            """{
    "provider": "system",
    "size": 1.0,
    "design": "serif"
}""".trimIndent(), data_sp
        )

        // TextStyleProvider
        val orig_tsp = Font.system(Font.TextStyle.body, Font.Design.serif)
        val data_tsp = json.encodeToString(Font.Serializer, orig_tsp)
        val json_tsp = json.decodeFromString(Font.Serializer, data_tsp)
        Assert.assertEquals(orig_tsp, json_tsp)
        Assert.assertEquals(
            """{
    "provider": "textStyle",
    "style": "body",
    "design": "serif"
}""".trimIndent(), data_tsp
        )

        // ItalicModifier
        val orig_mp_im = Font.body.italic()
        val data_mp_im = json.encodeToString(Font.Serializer, orig_mp_im)
        val json_mp_im = json.decodeFromString(Font.Serializer, data_mp_im)
        Assert.assertEquals(orig_mp_im, json_mp_im)
        Assert.assertEquals(
            """{
    "provider": "italic",
    "base": "body"
}""".trimIndent(), data_mp_im
        )

        // LowercaseSmallCapsModifier
        val orig_mp_lscm = Font.body.lowercaseSmallCaps()
        val data_mp_lscm = json.encodeToString(Font.Serializer, orig_mp_lscm)
        val json_mp_lscm = json.decodeFromString(Font.Serializer, data_mp_lscm)
        Assert.assertEquals(orig_mp_lscm, json_mp_lscm)
        Assert.assertEquals(
            """{
    "provider": "lowercaseSmallCaps",
    "base": "body"
}""".trimIndent(), data_mp_lscm
        )

        // UppercaseSmallCapsModifier
        val orig_mp_ucsm = Font.body.uppercaseSmallCaps()
        val data_mp_ucsm = json.encodeToString(Font.Serializer, orig_mp_ucsm)
        val json_mp_ucsm = json.decodeFromString(Font.Serializer, data_mp_ucsm)
        Assert.assertEquals(orig_mp_ucsm, json_mp_ucsm)
        Assert.assertEquals(
            """{
    "provider": "uppercaseSmallCaps",
    "base": "body"
}""".trimIndent(), data_mp_ucsm
        )

        // MonospacedDigitModifier
        val orig_mp_mdm = Font.body.monospacedDigit()
        val data_mp_mdm = json.encodeToString(Font.Serializer, orig_mp_mdm)
        val json_mp_mdm = json.decodeFromString(Font.Serializer, data_mp_mdm)
        Assert.assertEquals(orig_mp_mdm, json_mp_mdm)
        Assert.assertEquals(
            """{
    "provider": "monospacedDigit",
    "base": "body"
}""".trimIndent(), data_mp_mdm
        )

        // WeightModifier
        val orig_mp_wm = Font.body.weight(Font.Weight.bold)
        val data_mp_wm = json.encodeToString(Font.Serializer, orig_mp_wm)
        val json_mp_wm = json.decodeFromString(Font.Serializer, data_mp_wm)
        Assert.assertEquals(orig_mp_wm, json_mp_wm)
        Assert.assertEquals(
            """{
    "provider": "weight",
    "base": "body",
    "modifier": "bold"
}""".trimIndent(), data_mp_wm
        )

        // BoldModifier
        val orig_mp_bm = Font.body.bold()
        val data_mp_bm = json.encodeToString(Font.Serializer, orig_mp_bm)
        val json_mp_bm = json.decodeFromString(Font.Serializer, data_mp_bm)
        Assert.assertEquals(orig_mp_bm, json_mp_bm)
        Assert.assertEquals(
            """{
    "provider": "bold",
    "base": "body"
}""".trimIndent(), data_mp_bm
        )

        // LeadingModifier
        val orig_mp_lm = Font.body.leading(Font.Leading.standard)
        val data_mp_lm = json.encodeToString(Font.Serializer, orig_mp_lm)
        val json_mp_lm = json.decodeFromString(Font.Serializer, data_mp_lm)
        Assert.assertEquals(orig_mp_lm, json_mp_lm)
        Assert.assertEquals(
            """{
    "provider": "leading",
    "base": "body",
    "modifier": "standard"
}""".trimIndent(), data_mp_lm
        )

        // Nested
        val orig_n = Font.custom("name", 1f)
            .weight(Font.Weight.bold)
            .leading(Font.Leading.standard)
        val data_n = json.encodeToString(Font.Serializer, orig_n)
        val json_n = json.decodeFromString(Font.Serializer, data_n)
        Assert.assertEquals(orig_n, json_n)
        Assert.assertEquals(
            """{
    "provider": "leading",
    "base": {
        "provider": "weight",
        "base": {
            "provider": "named",
            "name": "name",
            "size": 1.0
        },
        "modifier": "bold"
    },
    "modifier": "standard"
}""".trimIndent(), data_n
        )
    }
}