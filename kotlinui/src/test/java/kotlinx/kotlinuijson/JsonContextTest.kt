@file:OptIn(ExperimentalStdlibApi::class)
package kotlinx.kotlinui

import kotlinx.kotlinuijson.*
import kotlinx.serialization.json.Json
import org.junit.Test
import org.junit.Assert.*
import kotlin.reflect.typeOf

class JsonContextTest {
    @Test
    fun serialize_text() {
        val context_s0c0 = JsonContext()
        val json_s0c0 = Json.encodeToString(JsonContextSerializer, context_s0c0)

        val context_s1c0 = JsonContext()
        with (context_s1c0) {
            slots["0"] = JsonContext.Slot(typeOf<String>(), 0)
        }
        val json_s1c0 = Json.encodeToString(JsonContextSerializer, context_s1c0)

        val context_s0c1 = JsonContext()
        with (context_s0c1) {
            contexts["0"] = JsonContext()
        }
        val json_s0c1 = Json.encodeToString(JsonContextSerializer, context_s0c1)

        val context_s1c1 = JsonContext()
        with (context_s1c1) {
            slots["0"] = JsonContext.Slot(typeOf<String>(), 0)
            contexts["0"] = JsonContext()
        }
        val json_s1c1 = Json.encodeToString(JsonContextSerializer, context_s1c1)
    }
}