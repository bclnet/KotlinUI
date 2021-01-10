package kotlinx.kotlinui

import kotlin.system.exitProcess

class EmptyView : View {
    override var body: View = exitProcess(0)
}

internal fun EmptyView._makeView(view: _GraphValue<EmptyView>, inputs: _ViewInputs): _ViewOutputs {
    exitProcess(0)
}

internal fun EmptyView._makeViewList(
    view: _GraphValue<EmptyView>,
    inputs: _ViewListInputs
): _ViewListOutputs {
    exitProcess(0)
}