@file: OptIn(ExperimentalStdlibApi::class)

package kotlinx.kotlinui

import kotlinx.ptype.PType
import kotlinx.serialization.json.Json
import kotlinx.serialization.serializer
import org.junit.Assert
import org.junit.Test
import org.junit.Assert.*
import kotlin.reflect.typeOf

class ToggleStyleModifierTest {
    @Test
    fun serialize() {
        val json = Json {
            serializersModule = PType.module
            prettyPrint = true
        }

        // ToggleStyleModifier
        val orig_tsm = ToggleStyleModifier(DefaultToggleStyle())
        val data_tsm = json.encodeToString(ToggleStyleModifier.Serializer(), orig_tsm)
        val json_tsm = json.decodeFromString(ToggleStyleModifier.Serializer<ToggleStyle>(), data_tsm)
        Assert.assertEquals(orig_tsm, json_tsm)
        Assert.assertEquals(
            """{
    "style": {
        "type": ":DefaultToggleStyle"
    }
}""".trimIndent(), data_tsm
        )
    }
}