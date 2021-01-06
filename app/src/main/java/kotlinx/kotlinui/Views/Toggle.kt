package kotlinx.kotlinui

import kotlin.system.exitProcess

class Toggle<Label : View>(isOn: Binding<Boolean>, label: () -> Label) : View {
    var __isOn: Binding<Boolean> = isOn
    var _label: Label = label()

    // where Label == ToggleStyleConfiguration.Label
    //public Constructor(configuration: ToggleStyleConfiguration) : this() {
    //    this.__isOn = configuration.__isOn;
    //    this._label = configuration.label;
    //}
    // where Label == Text
//    constructor(titleKey: LocalizedStringKey?, isOn: Binding<Boolean?>?) {
//        System.exit(0)
//    }
//    // where Label == Text
//    constructor(title: String?, isOn: Binding<Boolean?>?) {
//        System.exit(0)
//    }

    override var body: View = _label
}

interface ToggleStyle {
    fun makeBody(configuration: ToggleStyleConfiguration): View
}

class ToggleStyleConfiguration {
    class Label : View {
        override var body: View = exitProcess(0)
    }

    var label: Label = Label()
    var isOn: Boolean = false
        get() = field
        set(newValue) {
            field = newValue
        }

    var __isOn: Binding<Boolean> = Binding({ isOn }, { newValue -> isOn = newValue });
}

fun <S : ToggleStyle> View.toggleStyle(style: S): View = modifier(ToggleStyleModifier(style))

class CheckboxToggleStyle : ToggleStyle {
    override fun makeBody(configuration: ToggleStyleConfiguration): View = configuration.label
}

class ToggleStyleModifier<Style>(var style: Style) : ViewModifier
