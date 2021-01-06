package kotlinx.kotlinui

import kotlin.system.exitProcess

class ZStack<Content : View>(
    content: () -> Content,
    alignment: Alignment = Alignment.center
) : View {
    var _tree: _VariadicView_Tree<_ZStackLayout, Content> =
        _VariadicView_Tree(_ZStackLayout(alignment), content())

    override var body: View = exitProcess(0)
}

fun <Content : View> ZStack<Content>._makeView(
    view: _GraphValue<ZStack<Content>>,
    inputs: _ViewInputs
): _ViewOutputs = exitProcess(0)