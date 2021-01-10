package kotlinx.kotlinui

import kotlin.system.exitProcess

class ModifiedContent<Content, Modifier : ViewModifier>(
    var content: Content,
    var modifier: Modifier
) : View {
    override var body: View = exitProcess(0)
}

fun <T : ViewModifier> View.modifier(modifier: T): ModifiedContent<View, T> =
    ModifiedContent<View, T>(this, modifier)

internal fun <Content, Modifier : ViewModifier> ModifiedContent<Content, Modifier>._makeView(
    view: _GraphValue<ModifiedContent<Content, Modifier>>,
    inputs: _ViewInputs
): _ViewOutputs = exitProcess(0)

internal fun <Content, Modifier : ViewModifier> ModifiedContent<Content, Modifier>._makeViewList(
    view: _GraphValue<ModifiedContent<Content, Modifier>>,
    inputs: _ViewListInputs
): _ViewListOutputs = exitProcess(0)

