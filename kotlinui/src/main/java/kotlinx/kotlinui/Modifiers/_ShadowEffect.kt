// NEW
package kotlinx.kotlinui

import android.util.SizeF
import kotlinx.system.KTypeBase

class _ShadowEffect(
    var color: Color,
    var radius: Float,
    var offset: SizeF
) : KTypeBase(), ViewModifier

fun View.shadow(color: Color = Color(Color.RGBColorSpace.sRGBLinear, 0.0, 0.33), radius: Float, x: Float = 0f, y: Float = 0f): View =
    modifier(_ShadowEffect(color, radius, SizeF(x, y)))
