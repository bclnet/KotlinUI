package kotlinx.kotlinui

import kotlin.system.exitProcess

open class AnyViewStorageBase

class AnyViewStorage<V : View>(var _view: V) : AnyViewStorageBase()

class AnyView private constructor(var _storage: AnyViewStorageBase) : View {

    constructor(view: View) : this(AnyViewStorage<View>(view)) {
    }

    override var body: View = exitProcess(0)
}
