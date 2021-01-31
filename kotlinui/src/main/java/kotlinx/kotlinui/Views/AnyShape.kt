package kotlinx.kotlinui

import android.graphics.Rect
import kotlinx.ptype.PType

interface IAnyShape : IAnyView {
    val anyShape: AnyShape
}

data class AnyShape internal constructor(
    val storage: AnyShapeStorageBase
) : Shape {
    constructor(shape: Shape) : this(AnyShapeStorage<Shape>(shape))

    internal abstract class AnyShapeStorageBase
    internal class AnyShapeStorage<S : Shape>(val shape: S) : AnyShapeStorageBase()

    override fun path(rect: Rect): Path = error("Never")

    override val body: Never
        get() = error("Never")

    companion object {
        //: Register
        fun register() {
            PType.register<AnyShape>()
        }
    }
}
