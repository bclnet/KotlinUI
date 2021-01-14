package kotlinx.kotlinui

import kotlinx.system.*

// https://kotlinlang.org/docs/reference/lambdas.html
// Function literals with receiver
// https://kotlinlang.org/docs/reference/type-safe-builders.html

object ViewBuilder {
    fun buildBlock(): EmptyView = EmptyView()

    fun <Content : View> buildBlock(content: Content): Content = content

    fun <Content : View> buildIf(content: Content): Content = content

    fun <TrueContent : View, FalseContent : View> buildEitherTrue(first: TrueContent): _ConditionalContent<TrueContent, FalseContent> {
//        var storage = _ConditionalContent.Storage<TrueContent, FalseContent>(_ConditionalContent.StorageType.trueContent)
//        storage.trueContent = first
        val storage = _ConditionalContent.Storage.trueContent(first)
        return _ConditionalContent(storage)
    }

    fun <TrueContent : View, FalseContent : View> buildEitherFalse(second: FalseContent): _ConditionalContent<TrueContent, FalseContent> {
//        var storage = _ConditionalContent.Storage<TrueContent, FalseContent>(_ConditionalContent.StorageType.falseContent)
//        storage.falseContent = second
        val storage = _ConditionalContent.Storage.falseContent(second)
        return _ConditionalContent(storage)
    }

    fun <C0 : View, C1 : View> buildBlock(c0: C0, c1: C1): TupleView<Tuple2<C0, C1>> =
        TupleView(Tuple2(c0, c1))

    fun <C0 : View, C1 : View, C2 : View> buildBlock(
        c0: C0, c1: C1, c2: C2
    ): TupleView<Tuple3<C0, C1, C2>> = TupleView(Tuple3(c0, c1, c2))

    fun <C0 : View, C1 : View, C2 : View, C3 : View> buildBlock(
        c0: C0, c1: C1, c2: C2, c3: C3
    ): TupleView<Tuple4<C0, C1, C2, C3>> =
        TupleView(Tuple4(c0, c1, c2, c3))

    fun <C0 : View, C1 : View, C2 : View, C3 : View, C4 : View> buildBlock(
        c0: C0, c1: C1, c2: C2, c3: C3, c4: C4
    ): TupleView<Tuple5<C0, C1, C2, C3, C4>> =
        TupleView(Tuple5(c0, c1, c2, c3, c4))

    fun <C0 : View, C1 : View, C2 : View, C3 : View, C4 : View, C5 : View> buildBlock(
        c0: C0, c1: C1, c2: C2, c3: C3, c4: C4, c5: C5
    ): TupleView<Tuple6<C0, C1, C2, C3, C4, C5>> =
        TupleView(Tuple6(c0, c1, c2, c3, c4, c5))

    fun <C0 : View, C1 : View, C2 : View, C3 : View, C4 : View, C5 : View, C6 : View> buildBlock(
        c0: C0, c1: C1, c2: C2, c3: C3, c4: C4, c5: C5, c6: C6
    ): TupleView<Tuple7<C0, C1, C2, C3, C4, C5, C6>> =
        TupleView(Tuple7(c0, c1, c2, c3, c4, c5, c6))

    fun <C0 : View, C1 : View, C2 : View, C3 : View, C4 : View, C5 : View, C6 : View, C7 : View> buildBlock(
        c0: C0, c1: C1, c2: C2, c3: C3, c4: C4, c5: C5, c6: C6, c7: C7
    ): TupleView<Tuple8<C0, C1, C2, C3, C4, C5, C6, C7>> =
        TupleView(Tuple8(c0, c1, c2, c3, c4, c5, c6, c7))

    fun <C0 : View, C1 : View, C2 : View, C3 : View, C4 : View, C5 : View, C6 : View, C7 : View, C8 : View> buildBlock(
        c0: C0, c1: C1, c2: C2, c3: C3, c4: C4, c5: C5, c6: C6, c7: C7, c8: C8
    ): TupleView<Tuple9<C0, C1, C2, C3, C4, C5, C6, C7, C8>> =
        TupleView(Tuple9(c0, c1, c2, c3, c4, c5, c6, c7, c8))

    fun <C0 : View, C1 : View, C2 : View, C3 : View, C4 : View, C5 : View, C6 : View, C7 : View, C8 : View, C9 : View> buildBlock(
        c0: C0, c1: C1, c2: C2, c3: C3, c4: C4, c5: C5, c6: C6, c7: C7, c8: C8, c9: C9
    ): TupleView<TupleA<C0, C1, C2, C3, C4, C5, C6, C7, C8, C9>> =
        TupleView(TupleA(c0, c1, c2, c3, c4, c5, c6, c7, c8, c9))
}
