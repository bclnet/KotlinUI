package kotlinx.kotlinui

import kotlinx.system.KTypeBase1

class HStack<Content : View>(
    alignment: VerticalAlignment = VerticalAlignment.center,
    spacing: Float? = null,
    content: () -> Content
) : KTypeBase1<Content>(), View {
    var _tree: _VariadicView_Tree<_HStackLayout, Content> =
        _VariadicView_Tree(_HStackLayout(alignment, spacing), content())

    override val body: View
        get() = error("Never")
}

internal fun <Content : View> HStack<Content>._makeView(view: _GraphValue<HStack<Content>>, inputs: _ViewInputs): _ViewOutputs = error("Not Implemented")

class _HStackLayout(
    var alignment: VerticalAlignment? = VerticalAlignment.center,
    var spacing: Float? = null
) : _VariadicView_UnaryViewRoot
