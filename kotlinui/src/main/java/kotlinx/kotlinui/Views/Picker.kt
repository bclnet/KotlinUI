package kotlinx.kotlinui

import kotlin.system.exitProcess

interface PickerStyle

fun <SelectionValue> PickerStyle._makeView(
    value: _GraphValue<_PickerValue<PickerStyle, SelectionValue>>,
    inputs: _ViewInputs
): _ViewOutputs = exitProcess(0)

fun <SelectionValue> PickerStyle._makeViewList(
    value: _GraphValue<_PickerValue<PickerStyle, SelectionValue>>,
    inputs: _ViewListInputs
): _ViewListOutputs = exitProcess(0)

class _PickerValue<Style : PickerStyle, SelectionValue>

fun <S : PickerStyle> View.pickerStyle(style: S): View = modifier(PickerStyleWriter(style))

object SegmentedPickerStyle : PickerStyle

fun <SelectionValue> SegmentedPickerStyle._makeView(
    value: _GraphValue<_PickerValue<SegmentedPickerStyle, SelectionValue>>,
    inputs: _ViewInputs
): _ViewOutputs = exitProcess(0)

fun <SelectionValue> SegmentedPickerStyle._makeViewList(
    value: _GraphValue<_PickerValue<SegmentedPickerStyle, SelectionValue>>,
    inputs: _ViewListInputs
): _ViewListOutputs = exitProcess(0)

object DefaultPickerStyle : PickerStyle

fun <SelectionValue> DefaultPickerStyle._makeView(
    value: _GraphValue<_PickerValue<DefaultPickerStyle, SelectionValue>>,
    inputs: _ViewInputs
): _ViewOutputs = exitProcess(0)

fun <SelectionValue> DefaultPickerStyle._makeViewList(
    value: _GraphValue<_PickerValue<DefaultPickerStyle, SelectionValue>>,
    inputs: _ViewListInputs
): _ViewListOutputs = exitProcess(0)

object PopUpButtonPickerStyle : PickerStyle

fun <SelectionValue> PopUpButtonPickerStyle._makeView(
    value: _GraphValue<_PickerValue<PopUpButtonPickerStyle, SelectionValue>>,
    inputs: _ViewInputs
): _ViewOutputs = exitProcess(0)

fun <SelectionValue> PopUpButtonPickerStyle._makeViewList(
    value: _GraphValue<_PickerValue<PopUpButtonPickerStyle, SelectionValue>>,
    inputs: _ViewListInputs
): _ViewListOutputs = exitProcess(0)

class Picker<Label : View, SelectionValue, Content : View>(
    var selection: Binding<SelectionValue>,
    var label: Label,
    content: () -> Content
) : View {
    var content: Content = content()

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

    override val body: View =
        HStack {
            label
            this.content
        }
}

class PickerStyleWriter<Style>(var style: Style) : ViewModifier
