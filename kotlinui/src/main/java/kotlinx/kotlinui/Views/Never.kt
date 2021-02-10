package kotlinx.kotlinui

import android.content.Context
import android.view.View as XView

class Never : View, ViewBuildable {
    override val body: Never
        get() = error("Never")

    //: ViewBuildable
    override fun buildView(context: Context?): XView =
        error("Never")
}
