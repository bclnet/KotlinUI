// NEW
package kotlinx.kotlinui

import kotlinx.system.KTypeBase
import java.util.EnumSet

class _SafeAreaIgnoringLayout(
    var edges: EnumSet<Edge>,
) : KTypeBase(), ViewModifier

fun View.edgesIgnoringSafeArea(edges: EnumSet<Edge>): View =
    modifier(_SafeAreaIgnoringLayout(edges))
