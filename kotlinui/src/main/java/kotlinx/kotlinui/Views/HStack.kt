package kotlinx.kotlinui

class HStack<Content : View>(
    alignment: VerticalAlignment = VerticalAlignment.center,
    spacing: Float? = null,
    content: ViewBuilder.() -> Content
) : View {
    val _tree: _VariadicView_Tree<_HStackLayout, Content> =
        _VariadicView_Tree(_HStackLayout(alignment, spacing), content(ViewBuilder))

    override val body: Never
        get() = error("Never")
}

//internal fun <Content : View> HStack<Content>._makeView(view: _GraphValue<HStack<Content>>, inputs: _ViewInputs): _ViewOutputs = error("Not Implemented")

data class _HStackLayout(
    val alignment: VerticalAlignment? = VerticalAlignment.center,
    val spacing: Float? = null
) : _VariadicView_UnaryViewRoot
