package kotlinx.kotlinui

import kotlinx.system.KTypeBase1

class Optional<Wrapped : View> : KTypeBase1<Wrapped>(), View {
    override val body: View
        get() = error("Never")
}

internal fun <Wrapped : View> Optional<Wrapped>._makeView(view: _GraphValue<Optional<Wrapped>>, inputs: _ViewInputs): _ViewOutputs = error("Not Implemented")
internal fun <Wrapped : View> Optional<Wrapped>._makeViewList(view: _GraphValue<Optional<Wrapped>>, inputs: _ViewListInputs): _ViewListOutputs = error("Not Implemented")