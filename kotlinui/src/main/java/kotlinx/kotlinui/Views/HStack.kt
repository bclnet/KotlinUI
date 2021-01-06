package kotlinx.kotlinui

import kotlin.system.exitProcess

class HStack<Content : View>(
    content: () -> Content,
    alignment: VerticalAlignment = VerticalAlignment.center,
    spacing: Float? = null
) : View {
    var _tree: _VariadicView_Tree<_HStackLayout, Content> =
        _VariadicView_Tree(_HStackLayout(alignment, spacing), content())

    override var body: View = exitProcess(0)
}

fun <Content : View> HStack<Content>._makeView(
    view: _GraphValue<HStack<Content>>,
    inputs: _ViewInputs
): _ViewOutputs = exitProcess(0)
