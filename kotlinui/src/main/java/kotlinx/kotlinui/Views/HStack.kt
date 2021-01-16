package kotlinx.kotlinui

class HStack<Content : View>(
    alignment: VerticalAlignment = VerticalAlignment.center,
    spacing: Float? = null,
    content: ViewBuilder.() -> Content
) : View {
    var _tree: _VariadicView_Tree<_HStackLayout, Content> =
        _VariadicView_Tree(_HStackLayout(alignment, spacing), content(ViewBuilder))

    override val body: Never
        get() = error("Never")
}

//internal fun <Content : View> HStack<Content>._makeView(view: _GraphValue<HStack<Content>>, inputs: _ViewInputs): _ViewOutputs = error("Not Implemented")

class _HStackLayout(
    var alignment: VerticalAlignment? = VerticalAlignment.center,
    var spacing: Float? = null
) : _VariadicView_UnaryViewRoot
