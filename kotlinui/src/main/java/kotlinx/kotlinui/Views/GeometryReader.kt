// NEW
package kotlinx.kotlinui

import kotlin.system.exitProcess

class GeometryReader<Content : View>(content: (Geometry) -> Content) : View {
    class Geometry {

    }
    override var body: View = exitProcess(0)
}
