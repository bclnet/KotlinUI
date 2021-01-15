// NEW
package kotlinx.kotlinui

import java.util.EnumSet

class _SafeAreaIgnoringLayout(
    var edges: EnumSet<Edge>,
) : ViewModifier

fun View.edgesIgnoringSafeArea(edges: EnumSet<Edge>): View =
    modifier(_SafeAreaIgnoringLayout(edges))
