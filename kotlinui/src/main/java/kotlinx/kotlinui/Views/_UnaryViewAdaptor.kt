package kotlinx.kotlinui

class _UnaryViewAdaptor<Content : View>(var content: Content) : View {
    override val body: View
        get() = error("Never")
}

internal fun <Content : View> _UnaryViewAdaptor<Content>._makeView(view: _GraphValue<_UnaryViewAdaptor<Content>>, inputs: _ViewInputs): _ViewOutputs = error("Not Implemented")
