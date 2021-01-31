package kotlinx.kotlinui

data class ModifiedContent<Content, Modifier : ViewModifier>(
    val content: Content,
    val modifier: Modifier
) : View {
    override val body: Never
        get() = error("Never")
}

fun <T : ViewModifier> View.modifier(modifier: T): ModifiedContent<View, T> =
    ModifiedContent<View, T>(this, modifier)

//internal fun <Content, Modifier : ViewModifier> ModifiedContent<Content, Modifier>._makeView(view: _GraphValue<ModifiedContent<Content, Modifier>>, inputs: _ViewInputs): _ViewOutputs = error("Not Implemented")
//internal fun <Content, Modifier : ViewModifier> ModifiedContent<Content, Modifier>._makeViewList(view: _GraphValue<ModifiedContent<Content, Modifier>>, inputs: _ViewListInputs): _ViewListOutputs = error("Not Implemented")

