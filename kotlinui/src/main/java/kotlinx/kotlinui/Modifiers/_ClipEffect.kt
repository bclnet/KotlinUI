// NEW
package kotlinx.kotlinui

class _ClipEffect<ClipShape : Shape>(
    var shape: Shape,
    var style: FillStyle
) : ViewModifier

fun <S : Shape> View.clipShape(shape: S, style: FillStyle = FillStyle()): View =
    modifier(_ClipEffect<S>(shape, style))
