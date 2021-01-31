package kotlinx.kotlinui

import kotlinx.ptype.PType

interface IAnyView {
    val anyView: AnyView
}

data class AnyView internal constructor(
    val storage: AnyViewStorageBase
) : View {
    constructor(view: View) : this(AnyViewStorage<View>(view))

    internal abstract class AnyViewStorageBase
    internal data class AnyViewStorage<V : View>(val view: V) : AnyViewStorageBase()

    override val body: Never
        get() = error("Never")

    companion object {
        //: Register
        fun register() {
            PType.register<AnyView>()
        }
    }
}
