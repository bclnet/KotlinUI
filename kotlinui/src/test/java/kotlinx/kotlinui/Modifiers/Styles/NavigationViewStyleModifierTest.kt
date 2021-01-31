@file: OptIn(ExperimentalStdlibApi::class)

package kotlinx.kotlinui

import kotlinx.ptype.PType
import kotlinx.serialization.json.Json
import kotlinx.serialization.serializer
import org.junit.Assert
import org.junit.Test
import org.junit.Assert.*
import kotlin.reflect.typeOf

class NavigationViewStyleModifierTest {
    @Test
    fun serialize() {
        val json = Json {
            serializersModule = PType.module
            prettyPrint = true
        }

        // NavigationViewStyleModifier
        val orig_nvsm = NavigationViewStyleModifier(DefaultNavigationViewStyle())
        val data_nvsm = json.encodeToString(NavigationViewStyleModifier.Serializer(), orig_nvsm)
        val json_nvsm = json.decodeFromString(NavigationViewStyleModifier.Serializer<NavigationViewStyle>(), data_nvsm)
        Assert.assertEquals(orig_nvsm, json_nvsm)
        Assert.assertEquals(
            """{
    "style": {
        "type": ":DefaultNavigationViewStyle"
    }
}""".trimIndent(), data_nvsm
        )
    }
}