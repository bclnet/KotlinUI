package kotlinx.kotlinui

import kotlinx.system.KTypeBase1

class TupleView<T>(var value: T) : KTypeBase1<T>(), View {
    override val body: View
        get() = error("Never")
}

internal fun <T> TupleView<T>._makeView(view: _GraphValue<TupleView<T>>, inputs: _ViewInputs): _ViewOutputs = error("Not Implemented")
internal fun <T> TupleView<T>._makeViewList(view: _GraphValue<TupleView<T>>, inputs: _ViewListInputs): _ViewListOutputs = error("Not Implemented")