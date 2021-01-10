package kotlinx.kotlinui

class TupleView<T>(var value: T) : View {
    override var body: View = error("Never")
}

internal fun <T> TupleView<T>._makeView(view: _GraphValue<TupleView<T>>, inputs: _ViewInputs): _ViewOutputs = error("Not Implemented")
internal fun <T> TupleView<T>._makeViewList(view: _GraphValue<TupleView<T>>, inputs: _ViewListInputs): _ViewListOutputs = error("Not Implemented")