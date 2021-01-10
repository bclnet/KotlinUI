package kotlinx.kotlinui

import kotlin.system.exitProcess

interface _VariadicView_MultiViewRoot : _VariadicView_ViewRoot

internal fun _VariadicView_MultiViewRoot._makeView(
    root: _GraphValue<_VariadicView_MultiViewRoot>, inputs: _ViewInputs,
    body: (_Graph, _ViewInputs) -> _ViewListOutputs
): _ViewOutputs = exitProcess(0)