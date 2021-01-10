package kotlinx.kotlinui

import kotlin.system.exitProcess

class _ShapeView<Content : Shape, Style : ShapeStyle>(
    var shape: Content,
    var style: Style,
    var fillStyle: FillStyle = FillStyle()
) : View {
    override var body: View = exitProcess(0)
}

internal fun <Content : Shape, Style : ShapeStyle> _ShapeView<Content, Style>._makeView(view: _GraphValue<_ShapeView<Content, Style>>, inputs: _ViewInputs): _ViewOutputs = exitProcess(0)