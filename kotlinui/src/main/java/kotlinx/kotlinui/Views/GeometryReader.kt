// NEW
package kotlinx.kotlinui

class GeometryReader<Content : View>(
    content: ViewBuilder.(Geometry) -> Content
) : View {
    class Geometry

    override val body: Never
        get() = error("Never")
}
