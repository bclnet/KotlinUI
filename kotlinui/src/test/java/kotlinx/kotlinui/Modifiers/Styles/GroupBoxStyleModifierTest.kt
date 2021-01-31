@file: OptIn(ExperimentalStdlibApi::class)

package kotlinx.kotlinui

import kotlinx.ptype.PType
import kotlinx.serialization.json.Json
import kotlinx.serialization.serializer
import org.junit.Assert
import org.junit.Test
import org.junit.Assert.*
import kotlin.reflect.typeOf

class GroupBoxStyleModifierTest {
    @Test
    fun serialize() {
        val json = Json {
            serializersModule = PType.module
            prettyPrint = true
        }

        // GroupBoxStyleModifier
        val orig_gbsm = GroupBoxStyleModifier(DefaultGroupBoxStyle())
        val data_gbsm = json.encodeToString(GroupBoxStyleModifier.Serializer(), orig_gbsm)
        val json_gbsm = json.decodeFromString(GroupBoxStyleModifier.Serializer<GroupBoxStyle>(), data_gbsm)
        Assert.assertEquals(orig_gbsm, json_gbsm)
        Assert.assertEquals(
            """{
    "style": {
        "type": ":DefaultGroupBoxStyle"
    }
}""".trimIndent(), data_gbsm
        )
    }
}