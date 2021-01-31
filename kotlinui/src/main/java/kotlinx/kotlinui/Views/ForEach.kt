package kotlinx.kotlinui

data class ForEach<Data : List<Any?>, ID, Content : View>(
    val data: Data,
    val content: ViewBuilder.(Any?) -> Content
) : View {

    //where ID == Data.Element.ID, Content: View, Data.Element: Identifiable {
//    constructor(data: Data, content: Func1<Object?, Content>) {
//        this.data = data
//        this.content = content
//    }
//
//    constructor(data: Data, id: KeyPath<Object?, ID>?, content: Func1<Object?, Content>) {
//        this.data = data
//        this.content = content
//    }

    //where Data == Range<Int>, ID == Int, Content: View {
    //public ForEach(Range<Int> data, Func1<Int, Content> content) {
    //    this.data = data;
    //    this.content = content;
    //}

    override val body: Never
        get() = error("Never")
}

//internal fun <Data : List<Any?>, ID, Content : View> ForEach<Data, ID, Content>._makeView(view: _GraphValue<ForEach<Data, ID, Content>>, inputs: _ViewInputs): _ViewOutputs = error("Not Implemented")
//internal fun <Data : List<Any?>, ID, Content : View> ForEach<Data, ID, Content>._makeViewList(view: _GraphValue<ForEach<Data, ID, Content>>, inputs: _ViewListInputs): _ViewListOutputs = error("Not Implemented")