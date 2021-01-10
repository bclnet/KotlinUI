package kotlinx.kotlinui

interface View {
    val body: View
}

internal fun View._makeView(view: _GraphValue<View>, inputs: _ViewInputs): _ViewOutputs = error("Not Implemented")
internal fun View._makeViewList(view: _GraphValue<View>, inputs: _ViewListInputs): _ViewListOutputs = error("Not Implemented")
