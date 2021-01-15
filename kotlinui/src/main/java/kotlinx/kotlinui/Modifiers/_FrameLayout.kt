// NEW
package kotlinx.kotlinui

class _FrameLayout(
    var width: Float?,
    var height: Float?,
    var alignment: Alignment,
) : ViewModifier

fun View.frame(width: Float? = null, height: Float? = null, alignment: Alignment = Alignment.center): View =
    modifier(_FrameLayout(width, height, alignment))
