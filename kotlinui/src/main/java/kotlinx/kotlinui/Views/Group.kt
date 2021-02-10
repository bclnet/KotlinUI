package kotlinx.kotlinui

import android.view.View as XView

class Group<Content : View>(
    content: ViewBuilder.() -> Content
) : View {
    val _content: Content = content(ViewBuilder)

    override val body: Never
        get() = error("Never")
}

//internal fun <Content : View> Group<Content>._makeViewList(view: _GraphValue<Group<Content>>, inputs: _ViewListInputs): _ViewListOutputs = error("Not Implemented")