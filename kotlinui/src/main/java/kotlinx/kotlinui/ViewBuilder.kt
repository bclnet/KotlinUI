package kotlinx.kotlinui

import kotlinx.system.*

// https://kotlinlang.org/docs/reference/type-safe-builders.html
class ViewBuilder {
    private val views = arrayListOf<View>()

    operator fun View.unaryPlus() {
        views.add(this)
    }

    fun get(): View =
        when (views.size) {
            0 -> EmptyView()
            1 -> views[0]
            2 -> TupleView(Tuple2(views[0], views[1]))
            3 -> TupleView(Tuple3(views[0], views[1], views[2]))
            4 -> TupleView(Tuple4(views[0], views[1], views[2], views[3]))
            5 -> TupleView(Tuple5(views[0], views[1], views[2], views[3], views[4]))
            6 -> TupleView(Tuple6(views[0], views[1], views[2], views[3], views[4], views[5]))
            7 -> TupleView(Tuple7(views[0], views[1], views[2], views[3], views[4], views[5], views[6]))
            8 -> TupleView(Tuple8(views[0], views[1], views[2], views[3], views[4], views[5], views[6], views[7]))
            9 -> TupleView(Tuple9(views[0], views[1], views[2], views[3], views[4], views[5], views[6], views[7], views[8]))
            10 -> TupleView(TupleA(views[0], views[1], views[2], views[3], views[4], views[5], views[6], views[7], views[8], views[9]))
            else -> error("More than 10 not allowed")
        }

    // GET
    fun make(): EmptyView = EmptyView()

    fun <Content : View> make(content: Content): Content =
        content

    fun <C0 : View, C1 : View> make(c0: C0, c1: C1): TupleView<Tuple2<C0, C1>> =
        TupleView(Tuple2(c0, c1))

    fun <C0 : View, C1 : View, C2 : View> make(
        c0: C0, c1: C1, c2: C2
    ): TupleView<Tuple3<C0, C1, C2>> =
        TupleView(Tuple3(c0, c1, c2))

    fun <C0 : View, C1 : View, C2 : View, C3 : View> make(
        c0: C0, c1: C1, c2: C2, c3: C3
    ): TupleView<Tuple4<C0, C1, C2, C3>> =
        TupleView(Tuple4(c0, c1, c2, c3))

    fun <C0 : View, C1 : View, C2 : View, C3 : View, C4 : View> make(
        c0: C0, c1: C1, c2: C2, c3: C3, c4: C4
    ): TupleView<Tuple5<C0, C1, C2, C3, C4>> =
        TupleView(Tuple5(c0, c1, c2, c3, c4))

    fun <C0 : View, C1 : View, C2 : View, C3 : View, C4 : View, C5 : View> make(
        c0: C0, c1: C1, c2: C2, c3: C3, c4: C4, c5: C5
    ): TupleView<Tuple6<C0, C1, C2, C3, C4, C5>> =
        TupleView(Tuple6(c0, c1, c2, c3, c4, c5))

    fun <C0 : View, C1 : View, C2 : View, C3 : View, C4 : View, C5 : View, C6 : View> make(
        c0: C0, c1: C1, c2: C2, c3: C3, c4: C4, c5: C5, c6: C6
    ): TupleView<Tuple7<C0, C1, C2, C3, C4, C5, C6>> =
        TupleView(Tuple7(c0, c1, c2, c3, c4, c5, c6))

    fun <C0 : View, C1 : View, C2 : View, C3 : View, C4 : View, C5 : View, C6 : View, C7 : View> make(
        c0: C0, c1: C1, c2: C2, c3: C3, c4: C4, c5: C5, c6: C6, c7: C7
    ): TupleView<Tuple8<C0, C1, C2, C3, C4, C5, C6, C7>> =
        TupleView(Tuple8(c0, c1, c2, c3, c4, c5, c6, c7))

    fun <C0 : View, C1 : View, C2 : View, C3 : View, C4 : View, C5 : View, C6 : View, C7 : View, C8 : View> make(
        c0: C0, c1: C1, c2: C2, c3: C3, c4: C4, c5: C5, c6: C6, c7: C7, c8: C8
    ): TupleView<Tuple9<C0, C1, C2, C3, C4, C5, C6, C7, C8>> =
        TupleView(Tuple9(c0, c1, c2, c3, c4, c5, c6, c7, c8))

    fun <C0 : View, C1 : View, C2 : View, C3 : View, C4 : View, C5 : View, C6 : View, C7 : View, C8 : View, C9 : View> make(
        c0: C0, c1: C1, c2: C2, c3: C3, c4: C4, c5: C5, c6: C6, c7: C7, c8: C8, c9: C9
    ): TupleView<TupleA<C0, C1, C2, C3, C4, C5, C6, C7, C8, C9>> =
        TupleView(TupleA(c0, c1, c2, c3, c4, c5, c6, c7, c8, c9))

    // IF
    //    fun <Content : View> buildIf(content: Content): Content = content

    fun <TrueContent : View, FalseContent : View> ifTrue(first: TrueContent): _ConditionalContent<TrueContent, FalseContent> {
        val storage = _ConditionalContent.Storage.trueContent(first)
        return _ConditionalContent(storage)
    }

    fun <TrueContent : View, FalseContent : View> ifFalse(second: FalseContent): _ConditionalContent<TrueContent, FalseContent> {
        val storage = _ConditionalContent.Storage.falseContent(second)
        return _ConditionalContent(storage)
    }
}
