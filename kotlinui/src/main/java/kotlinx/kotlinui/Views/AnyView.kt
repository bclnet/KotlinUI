package kotlinx.kotlinui

internal abstract class AnyViewStorageBase

internal class AnyViewStorage<V : View>(val _view: V) : AnyViewStorageBase()

interface IAnyView {
    val anyView: AnyView
}

class AnyView private constructor(
    val _storage: AnyViewStorageBase
) : View {
    constructor(view: View) : this(AnyViewStorage<View>(view))

    override val body: Never
        get() = error("Never")
}
