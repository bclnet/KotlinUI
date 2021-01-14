package kotlinx.kotlinui

import kotlinx.system.KTypeBase1

class Group<Content : View>(content: () -> Content) : KTypeBase1<Content>(), View {
    var _content: Content = content()

    override val body: View
        get() = error("Never")
}

internal fun <Content : View> Group<Content>._makeViewList(view: _GraphValue<Group<Content>>, inputs: _ViewListInputs): _ViewListOutputs = error("Not Implemented")