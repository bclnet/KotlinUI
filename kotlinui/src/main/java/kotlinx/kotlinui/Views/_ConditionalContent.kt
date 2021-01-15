package kotlinx.kotlinui

class _ConditionalContent<TrueContent : View, FalseContent : View>(
    val _storage: Storage
) : View {
    sealed class Storage {
        data class trueContent<TrueContent : View>(val trueContent: TrueContent) : Storage()
        data class falseContent<FalseContent : View>(val falseContent: FalseContent) : Storage()
    }

    override val body: Never
        get() = error("Never")
}

internal fun <TrueContent : View, FalseContent : View> _ConditionalContent<TrueContent, FalseContent>._makeView(view: _GraphValue<_ConditionalContent<TrueContent, FalseContent>>, inputs: _ViewInputs): _ViewOutputs = error("Not Implemented")
internal fun <TrueContent : View, FalseContent : View> _ConditionalContent<TrueContent, FalseContent>._makeViewList(view: _GraphValue<_ConditionalContent<TrueContent, FalseContent>>, inputs: _ViewListInputs): _ViewListOutputs = error("Not Implemented")