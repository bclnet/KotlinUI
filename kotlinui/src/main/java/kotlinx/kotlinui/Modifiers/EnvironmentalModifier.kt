package kotlinx.kotlinui

import kotlin.system.exitProcess

interface EnvironmentalModifier : ViewModifier {
    fun resolve(values: EnvironmentValues): ViewModifier
}

fun EnvironmentalModifier._makeView(
    modifier: _GraphValue<EnvironmentalModifier>, inputs: _ViewInputs,
    body: (_Graph, _ViewInputs) -> _ViewOutputs
): _ViewOutputs = exitProcess(0)

fun EnvironmentalModifier._makeViewList(
    modifier: _GraphValue<EnvironmentalModifier>, inputs: _ViewListInputs,
    body: (_Graph, _ViewListInputs) -> _ViewListOutputs
): _ViewListOutputs = exitProcess(0)