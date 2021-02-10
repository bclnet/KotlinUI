package kotlinx.kotlinui

import android.content.Context
import android.view.View as XView

class Optional<Wrapped : View> : View, ViewBuildable {
    override val body: Never
        get() = error("Never")

    //: ViewBuildable
    override fun buildView(context: Context?): XView =
        error("Not Implemented")
}

//internal fun <Wrapped : View> Optional<Wrapped>._makeView(view: _GraphValue<Optional<Wrapped>>, inputs: _ViewInputs): _ViewOutputs = error("Not Implemented")
//internal fun <Wrapped : View> Optional<Wrapped>._makeViewList(view: _GraphValue<Optional<Wrapped>>, inputs: _ViewListInputs): _ViewListOutputs = error("Not Implemented")