package kotlinx.kotlinui

import android.graphics.Rect

interface Shape : Animatable, View {
    fun path(rect: Rect): Path
    //@JvmDefault override val body: View = exitProcess(0)
}

interface ShapeStyle

internal fun <S : Shape> ShapeStyle._makeView(view: _GraphValue<_ShapeView<S, ShapeStyle>>, inputs: _ViewInputs): _ViewOutputs = error("Not Implemented")

// val ShapeStyle.body: View = exitProcess(0)

class FillStyle(
    var isEOFilled: Boolean = true,
    var isAntialiased: Boolean = true
) {
    override fun equals(other: Any?): Boolean {
        if (other !is FillStyle) return false
        val s = other as FillStyle
        return isEOFilled.equals(s.isEOFilled) &&
            isAntialiased.equals(s.isAntialiased)
    }

    override fun hashCode(): Int {
        var result = isEOFilled.hashCode()
        result = 31 * result + isAntialiased.hashCode()
        return result
    }
}

object ForegroundStyle : ShapeStyle

internal fun <S : Shape> ForegroundStyle._makeView(view: _GraphValue<_ShapeView<S, ForegroundStyle>>, inputs: _ViewInputs): _ViewOutputs = error("Not Implemented")
