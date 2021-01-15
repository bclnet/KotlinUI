package kotlinx.kotlinui

interface PickerStyle

internal fun <SelectionValue> PickerStyle._makeView(value: _GraphValue<_PickerValue<PickerStyle, SelectionValue>>, inputs: _ViewInputs): _ViewOutputs = error("Not Implemented")
internal fun <SelectionValue> PickerStyle._makeViewList(value: _GraphValue<_PickerValue<PickerStyle, SelectionValue>>, inputs: _ViewListInputs): _ViewListOutputs = error("Not Implemented")

class _PickerValue<Style : PickerStyle, SelectionValue>

fun <S : PickerStyle> View.pickerStyle(style: S): View = modifier(PickerStyleWriter(style))

object SegmentedPickerStyle : PickerStyle

internal fun <SelectionValue> SegmentedPickerStyle._makeView(value: _GraphValue<_PickerValue<SegmentedPickerStyle, SelectionValue>>, inputs: _ViewInputs): _ViewOutputs = error("Not Implemented")
internal fun <SelectionValue> SegmentedPickerStyle._makeViewList(value: _GraphValue<_PickerValue<SegmentedPickerStyle, SelectionValue>>, inputs: _ViewListInputs): _ViewListOutputs = error("Not Implemented")

object DefaultPickerStyle : PickerStyle

internal fun <SelectionValue> DefaultPickerStyle._makeView(value: _GraphValue<_PickerValue<DefaultPickerStyle, SelectionValue>>, inputs: _ViewInputs): _ViewOutputs = error("Not Implemented")
internal fun <SelectionValue> DefaultPickerStyle._makeViewList(value: _GraphValue<_PickerValue<DefaultPickerStyle, SelectionValue>>, inputs: _ViewListInputs): _ViewListOutputs = error("Not Implemented")

object PopUpButtonPickerStyle : PickerStyle

internal fun <SelectionValue> PopUpButtonPickerStyle._makeView(value: _GraphValue<_PickerValue<PopUpButtonPickerStyle, SelectionValue>>, inputs: _ViewInputs): _ViewOutputs = error("Not Implemented")
internal fun <SelectionValue> PopUpButtonPickerStyle._makeViewList(value: _GraphValue<_PickerValue<PopUpButtonPickerStyle, SelectionValue>>, inputs: _ViewListInputs): _ViewListOutputs = error("Not Implemented")

class Picker<Label : View, SelectionValue, Content : View>(
    val selection: Binding<SelectionValue>,
    val label: Label,
    content: ViewBuilder.() -> Content
) : View {
    val content: Content = content(ViewBuilder())

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

    override val body: View
        get() = HStack {
            get(
                label,
                this@Picker.content
            )
        }
}

class PickerStyleWriter<Style>(var style: Style) : ViewModifier
