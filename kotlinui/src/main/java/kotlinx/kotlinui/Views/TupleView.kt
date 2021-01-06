package kotlinx.kotlinui

import kotlin.system.exitProcess

class TupleView<T>(var value: T) : View {
    override var body: View = exitProcess(0)
}

fun <T> TupleView<T>._makeView(view: _GraphValue<TupleView<T>>, inputs: _ViewInputs): _ViewOutputs =
    exitProcess(0)

fun <T> TupleView<T>._makeViewList(
    view: _GraphValue<TupleView<T>>,
    inputs: _ViewListInputs
): _ViewListOutputs = exitProcess(0)