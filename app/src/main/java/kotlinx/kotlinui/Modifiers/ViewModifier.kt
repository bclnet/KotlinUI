package kotlinx.kotlinui

import kotlin.system.exitProcess

interface ViewModifier {
    fun body(content: _ViewModifier_Content<ViewModifier>): View = exitProcess(0)

    fun <T : ViewModifier> concat(modifier: T): ModifiedContent<ViewModifier, T> =
        ModifiedContent(this, modifier)
}

fun ViewModifier._makeView(
    modifier: _GraphValue<ViewModifier>, inputs: _ViewInputs,
    body: (_Graph, _ViewInputs) -> _ViewOutputs
): _ViewOutputs = exitProcess(0)

fun ViewModifier._makeViewList(
    modifier: _GraphValue<ViewModifier>, inputs: _ViewListInputs,
    body: (_Graph, _ViewListInputs) -> _ViewListOutputs
): _ViewListOutputs = exitProcess(0)