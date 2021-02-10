package kotlinx.kotlinui

import android.content.Context
import kotlinx.system.*
import android.view.View as XView

interface View {
    val body: View

    infix fun abc(other: View): View =
        if (this !is TupleView<*>) TupleView(Tuple2(this, other))
        else when (val v = this.value) {
            is Tuple2<*, *> -> TupleView(Tuple3(v.v0, v.v1, other))
            is Tuple3<*, *, *> -> TupleView(Tuple4(v.v0, v.v1, v.v2, other))
            is Tuple4<*, *, *, *> -> TupleView(Tuple5(v.v0, v.v1, v.v2, v.v3, other))
            is Tuple5<*, *, *, *, *> -> TupleView(Tuple6(v.v0, v.v1, v.v2, v.v3, v.v4, other))
            is Tuple6<*, *, *, *, *, *> -> TupleView(Tuple7(v.v0, v.v1, v.v2, v.v3, v.v4, v.v5, other))
            is Tuple7<*, *, *, *, *, *, *> -> TupleView(Tuple8(v.v0, v.v1, v.v2, v.v3, v.v4, v.v5, v.v6, other))
            is Tuple8<*, *, *, *, *, *, *, *> -> TupleView(Tuple9(v.v0, v.v1, v.v2, v.v3, v.v4, v.v5, v.v6, v.v7, other))
            is Tuple9<*, *, *, *, *, *, *, *, *> -> TupleView(TupleA(v.v0, v.v1, v.v2, v.v3, v.v4, v.v5, v.v6, v.v7, v.v7, other))
            else -> error("More than 10 not allowed")
        }
}

interface ViewBuildable {
    fun buildView(context: Context?): XView
}

val View.builder: ViewBuildable
    get() = when {
        this is ViewBuildable -> this
        this.body is ViewBuildable -> this.body as ViewBuildable
        else -> error("${this.body}: Not Builder")
    }

fun View.buildViews(context: Context?): Array<XView> =
    if (this !is TupleView<*>) arrayOf(this.builder.buildView(context))
    else when (val v = this.value) {
        is Tuple2<*, *> -> arrayOf(v.v0 as View, v.v1 as View)
        is Tuple3<*, *, *> -> arrayOf(v.v0 as View, v.v1 as View, v.v2 as View)
        is Tuple4<*, *, *, *> -> arrayOf(v.v0 as View, v.v1 as View, v.v2 as View, v.v3 as View)
        is Tuple5<*, *, *, *, *> -> arrayOf(v.v0 as View, v.v1 as View, v.v2 as View, v.v3 as View, v.v4 as View)
        is Tuple6<*, *, *, *, *, *> -> arrayOf(v.v0 as View, v.v1 as View, v.v2 as View, v.v3 as View, v.v4 as View, v.v5 as View)
        is Tuple7<*, *, *, *, *, *, *> -> arrayOf(v.v0 as View, v.v1 as View, v.v2 as View, v.v3 as View, v.v4 as View, v.v5 as View, v.v6 as View)
        is Tuple8<*, *, *, *, *, *, *, *> -> arrayOf(v.v0 as View, v.v1 as View, v.v2 as View, v.v3 as View, v.v4 as View, v.v5 as View, v.v6 as View, v.v7 as View)
        is Tuple9<*, *, *, *, *, *, *, *, *> -> arrayOf(v.v0 as View, v.v1 as View, v.v2 as View, v.v3 as View, v.v4 as View, v.v5 as View, v.v6 as View, v.v7 as View, v.v8 as View)
        is TupleA<*, *, *, *, *, *, *, *, *, *> -> arrayOf(v.v0 as View, v.v1 as View, v.v2 as View, v.v3 as View, v.v4 as View, v.v5 as View, v.v6 as View, v.v7 as View, v.v8 as View, v.v9 as View)
        else -> error("$v: More than 10 not allowed")
    }.map { it.builder.buildView(context) }.toTypedArray()

//internal fun View._makeView(view: _GraphValue<View>, inputs: _ViewInputs): _ViewOutputs = error("Not Implemented")
//internal fun View._makeViewList(view: _GraphValue<View>, inputs: _ViewListInputs): _ViewListOutputs = error("Not Implemented")
