package kotlinx.kotlinui

import kotlin.system.exitProcess

class _ConditionalContent<TrueContent : View, FalseContent : View>(
    var _storage: Storage<TrueContent, FalseContent>
) : View {
    enum class StorageType { trueContent, falseContent }
    class Storage<TrueContent : View, FalseContent : View>(var value: StorageType) {
        var trueContent: TrueContent? = null
        var falseContent: FalseContent? = null
    }

    override var body: View = exitProcess(0)
}

fun <TrueContent : View, FalseContent : View> _ConditionalContent<TrueContent, FalseContent>._makeView(
    view: _GraphValue<_ConditionalContent<TrueContent, FalseContent>>,
    inputs: _ViewInputs
): _ViewOutputs = exitProcess(0)

fun <TrueContent : View, FalseContent : View> _ConditionalContent<TrueContent, FalseContent>._makeViewList(
    view: _GraphValue<_ConditionalContent<TrueContent, FalseContent>>,
    inputs: _ViewListInputs
): _ViewListOutputs = exitProcess(0)