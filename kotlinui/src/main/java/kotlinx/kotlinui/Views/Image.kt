package kotlinx.kotlinui

import android.os.Bundle

class AnyImageProviderBox

class Image private constructor(
    var _provider: AnyImageProviderBox
) : View {
    constructor(name: String, bundle: Bundle? = null) : this(AnyImageProviderBox()) {
        error("Not Implemented")
    }

    constructor(name: String, bundle: Bundle? = null, label: Text) : this(AnyImageProviderBox()) {
        error("Not Implemented")
    }

//    constructor(decorative: String, bundle: Bundle? = null) : this(AnyImageProviderBox()) {
//        error("Not Implemented")
//    }

//    constructor(systemName: String) : this(AnyImageProviderBox()) {
//        error("Not Implemented")
//    }

    override fun equals(other: Any?): Boolean {
        if (other !is Image) return false
        return _provider == other._provider
    }

    override fun hashCode(): Int {
        var result = _provider.hashCode()
        result = 31 * result + body.hashCode()
        return result
    }

    override val body: Never
        get() = error("Never")
}
