package kotlinx.kotlinui

import android.graphics.Rect
import kotlin.system.exitProcess

interface Shape : Animatable, View {
    fun path(rect: Rect): Path
    //@JvmDefault override val body: View = exitProcess(0)
}

interface ShapeStyle

internal fun <S : Shape> ShapeStyle._makeView(
    view: _GraphValue<_ShapeView<S, ShapeStyle>>,
    inputs: _ViewInputs
): _ViewOutputs = exitProcess(0)

// val ShapeStyle.body: View = exitProcess(0)

class FillStyle(
    var isEOFilled: Boolean = true,
    var isAntialiased: Boolean = true
) {
    override fun equals(o: Any?): Boolean {
        if (o !is FillStyle) return false
        val s = o as FillStyle
        return isEOFilled.equals(s.isEOFilled) &&
            isAntialiased.equals(s.isAntialiased)
    }
}

object ForegroundStyle : ShapeStyle

internal fun <S : Shape> ForegroundStyle._makeView(
    view: _GraphValue<_ShapeView<S, ForegroundStyle>>,
    inputs: _ViewInputs
): _ViewOutputs = exitProcess(0)
