package kotlinx.kotlinui

class _ShapeView<Content : Shape, Style : ShapeStyle>(
    val shape: Content,
    val style: Style,
    val fillStyle: FillStyle = FillStyle()
) : View {
    override val body: Never
        get() = error("Never")
}

//internal fun <Content : Shape, Style : ShapeStyle> _ShapeView<Content, Style>._makeView(view: _GraphValue<_ShapeView<Content, Style>>, inputs: _ViewInputs): _ViewOutputs = error("Not Implemented")