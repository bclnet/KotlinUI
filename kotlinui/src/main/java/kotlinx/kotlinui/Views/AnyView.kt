package kotlinx.kotlinui

import kotlinx.system.KTypeBase

open class AnyViewStorageBase

class AnyViewStorage<V : View>(var _view: V) : AnyViewStorageBase()

class AnyView private constructor(var _storage: AnyViewStorageBase) : KTypeBase(), View {

    constructor(view: View) : this(AnyViewStorage<View>(view)) {
    }

    override val body: View
        get() = error("Never")
}
