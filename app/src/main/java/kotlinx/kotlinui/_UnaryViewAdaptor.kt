package kotlinx.kotlinui

import kotlin.system.exitProcess

class _UnaryViewAdaptor<Content : View>(var content: Content) : View {
    override var body: View = exitProcess(0)
}

fun <Content : View> _UnaryViewAdaptor<Content>._makeView(
        view: _GraphValue<_UnaryViewAdaptor<Content>>,
        inputs: _ViewInputs
): _ViewOutputs =
        exitProcess(0)
