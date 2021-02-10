package kotlinx.kotlinui

interface ViewModifier {
    fun body(content: _ViewModifier_Content<ViewModifier>): View =
        error("Never")

    fun <T : ViewModifier> concat(modifier: T): ModifiedContent<ViewModifier, T> =
        ModifiedContent(this, modifier)
}

//internal fun ViewModifier._makeView(modifier: _GraphValue<ViewModifier>, inputs: _ViewInputs, body: (_Graph, _ViewInputs) -> _ViewOutputs): _ViewOutputs = error("Not Implemented")
//internal fun ViewModifier._makeViewList(modifier: _GraphValue<ViewModifier>, inputs: _ViewListInputs, body: (_Graph, _ViewListInputs) -> _ViewListOutputs): _ViewListOutputs = error("Not Implemented")