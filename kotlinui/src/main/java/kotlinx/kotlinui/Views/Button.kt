package kotlinx.kotlinui

class Button<Label : View> private constructor(
    val _action: () -> Unit,
    val _label: Label
) : View {
    constructor(action: () -> Unit, label: ViewBuilder.() -> Label) : this(action, label(ViewBuilder))

    //where Label == Text
    //constructor(title: String, action: () -> Unit) : this(action, Text(title))

    override val body: Label
        get() = _label
}

fun <S : ButtonStyle> View.buttonStyle(style: S): View {
    val label = ButtonStyleConfiguration.Label(this)
    val configuration = ButtonStyleConfiguration(label, false)
    return style.makeBody(configuration)
}