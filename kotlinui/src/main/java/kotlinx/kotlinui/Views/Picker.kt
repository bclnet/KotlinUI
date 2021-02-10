package kotlinx.kotlinui

import android.content.Context
import android.view.View as XView
import kotlinx.system.Tuple2

class Picker<Label : View, SelectionValue, Content : View>(
    val selection: Binding<SelectionValue>,
    val label: Label,
    content: ViewBuilder.() -> Content
) : View, ViewBuildable {
    val content: Content = content(ViewBuilder)

    //where Label == Text {
//    constructor(
//        titleKey: LocalizedStringKey,
//        selection: Binding<SelectionValue>,
//        content: () -> Content
//    ) : this(selection, Text(titleKey), content) {
//    }
//
//    constructor(title: String, selection: Binding<SelectionValue>, content: () -> Content) : this(
//        selection,
//        Text(titleKey),
//        content
//    ) {
//    }

    //: ViewBuildable
    override fun buildView(context: Context?): XView =
        error("Not Implemented")

    override val body: View
        get() = HStack { TupleView(Tuple2(label, content)) }
}

fun <S : PickerStyle> View.pickerStyle(style: S): View =
    modifier(PickerStyleWriter(style))

//internal fun <SelectionValue> PickerStyle._makeView(value: _GraphValue<_PickerValue<PickerStyle, SelectionValue>>, inputs: _ViewInputs): _ViewOutputs = error("Not Implemented")
//internal fun <SelectionValue> PickerStyle._makeViewList(value: _GraphValue<_PickerValue<PickerStyle, SelectionValue>>, inputs: _ViewListInputs): _ViewListOutputs = error("Not Implemented")

