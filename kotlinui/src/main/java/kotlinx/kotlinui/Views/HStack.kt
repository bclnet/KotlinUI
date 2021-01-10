package kotlinx.kotlinui

import kotlin.system.exitProcess

class HStack<Content : View>(
    alignment: VerticalAlignment = VerticalAlignment.center,
    spacing: Float? = null,
    content: () -> Content
) : View {
    var _tree: _VariadicView_Tree<_HStackLayout, Content> =
        _VariadicView_Tree(_HStackLayout(alignment, spacing), content())

    override var body: View = exitProcess(0)
}

internal fun <Content : View> HStack<Content>._makeView(
    view: _GraphValue<HStack<Content>>,
    inputs: _ViewInputs
): _ViewOutputs = exitProcess(0)

class _HStackLayout(
    var alignment: VerticalAlignment? = VerticalAlignment.center,
    var spacing: Float? = null
) : _VariadicView_UnaryViewRoot
