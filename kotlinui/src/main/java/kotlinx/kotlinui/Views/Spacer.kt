package kotlinx.kotlinui

import android.content.Context
import android.widget.Space
import android.view.View as XView
import kotlinx.ptype.PType

class Spacer : View, ViewBuildable {
    override val body: Never
        get() = error("Never")

    //: ViewBuildable
    override fun buildView(context: Context?): XView =
        Space(context)

    companion object {
        //: Register
        fun register() {
            PType.register<Spacer>()
        }
    }
}
