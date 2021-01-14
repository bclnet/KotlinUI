package kotlinx.kotlinui

import kotlinx.system.KTypeBase2

class _ConditionalContent<TrueContent : View, FalseContent : View>(
    //var _storage: Storage<TrueContent, FalseContent>
    var _storage: Storage
) : KTypeBase2<TrueContent, FalseContent>(), View {
//    enum class StorageType { trueContent, falseContent }
//    class Storage<TrueContent : View, FalseContent : View>(var value: StorageType) {
//        var trueContent: TrueContent? = null
//        var falseContent: FalseContent? = null
//    }

    sealed class Storage {
        data class trueContent<TrueContent : View>(val trueContent: TrueContent) : Storage()
        data class falseContent<FalseContent : View>(val falseContent: FalseContent) : Storage()
    }

    override val body: View
        get() = error("Never")
}

internal fun <TrueContent : View, FalseContent : View> _ConditionalContent<TrueContent, FalseContent>._makeView(view: _GraphValue<_ConditionalContent<TrueContent, FalseContent>>, inputs: _ViewInputs): _ViewOutputs = error("Not Implemented")
internal fun <TrueContent : View, FalseContent : View> _ConditionalContent<TrueContent, FalseContent>._makeViewList(view: _GraphValue<_ConditionalContent<TrueContent, FalseContent>>, inputs: _ViewListInputs): _ViewListOutputs = error("Not Implemented")