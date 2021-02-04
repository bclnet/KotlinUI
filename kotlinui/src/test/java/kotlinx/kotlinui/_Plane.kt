package kotlinx.kotlinui

import android.graphics.Color
import android.graphics.ColorSpace
import android.icu.util.DateInterval
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import kotlinx.kotlinuijson.JsonUI
import kotlinx.ptype.ActionManager
import kotlinx.serialization.json.Json
import org.junit.Assert
import org.junit.Test
import org.junit.Assert.*
import java.util.*
import android.graphics.Color as CGColor

fun _Plane.register() {
    mockColors()
    JsonUI.registered
}

fun _Plane.mockActionManager() {
    ActionManager.mocked = true
}

fun _Plane.mockColors() {
    colorSpaces = Array(1) {
        val colorSpace = mockk<ColorSpace>(relaxed = true)
        every { colorSpace.name } returns when (it) {
            0 -> "SRGB"
            else -> error("$it")
        }
        colorSpace
    }
    colors = Array(14) { mockk(relaxed = true) }

    // mock ColorSpace
    mockkStatic(ColorSpace::class)
    every { ColorSpace.get(ColorSpace.Named.SRGB) } returns colorSpaces[0]!!

    // mock Color
    mockkStatic(CGColor::class)
    every { Color.valueOf(any(), any()) } returns makeCGColor(10f, 10f, 10f)
}

fun _Plane.makeCGColor(r: Float, g: Float, b: Float): CGColor {
    val color = mockk<CGColor>(relaxed = true)
    every { color.colorSpace } returns colorSpaces[0]!!
    every { color.components } returns arrayOf(r, g, b).toFloatArray()
    return color
}

fun _Plane.makeDateInterval(): DateInterval {
    val dateInterval = mockk<DateInterval>(relaxed = true)
    every { dateInterval == any() } returns true
    return dateInterval
}

fun _Plane.makeDate(): Date {
    val date = mockk<Date>(relaxed = true)
    every { date == any() } returns true
    return date
}

//    every { ColorSpace.Named.valueOf(any()) } returns ColorSpace.Named.SRGB
//    val cgColor = mockk<CGColor>(relaxed = true)
//    every { cgColor.colorSpace.name } returns "SRGB"
//    every { Color.valueOf(any()) } returns cgColor
//    every { Color.valueOf(any(), any(), any()) } returns cgColor
//    every { Color.valueOf(any(), any()) } returns cgColor