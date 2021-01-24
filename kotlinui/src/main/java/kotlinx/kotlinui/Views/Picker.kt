package kotlinx.kotlinui

class Picker<Label : View, SelectionValue, Content : View>(
    val selection: Binding<SelectionValue>,
    val label: Label,
    content: ViewBuilder.() -> Content
) : View {
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

    override val body: View
        get() = HStack {
            get(
                label,
                this@Picker.content
            )
        }
}

fun <S : PickerStyle> View.pickerStyle(style: S): View =
    modifier(PickerStyleWriter(style))

//internal fun <SelectionValue> PickerStyle._makeView(value: _GraphValue<_PickerValue<PickerStyle, SelectionValue>>, inputs: _ViewInputs): _ViewOutputs = error("Not Implemented")
//internal fun <SelectionValue> PickerStyle._makeViewList(value: _GraphValue<_PickerValue<PickerStyle, SelectionValue>>, inputs: _ViewListInputs): _ViewListOutputs = error("Not Implemented")

