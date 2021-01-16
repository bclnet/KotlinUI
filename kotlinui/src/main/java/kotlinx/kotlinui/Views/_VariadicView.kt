package kotlinx.kotlinui

class _VariadicView_Tree<Root : _VariadicView_Root, Content : View>(
    var root: Root,
    var content: Content
) : View {
    constructor(root: Root, content: ViewBuilder.() -> Content) : this(root, content(ViewBuilder))

    override val body: View
        get() = error("Never")
}

class _VariadicView_Children : View {
    override val body: View
        get() = error("Never")
}

interface _VariadicView_Root

interface _VariadicView_ViewRoot : _VariadicView_Root {
    fun body(children: _VariadicView_Children): View = error("Never")
}

interface _VariadicView_UnaryViewRoot : _VariadicView_ViewRoot

interface _VariadicView_MultiViewRoot : _VariadicView_ViewRoot

//internal fun _VariadicView_Children._makeViewList(view: _GraphValue<_VariadicView_Children>, inputs: _ViewListInputs): _ViewListOutputs = error("Not Implemented")
//internal fun <Root : _VariadicView_Root, Content : View> _VariadicView_Tree<Root, Content>._makeView(view: _GraphValue<_VariadicView_Tree<Root, Content>>, inputs: _ViewInputs): _ViewOutputs = error("Not Implemented")
//internal fun <Root : _VariadicView_Root, Content : View> _VariadicView_Tree<Root, Content>._makeViewList(view: _GraphValue<_VariadicView_Tree<Root, Content>>, inputs: _ViewListInputs): _ViewListOutputs = error("Not Implemented")
//internal fun _VariadicView_Root._viewListOptions(): Int = error("Not Implemented")
//internal fun _VariadicView_ViewRoot._makeView(root: _GraphValue<_VariadicView_ViewRoot>, inputs: _ViewInputs, body: (_Graph, _ViewInputs) -> _ViewListOutputs): _ViewOutputs = error("Not Implemented")
//internal fun _VariadicView_ViewRoot._makeViewList(root: _GraphValue<_VariadicView_ViewRoot>, inputs: _ViewListInputs, body: (_Graph, _ViewListInputs) -> _ViewListOutputs): _ViewListOutputs = error("Not Implemented")
//internal fun _VariadicView_UnaryViewRoot._makeViewList(root: _GraphValue<_VariadicView_UnaryViewRoot>, inputs: _ViewListInputs, body: (_Graph, _ViewListInputs) -> _ViewListOutputs): _ViewListOutputs = error("Not Implemented")
//internal fun _VariadicView_MultiViewRoot._makeView(root: _GraphValue<_VariadicView_MultiViewRoot>, inputs: _ViewInputs, body: (_Graph, _ViewInputs) -> _ViewListOutputs): _ViewOutputs = error("Not Implemented")