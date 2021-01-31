@file: OptIn(ExperimentalStdlibApi::class)

package kotlinx.kotlinui

import kotlinx.ptype.PType
import kotlinx.serialization.json.Json
import kotlinx.serialization.serializer
import org.junit.Assert
import org.junit.Test
import org.junit.Assert.*
import kotlin.reflect.typeOf

class TextFieldStyleStyleModifierTest {
    @Test
    fun serialize() {
        val json = Json {
            serializersModule = PType.module
            prettyPrint = true
        }

        // TextFieldStyleStyleModifier
        val orig_tfssm = TextFieldStyleStyleModifier(DefaultTextFieldStyle())
        val data_tfssm = json.encodeToString(TextFieldStyleStyleModifier.Serializer(), orig_tfssm)
        val json_tfssm = json.decodeFromString(TextFieldStyleStyleModifier.Serializer<TextFieldStyle>(), data_tfssm)
        Assert.assertEquals(orig_tfssm, json_tfssm)
        Assert.assertEquals(
            """{
    "style": {
        "type": ":DefaultTextFieldStyle"
    }
}""".trimIndent(), data_tfssm
        )
    }
}