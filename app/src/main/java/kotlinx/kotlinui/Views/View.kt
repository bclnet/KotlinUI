package kotlinx.kotlinui

import kotlin.system.exitProcess

interface View {
    val body: View
}

fun View._makeView(view: _GraphValue<View>, inputs: _ViewInputs): _ViewOutputs {
    exitProcess(0)
}

fun View._makeViewList(view: _GraphValue<View>, inputs: _ViewListInputs): _ViewListOutputs {
    exitProcess(0)
}