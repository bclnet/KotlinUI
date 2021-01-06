package kotlinx.kotlinui

import kotlin.system.exitProcess

class Group<Content : View>(content: () -> Content) : View {
    var _content: Content = content()

    override val body: View = exitProcess(0)
}

fun <Content : View> Group<Content>._makeViewList(
    view: _GraphValue<Group<Content>>,
    inputs: _ViewListInputs
): _ViewListOutputs = exitProcess(0)