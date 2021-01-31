package kotlinx.kotlinui

import android.icu.util.DateInterval
import io.mockk.every
import io.mockk.mockk
import kotlinx.kotlinuijson.*
import kotlinx.serialization.json.Json
import kotlinx.serialization.serializer
import org.junit.Assert
import org.junit.Test
import org.junit.Assert.*
import java.util.*

class TextTest {
    @Test
    fun serialize_storage() {
        val json = Json {
            prettyPrint = true
        }

        // text
        val orig_s0 = Text(Text.Init.string, "text")
        val data_s0 = json.encodeToString(serializer(), orig_s0)
        val json_s0 = json.decodeFromString(serializer<Text>(), data_s0)
        Assert.assertEquals(orig_s0, json_s0)

        // verbatim
        val orig_s1 = Text(Text.Init.verbatim, "verbatim")
        val data_s1 = json.encodeToString(serializer(), orig_s1)
        val json_s1 = json.decodeFromString(serializer<Text>(), data_s1)
        Assert.assertEquals(orig_s1, json_s1)

        // LocalizedTextStorage
        val orig_s2 = Text(LocalizedStringKey("textKey"))
        val data_s2 = json.encodeToString(serializer(), orig_s2)
        val json_s2 = json.decodeFromString(serializer<Text>(), data_s2)
        Assert.assertEquals(orig_s2, json_s2)

        // AttachmentTextStorage
        val orig_s3 = Text(Image(Image.Init.systemName, "image"))
        val data_s3 = json.encodeToString(serializer(), orig_s3)
        val json_s3 = json.decodeFromString(serializer<Text>(), data_s3)
        Assert.assertEquals(orig_s3, json_s3)

////        // FormatterTextStorage
////        val orig_s4 = Text("FormatterTextStorage")
////        val data_s4 = json.encodeToString(serializer(), orig_s4)
////        val json_s4 = json.decodeFromString(serializer<Text>(), data_s4)
////        Assert.assertEquals(orig_s4, json_s4)

        // DateTextStorage.Interval
        val dateInterval_s5 = mockk<DateInterval>(relaxed = true)
        every { dateInterval_s5.equals(any()) } returns true
        val orig_s5 = Text(dateInterval_s5)
        val data_s5 = json.encodeToString(serializer(), orig_s5)
        val json_s5 = json.decodeFromString(serializer<Text>(), data_s5)
        Assert.assertEquals(orig_s5, json_s5)

        // DateTextStorage.Absolute
        val date_s6 = mockk<Date>(relaxed = true)
        every { date_s6.equals(any()) } returns true
        val orig_s6 = Text(date_s6, Text.DateStyle.date)
        val data_s6 = json.encodeToString(serializer(), orig_s6)
        val json_s6 = json.decodeFromString(serializer<Text>(), data_s6)
        Assert.assertEquals(orig_s6, json_s6)
    }
}