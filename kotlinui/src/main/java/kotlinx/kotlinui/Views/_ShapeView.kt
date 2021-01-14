package kotlinx.kotlinui

import kotlinx.system.KTypeBase2

class _ShapeView<Content : Shape, Style : ShapeStyle>(
    var shape: Content,
    var style: Style,
    var fillStyle: FillStyle = FillStyle()
) : KTypeBase2<Content, Style>(), View {
    override val body: View
        get() = error("Never")
}

internal fun <Content : Shape, Style : ShapeStyle> _ShapeView<Content, Style>._makeView(view: _GraphValue<_ShapeView<Content, Style>>, inputs: _ViewInputs): _ViewOutputs = error("Not Implemented")