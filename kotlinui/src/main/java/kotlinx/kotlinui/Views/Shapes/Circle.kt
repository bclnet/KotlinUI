package kotlinx.kotlinui

import android.graphics.Rect
import kotlinx.system.KTypeBase

class Circle : KTypeBase(), Shape {
    override fun path(rect: Rect): Path = error("Never")
    override val body: View
        get() = error("Never")
}
