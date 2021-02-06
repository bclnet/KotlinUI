package kotlinx.kotlinui

import kotlinx.system.*

// https://kotlinlang.org/docs/reference/type-safe-builders.html
class ViewBuilder {
    private val children = arrayListOf<View>()

    operator fun View.unaryPlus() {
        children.add(this)
    }

    fun <Content : View> build(): Content {
        error("")
    }

    // GET
    fun get(): EmptyView = EmptyView()

    operator fun <Content : View> get(content: Content): Content =
        content

    operator fun <C0 : View, C1 : View> get(c0: C0, c1: C1): TupleView<Tuple2<C0, C1>> =
        TupleView(Tuple2(c0, c1))

    operator fun <C0 : View, C1 : View, C2 : View> get(
        c0: C0, c1: C1, c2: C2
    ): TupleView<Tuple3<C0, C1, C2>> =
        TupleView(Tuple3(c0, c1, c2))

    operator fun <C0 : View, C1 : View, C2 : View, C3 : View> get(
        c0: C0, c1: C1, c2: C2, c3: C3
    ): TupleView<Tuple4<C0, C1, C2, C3>> =
        TupleView(Tuple4(c0, c1, c2, c3))

    operator fun <C0 : View, C1 : View, C2 : View, C3 : View, C4 : View> get(
        c0: C0, c1: C1, c2: C2, c3: C3, c4: C4
    ): TupleView<Tuple5<C0, C1, C2, C3, C4>> =
        TupleView(Tuple5(c0, c1, c2, c3, c4))

    operator fun <C0 : View, C1 : View, C2 : View, C3 : View, C4 : View, C5 : View> get(
        c0: C0, c1: C1, c2: C2, c3: C3, c4: C4, c5: C5
    ): TupleView<Tuple6<C0, C1, C2, C3, C4, C5>> =
        TupleView(Tuple6(c0, c1, c2, c3, c4, c5))

    operator fun <C0 : View, C1 : View, C2 : View, C3 : View, C4 : View, C5 : View, C6 : View> get(
        c0: C0, c1: C1, c2: C2, c3: C3, c4: C4, c5: C5, c6: C6
    ): TupleView<Tuple7<C0, C1, C2, C3, C4, C5, C6>> =
        TupleView(Tuple7(c0, c1, c2, c3, c4, c5, c6))

    operator fun <C0 : View, C1 : View, C2 : View, C3 : View, C4 : View, C5 : View, C6 : View, C7 : View> get(
        c0: C0, c1: C1, c2: C2, c3: C3, c4: C4, c5: C5, c6: C6, c7: C7
    ): TupleView<Tuple8<C0, C1, C2, C3, C4, C5, C6, C7>> =
        TupleView(Tuple8(c0, c1, c2, c3, c4, c5, c6, c7))

    operator fun <C0 : View, C1 : View, C2 : View, C3 : View, C4 : View, C5 : View, C6 : View, C7 : View, C8 : View> get(
        c0: C0, c1: C1, c2: C2, c3: C3, c4: C4, c5: C5, c6: C6, c7: C7, c8: C8
    ): TupleView<Tuple9<C0, C1, C2, C3, C4, C5, C6, C7, C8>> =
        TupleView(Tuple9(c0, c1, c2, c3, c4, c5, c6, c7, c8))

    operator fun <C0 : View, C1 : View, C2 : View, C3 : View, C4 : View, C5 : View, C6 : View, C7 : View, C8 : View, C9 : View> get(
        c0: C0, c1: C1, c2: C2, c3: C3, c4: C4, c5: C5, c6: C6, c7: C7, c8: C8, c9: C9
    ): TupleView<TupleA<C0, C1, C2, C3, C4, C5, C6, C7, C8, C9>> =
        TupleView(TupleA(c0, c1, c2, c3, c4, c5, c6, c7, c8, c9))


    // IF
    //    fun <Content : View> buildIf(content: Content): Content = content

    fun <TrueContent : View, FalseContent : View> buildIfTrue(first: TrueContent): _ConditionalContent<TrueContent, FalseContent> {
        val storage = _ConditionalContent.Storage.trueContent(first)
        return _ConditionalContent(storage)
    }

    fun <TrueContent : View, FalseContent : View> buildIfFalse(second: FalseContent): _ConditionalContent<TrueContent, FalseContent> {
        val storage = _ConditionalContent.Storage.falseContent(second)
        return _ConditionalContent(storage)
    }
}
