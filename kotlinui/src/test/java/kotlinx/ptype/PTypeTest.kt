@file:OptIn(ExperimentalStdlibApi::class)

package kotlinx.ptype

import kotlinx.kotlinui.*
import kotlinx.serialization.json.Json
import kotlinx.system.*
import org.junit.Test
import org.junit.Assert.*
import kotlin.reflect.typeOf

class PTypeTest {
    @Test
    fun typeKey_test() {
        // standard type
        assertEquals("#String", PType.typeKey(typeOf<String>()))
        assertEquals("#String?", PType.typeKey(typeOf<String?>()))
        assertEquals(":Edge", PType.typeKey(typeOf<Edge>()))
        assertEquals(":Edge.Set", PType.typeKey(typeOf<Edge.Set>()))

        // tuple type
        assertEquals("(#String,#String)", PType.typeKey(typeOf<Tuple2<String, String>>()))
        assertEquals("(#String,#String)?", PType.typeKey(typeOf<Tuple2<String, String>?>()))
        assertEquals("((#String,#String),#String)", PType.typeKey(typeOf<Tuple2<Tuple2<String, String>, String>>()))
        assertEquals("(#List<#String>,#String)", PType.typeKey(typeOf<Tuple2<List<String>, String>>()))

        // generic type
        assertEquals("#List<#String>", PType.typeKey(typeOf<List<String>>()))
    }

    @Test
    fun register_test() {
        // standard type
        PType.register<PTypeTest>()
        assertEquals("kotlinx.ptype.PTypeTest", PType.find("kotlinx.ptype.PTypeTest")?.key)

        // generic type
        PType.register<List<*>>()
        assertEquals("#List<#String>", PType.find("#List<#String>")?.key)
    }

    @Test
    fun typeFor_test() {
        // standard type
        assertEquals("#String", PType.typeFor(typeOf<String>())?.key)

        // tuple type
        assertEquals("(#String,#String)", PType.typeFor(typeOf<Tuple2<String, String>>())?.key)

        // generic type
        assertEquals("#List<#String>", PType.typeFor(typeOf<List<String>>())?.key)
    }

    @Test
    fun find_test() {
        // standard type
        assertEquals("#String", PType.find("#String")?.key)

        // tuple type
        assertEquals("(#String,#String)", PType.find("(#String,#String)")?.key)

        // generic type
        assertEquals("#List<#String>", PType.find("#List<#String>")?.key)
    }

    @Test
    fun serialize_test() {
        // standard type
        assertEquals("\"#String\"", Json.encodeToString(PTypeSerializer, PType.find("#String")))
        assertEquals("\"#String\"", Json.encodeToString(PTypeWithNilSerializer, PTypeWithNil(PType.find("#String"), false)))
        assertEquals("\"#String:nil\"", Json.encodeToString(PTypeWithNilSerializer, PTypeWithNil(PType.find("#String"), true)))

        // tuple type
        assertEquals("\"(#String,#String)\"", Json.encodeToString(PTypeSerializer, PType.find("(#String,#String)")))
        assertEquals("\"(#String,#String)\"", Json.encodeToString(PTypeWithNilSerializer, PTypeWithNil(PType.find("(#String,#String)"), false)))
        assertEquals("\"(#String,#String):nil\"", Json.encodeToString(PTypeWithNilSerializer, PTypeWithNil(PType.find("(#String,#String)"), true)))

        // generic type
        assertEquals("\"#List<#String>\"", Json.encodeToString(PTypeSerializer, PType.find("#List<#String>")))
        assertEquals("\"#List<#String>\"", Json.encodeToString(PTypeWithNilSerializer, PTypeWithNil(PType.find("#List<#String>"), false)))
        assertEquals("\"#List<#String>:nil\"", Json.encodeToString(PTypeWithNilSerializer, PTypeWithNil(PType.find("#List<#String>"), true)))
    }
}