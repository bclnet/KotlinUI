package kotlinx.kotlinui

import kotlinx.ptype.PType
import kotlinx.system.*

class TupleView<T>(
    var value: T
) : View {
    override val body: View
        get() = error("Never")

    companion object {
        //: Register
        fun register() {
            PType.register<TupleView<AnyView>>()
            PType.register<TupleView<Tuple2<AnyView, AnyView>>>()
            PType.register<TupleView<Tuple3<AnyView, AnyView, AnyView>>>()
            PType.register<TupleView<Tuple4<AnyView, AnyView, AnyView, AnyView>>>()
            PType.register<TupleView<Tuple5<AnyView, AnyView, AnyView, AnyView, AnyView>>>()
            PType.register<TupleView<Tuple6<AnyView, AnyView, AnyView, AnyView, AnyView, AnyView>>>()
            PType.register<TupleView<Tuple7<AnyView, AnyView, AnyView, AnyView, AnyView, AnyView, AnyView>>>()
            PType.register<TupleView<Tuple8<AnyView, AnyView, AnyView, AnyView, AnyView, AnyView, AnyView, AnyView>>>()
            PType.register<TupleView<Tuple9<AnyView, AnyView, AnyView, AnyView, AnyView, AnyView, AnyView, AnyView, AnyView>>>()
            PType.register<TupleView<TupleA<AnyView, AnyView, AnyView, AnyView, AnyView, AnyView, AnyView, AnyView, AnyView, AnyView>>>()
        }
    }
}

//internal fun <T> TupleView<T>._makeView(view: _GraphValue<TupleView<T>>, inputs: _ViewInputs): _ViewOutputs = error("Not Implemented")
//internal fun <T> TupleView<T>._makeViewList(view: _GraphValue<TupleView<T>>, inputs: _ViewListInputs): _ViewListOutputs = error("Not Implemented")