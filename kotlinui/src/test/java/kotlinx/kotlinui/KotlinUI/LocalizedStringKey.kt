package kotlinx.kotlinui

import android.icu.util.DateInterval
import io.mockk.*
import kotlinx.serialization.json.Json
import org.junit.Assert
import org.junit.Test
import java.util.*

class LocalizedStringKeyTest {
    @Test
    fun serialize() {
        val json = Json {
            prettyPrint = true
        }

        // LocalizedStringKey
        val orig_s0 = LocalizedStringKey("key")
        val data_s0 = json.encodeToString(LocalizedStringKey.Serializer, orig_s0)
        val json_s0 = json.decodeFromString(LocalizedStringKey.Serializer, data_s0)
        Assert.assertEquals(orig_s0, json_s0)
        Assert.assertEquals(data_s0, "key")
    }
}