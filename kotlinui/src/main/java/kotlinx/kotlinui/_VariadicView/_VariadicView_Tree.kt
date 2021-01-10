package kotlinx.kotlinui

import kotlin.system.exitProcess

class _VariadicView_Tree<Root : _VariadicView_Root, Content : View>(
    var root: Root,
    var content: Content
) : View {
    constructor(root: Root, content: () -> Content) : this(root, content())

    override var body: View = exitProcess(0)
}

internal fun <Root : _VariadicView_Root, Content : View> _VariadicView_Tree<Root, Content>._makeView(
    view: _GraphValue<_VariadicView_Tree<Root, Content>>,
    inputs: _ViewInputs
): _ViewOutputs = exitProcess(0)

internal fun <Root : _VariadicView_Root, Content : View> _VariadicView_Tree<Root, Content>._makeViewList(
    view: _GraphValue<_VariadicView_Tree<Root, Content>>,
    inputs: _ViewListInputs
): _ViewListOutputs = exitProcess(0)
