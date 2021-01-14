// NEW
package kotlinx.kotlinui

import kotlinx.system.KTypeBase1

class _ClipEffect<ClipShape : Shape>(
    var shape: Shape,
    var style: FillStyle
) : KTypeBase1<ClipShape>(), ViewModifier

fun <S : Shape> View.clipShape(shape: S, style: FillStyle = FillStyle()): View =
    modifier(_ClipEffect<S>(shape, style))
