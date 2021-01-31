@file: OptIn(ExperimentalStdlibApi::class)

package kotlinx.kotlinui

import kotlinx.ptype.PType
import kotlinx.serialization.json.Json
import kotlinx.serialization.serializer
import org.junit.Assert
import org.junit.Test
import org.junit.Assert.*
import kotlin.reflect.typeOf

class IndexViewStyleModifierTest {
    @Test
    fun serialize() {
        val json = Json {
            serializersModule = PType.module
            prettyPrint = true
        }

        // IndexViewStyleModifier
        val orig_ivsm = IndexViewStyleModifier(PageIndexViewStyle())
        val data_ivsm = json.encodeToString(IndexViewStyleModifier.Serializer(), orig_ivsm)
        val json_ivsm = json.decodeFromString(IndexViewStyleModifier.Serializer<IndexViewStyle>(), data_ivsm)
        Assert.assertEquals(orig_ivsm, json_ivsm)
        Assert.assertEquals(
            """{
    "style": {
        "type": ":PageIndexViewStyle"
    }
}""".trimIndent(), data_ivsm
        )
    }
}