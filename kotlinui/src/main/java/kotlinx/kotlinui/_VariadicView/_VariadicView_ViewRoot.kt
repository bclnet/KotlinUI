package kotlinx.kotlinui

import kotlin.system.exitProcess

interface _VariadicView_ViewRoot : _VariadicView_Root {
    fun body(children: _VariadicView_Children): View = exitProcess(0)
}

internal fun _VariadicView_ViewRoot._makeView(
    root: _GraphValue<_VariadicView_ViewRoot>, inputs: _ViewInputs,
    body: (_Graph, _ViewInputs) -> _ViewListOutputs
): _ViewOutputs = exitProcess(0)

internal fun _VariadicView_ViewRoot._makeViewList(
    root: _GraphValue<_VariadicView_ViewRoot>, inputs: _ViewListInputs,
    body: (_Graph, _ViewListInputs) -> _ViewListOutputs
): _ViewListOutputs = exitProcess(0)