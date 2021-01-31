package kotlinx.kotlinui

data class _UnaryViewAdaptor<Content : View>(
    val content: Content
) : View {
    override val body: Never
        get() = error("Never")
}

//internal fun <Content : View> _UnaryViewAdaptor<Content>._makeView(view: _GraphValue<_UnaryViewAdaptor<Content>>, inputs: _ViewInputs): _ViewOutputs = error("Not Implemented")
