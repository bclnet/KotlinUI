// NEW
package kotlinx.kotlinui

import android.util.SizeF
import kotlinx.system.KTypeBase

class _OffsetEffect(
    var offset: SizeF,
) : KTypeBase(), ViewModifier

fun View.offset(x: Float = 0f, y: Float = 0f): View =
    modifier(_OffsetEffect(SizeF(x, y)))
