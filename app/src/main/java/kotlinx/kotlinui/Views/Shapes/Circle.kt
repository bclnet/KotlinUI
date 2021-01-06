package kotlinx.kotlinui

import android.graphics.Rect
import kotlin.system.exitProcess

class Circle : Shape {
    override fun path(rect: Rect): Path = exitProcess(0)
    override val body: View = exitProcess(0)
}
