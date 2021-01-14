package kotlinx.kotlinui

import kotlinx.system.KTypeBase1

class Button<Label : View> private constructor(var _action: () -> Unit, var _label: Label) : KTypeBase1<Label>(), View {
    constructor(action: () -> Unit, label: () -> Label) : this(action, label())

    //where Label == Text
    //constructor(title: String, action: () -> Unit) : this(action, Text(title))

    override val body: View
        get() = _label
}

interface ButtonStyle {
    fun makeBody(configuration: ButtonStyleConfiguration): View
}

class ButtonStyleConfiguration(var label: Label, var isPressed: Boolean) {
    class Label(var storage: Any) : View {
        override var body: View = error("Never")
    }
}

fun <S : ButtonStyle> View.buttonStyle(style: S): View {
    val label = ButtonStyleConfiguration.Label(this)
    val configuration = ButtonStyleConfiguration(label, false)
    return style.makeBody(configuration)
}