package kotlinx.kotlinui

interface ShapeStyle {
    fun makeView(): Shape
}

// val ShapeStyle.body: Never
// get() = error("Never)

//internal fun <S : Shape> ShapeStyle._makeView(view: _GraphValue<_ShapeView<S, ShapeStyle>>, inputs: _ViewInputs): _ViewOutputs = error("Not Implemented")