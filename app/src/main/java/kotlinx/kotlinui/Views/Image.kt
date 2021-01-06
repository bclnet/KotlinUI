package kotlinx.kotlinui

import android.os.Bundle
import kotlin.system.exitProcess

class AnyImageProviderBox

class Image(var _provider: AnyImageProviderBox) : View {
    constructor(name: String, bundle: Bundle? = null) : this(AnyImageProviderBox()) {
        exitProcess(0)
    }

    constructor(name: String, bundle: Bundle? = null, label: Text) : this(AnyImageProviderBox()) {
        exitProcess(0)
    }

//    constructor(decorative: String, bundle: Bundle? = null) : this(AnyImageProviderBox()) {
//        exitProcess(0)
//    }

//    constructor(systemName: String) : this(AnyImageProviderBox()) {
//        exitProcess(0)
//    }

    override fun equals(o: Any?): Boolean {
        if (o !is Image) return false
        val s = o as Image
        return _provider.equals(s._provider)
    }

    override val body: View = exitProcess(0)
}
