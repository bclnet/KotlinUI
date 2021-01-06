// NEW
package kotlinx.kotlinui

class _OverlayModifier<Overlay : View>(
    var overlay: Overlay,
    var alignment: Alignment
) : ViewModifier

fun <Overlay : View> View.overlay(overlay: Overlay, alignment: Alignment = Alignment.center): View =
    modifier(_OverlayModifier<Overlay>(overlay, alignment))
