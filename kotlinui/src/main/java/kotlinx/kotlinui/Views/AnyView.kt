package kotlinx.kotlinui

open class AnyViewStorageBase

class AnyViewStorage<V : View>(val _view: V) : AnyViewStorageBase()

class AnyView private constructor(
    val _storage: AnyViewStorageBase
) : View {
    constructor(view: View) : this(AnyViewStorage<View>(view))

    override val body: Never
        get() = error("Never")
}
