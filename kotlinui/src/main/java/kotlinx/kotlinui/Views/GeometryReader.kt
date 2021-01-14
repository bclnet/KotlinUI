// NEW
package kotlinx.kotlinui

import kotlinx.system.KTypeBase1

class GeometryReader<Content : View>(content: (Geometry) -> Content) : KTypeBase1<Content>(), View {
    class Geometry {
    }

    override val body: View
        get() = error("Never")
}
