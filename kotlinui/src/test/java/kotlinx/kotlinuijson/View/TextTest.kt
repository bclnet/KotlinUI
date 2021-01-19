package kotlinx.kotlinui

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
//
//        // verbatim
//        val orig_s1 = Text(Text.Init.verbatim, "verbatim")
//        val data_s1 = json.encodeToString(serializer(), orig_s1)
//        val json_s1 = json.decodeFromString(serializer<Text>(), data_s1)
//        Assert.assertEquals(orig_s1, json_s1)
//
//        // anyTextStorage:LocalizedTextStorage
//        val orig_s2 = Text(LocalizedStringKey("textKey"))
//        val data_s2 = json.encodeToString(serializer(), orig_s2)
//        val json_s2 = json.decodeFromString(serializer<Text>(), data_s2)
//        Assert.assertEquals(orig_s2, json_s2)
//
//        // anyTextStorage:AttachmentTextStorage
//        val orig_s3 = Text(Image(Image.Init.systemName, "image"))
//        val data_s3 = json.encodeToString(serializer(), orig_s3)
//        val json_s3 = json.decodeFromString(serializer<Text>(), data_s3)
//        Assert.assertEquals(orig_s3, json_s3)
//
////        // anyTextStorage:FormatterTextStorage
////        val orig_s4 = Text("anyTextStorage:FormatterTextStorage")
////        val data_s4 = json.encodeToString(serializer(), orig_s4)
////        val json_s4 = json.decodeFromString(serializer<Text>(), data_s4)
////        Assert.assertEquals(orig_s4, json_s4)
//
//        // anyTextStorage:DateTextStorage.interval
//        val orig_s5 = Text(Date()..Date())
//        val data_s5 = json.encodeToString(serializer(), orig_s5)
//        val json_s5 = json.decodeFromString(serializer<Text>(), data_s5)
//        Assert.assertEquals(orig_s5, json_s5)
//
//        // anyTextStorage:DateTextStorage.absolute
//        val orig_s6 = Text(Date(), Text.DateStyle.date)
//        val data_s6 = json.encodeToString(serializer(), orig_s6)
//        val json_s6 = json.decodeFromString(serializer<Text>(), data_s6)
//        Assert.assertEquals(orig_s6, json_s6)
    }
}