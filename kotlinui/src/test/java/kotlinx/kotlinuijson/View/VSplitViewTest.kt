package kotlinx.kotlinui

import kotlinx.kotlinuijson.*
import kotlinx.serialization.json.Json
import kotlinx.serialization.serializer
import org.junit.Assert
import org.junit.Test
import org.junit.Assert.*

class VSplitViewTest {
    @Test
    fun serialize() {
        val json = Json {
            prettyPrint = true
        }

        val orig_s0 = VSplitView { Text("Text") }
        val data_s0 = json.encodeToString(serializer(), orig_s0)
        val json_s0 = json.decodeFromString(serializer<VSplitView<Text>>(), data_s0)
        Assert.assertEquals(orig_s0, json_s0)
    }
}