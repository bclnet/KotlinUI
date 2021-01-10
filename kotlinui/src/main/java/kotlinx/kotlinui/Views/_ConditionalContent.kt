package kotlinx.kotlinui

import kotlin.system.exitProcess

class _ConditionalContent<TrueContent : View, FalseContent : View>(
    //var _storage: Storage<TrueContent, FalseContent>
    var _storage: Storage
) : View {
//    enum class StorageType { trueContent, falseContent }
//    class Storage<TrueContent : View, FalseContent : View>(var value: StorageType) {
//        var trueContent: TrueContent? = null
//        var falseContent: FalseContent? = null
//    }

    sealed class Storage {
        data class trueContent<TrueContent : View>(val trueContent: TrueContent) : Storage()
        data class falseContent<FalseContent : View>(val falseContent: FalseContent) : Storage()
    }

    override var body: View = exitProcess(0)
}

internal fun <TrueContent : View, FalseContent : View> _ConditionalContent<TrueContent, FalseContent>._makeView(
    view: _GraphValue<_ConditionalContent<TrueContent, FalseContent>>,
    inputs: _ViewInputs
): _ViewOutputs = exitProcess(0)

internal fun <TrueContent : View, FalseContent : View> _ConditionalContent<TrueContent, FalseContent>._makeViewList(
    view: _GraphValue<_ConditionalContent<TrueContent, FalseContent>>,
    inputs: _ViewListInputs
): _ViewListOutputs = exitProcess(0)