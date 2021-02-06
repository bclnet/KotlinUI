package kotlinx.kotlinui

import kotlinx.ptype.PType

data class Button<Label : View> private constructor(
    val action: () -> Unit,
    val _label: Label
) : View {
    constructor(action: () -> Unit, label: ViewBuilder.() -> Label) : this(action, label(ViewBuilder()))

    //where Label == Text
    //constructor(title: String, action: () -> Unit) : this(action, Text(title))

    override val body: Label
        get() = _label

    companion object {
        //: Register
        fun register() {
            PType.register<Button<AnyView>>()
        }
    }
}

fun <S : ButtonStyle> View.buttonStyle(style: S): View {
    val label = ButtonStyleConfiguration.Label(this)
    val configuration = ButtonStyleConfiguration(label, false)
    return style.makeBody(configuration)
}