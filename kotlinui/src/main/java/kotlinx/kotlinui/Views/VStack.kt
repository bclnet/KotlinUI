package kotlinx.kotlinui

import kotlin.system.exitProcess

class VStack<Content : View>(
    alignment: HorizontalAlignment = HorizontalAlignment.center,
    spacing: Float? = null,
    content: () -> Content
) : View {
    var _tree: _VariadicView_Tree<_VStackLayout, Content> =
        _VariadicView_Tree(_VStackLayout(alignment, spacing), content())

    override var body: View = exitProcess(0)
}

fun <Content : View> VStack<Content>._makeView(
    view: _GraphValue<VStack<Content>>,
    inputs: _ViewInputs
): _ViewOutputs = exitProcess(0)