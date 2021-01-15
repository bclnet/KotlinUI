package kotlinx.kotlinui

import android.graphics.Rect

class Circle : Shape {
    override fun path(rect: Rect): Path = error("Never")
    override val body: View
        get() = error("Never")
}
