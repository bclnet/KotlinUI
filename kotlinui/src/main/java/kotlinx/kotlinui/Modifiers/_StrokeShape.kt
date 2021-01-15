// NEW
package kotlinx.kotlinui

class _StrokeShape<S : View>(
    var shape: S,
    var style: StrokeStyle,
) : ViewModifier

fun <S : View> Shape.stroke(content: S, lineWidth: Float = 1f): View =
    modifier(_StrokeShape(content, StrokeStyle(lineWidth = lineWidth)))

fun <S : Shape> Shape.stroke(content: S, style: StrokeStyle): View =
    modifier(_StrokeShape(content, style))
