package kotlinx.kotlinui

class _VariadicView_Tree<Root : _VariadicView_Root, Content : View>(
    var root: Root,
    var content: Content
) : View {
    constructor(root: Root, content: ViewBuilder.() -> Content) : this(root, content(ViewBuilder))

    override val body: View
        get() = error("Never")
}

internal fun <Root : _VariadicView_Root, Content : View> _VariadicView_Tree<Root, Content>._makeView(view: _GraphValue<_VariadicView_Tree<Root, Content>>, inputs: _ViewInputs): _ViewOutputs = error("Not Implemented")
internal fun <Root : _VariadicView_Root, Content : View> _VariadicView_Tree<Root, Content>._makeViewList(view: _GraphValue<_VariadicView_Tree<Root, Content>>, inputs: _ViewListInputs): _ViewListOutputs = error("Not Implemented")
