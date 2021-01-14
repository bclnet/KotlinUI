// NEW
package kotlinx.kotlinui

import kotlinx.system.KTypeBase1

class _OverlayModifier<Overlay : View>(
    var overlay: Overlay,
    var alignment: Alignment
) : KTypeBase1<Overlay>(), ViewModifier

fun <Overlay : View> View.overlay(overlay: Overlay, alignment: Alignment = Alignment.center): View =
    modifier(_OverlayModifier<Overlay>(overlay, alignment))
