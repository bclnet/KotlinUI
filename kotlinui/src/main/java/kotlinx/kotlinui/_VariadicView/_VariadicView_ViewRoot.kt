package kotlinx.kotlinui

interface _VariadicView_ViewRoot : _VariadicView_Root {
    fun body(children: _VariadicView_Children): View = error("Never")
}

internal fun _VariadicView_ViewRoot._makeView(root: _GraphValue<_VariadicView_ViewRoot>, inputs: _ViewInputs, body: (_Graph, _ViewInputs) -> _ViewListOutputs): _ViewOutputs = error("Not Implemented")
internal fun _VariadicView_ViewRoot._makeViewList(root: _GraphValue<_VariadicView_ViewRoot>, inputs: _ViewListInputs, body: (_Graph, _ViewListInputs) -> _ViewListOutputs): _ViewListOutputs = error("Not Implemented")