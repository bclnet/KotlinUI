package kotlinx.kotlinui

interface EnvironmentalModifier : ViewModifier {
    fun resolve(values: EnvironmentValues): ViewModifier
}

internal fun EnvironmentalModifier._makeView(modifier: _GraphValue<EnvironmentalModifier>, inputs: _ViewInputs, body: (_Graph, _ViewInputs) -> _ViewOutputs): _ViewOutputs = error("Not Implemented")
internal fun EnvironmentalModifier._makeViewList(modifier: _GraphValue<EnvironmentalModifier>, inputs: _ViewListInputs, body: (_Graph, _ViewListInputs) -> _ViewListOutputs): _ViewListOutputs = error("Not Implemented")