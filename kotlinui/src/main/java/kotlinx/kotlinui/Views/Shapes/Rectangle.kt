package kotlinx.kotlinui

import android.graphics.Rect

class Rectangle : Shape {
    override fun path(rect: Rect): Path = error("Never")

    override val body: View
        get() = error("Never")
}
