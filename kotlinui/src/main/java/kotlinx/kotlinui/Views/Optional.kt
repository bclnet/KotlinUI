package kotlinx.kotlinui

import kotlin.system.exitProcess

class Optional<Wrapped : View> : View {
    override var body: View = exitProcess(0)
}

internal fun <Wrapped : View> Optional<Wrapped>._makeView(
    view: _GraphValue<Optional<Wrapped>>,
    inputs: _ViewInputs
): _ViewOutputs =
    exitProcess(0)

internal fun <Wrapped : View> Optional<Wrapped>._makeViewList(
    view: _GraphValue<Optional<Wrapped>>,
    inputs: _ViewListInputs
): _ViewListOutputs = exitProcess(0)