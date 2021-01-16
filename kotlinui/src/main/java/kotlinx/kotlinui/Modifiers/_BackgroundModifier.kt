package kotlinx.kotlinui

class _BackgroundModifier<Background : View>(
    var background: Background,
    var alignment: Alignment
) : ViewModifier

//internal fun <Background : View> _BackgroundModifier<Background>._makeView(modifier: _GraphValue<_BackgroundModifier<Background>>, inputs: _ViewInputs, body: (_Graph, _ViewInputs) -> _ViewOutputs): _ViewOutputs = error("Not Implemented")

fun <Background : View> View.background(background: Background, alignment: Alignment = Alignment.center): View =
    modifier(_BackgroundModifier(background, alignment))
