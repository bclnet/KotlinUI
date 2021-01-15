package kotlinx.kotlinui

import kotlinx.kotlinuijson.DynaType
import kotlinx.system.*

class TupleView<T>(
    var value: T
) : View {
    override val body: View
        get() = error("Never")

    companion object {
        fun register() {
            DynaType.register<TupleView<AnyView>>()
            DynaType.register<TupleView<Tuple2<AnyView, AnyView>>>()
            DynaType.register<TupleView<Tuple3<AnyView, AnyView, AnyView>>>()
            DynaType.register<TupleView<Tuple4<AnyView, AnyView, AnyView, AnyView>>>()
            DynaType.register<TupleView<Tuple5<AnyView, AnyView, AnyView, AnyView, AnyView>>>()
            DynaType.register<TupleView<Tuple6<AnyView, AnyView, AnyView, AnyView, AnyView, AnyView>>>()
            DynaType.register<TupleView<Tuple7<AnyView, AnyView, AnyView, AnyView, AnyView, AnyView, AnyView>>>()
            DynaType.register<TupleView<Tuple8<AnyView, AnyView, AnyView, AnyView, AnyView, AnyView, AnyView, AnyView>>>()
            DynaType.register<TupleView<Tuple9<AnyView, AnyView, AnyView, AnyView, AnyView, AnyView, AnyView, AnyView, AnyView>>>()
            DynaType.register<TupleView<TupleA<AnyView, AnyView, AnyView, AnyView, AnyView, AnyView, AnyView, AnyView, AnyView, AnyView>>>()
        }
    }
}

internal fun <T> TupleView<T>._makeView(view: _GraphValue<TupleView<T>>, inputs: _ViewInputs): _ViewOutputs = error("Not Implemented")
internal fun <T> TupleView<T>._makeViewList(view: _GraphValue<TupleView<T>>, inputs: _ViewListInputs): _ViewListOutputs = error("Not Implemented")