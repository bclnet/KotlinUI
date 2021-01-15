package kotlinx.kotlinui

import java.util.EnumSet

class PaddingModifier(var value: EdgeInsets) : ViewModifier {
    companion object {
        var defaultPadding = 8f
    }

    constructor(edges: EnumSet<Edge> = Edge.Set.all, length: Float = defaultPadding) : this(
        EdgeInsets(
            if (edges.contains(Edge.top)) length else 0f,
            if (edges.contains(Edge.leading)) length else 0f,
            if (edges.contains(Edge.bottom)) length else 0f,
            if (edges.contains(Edge.trailing)) length else 0f
        )
    )

    constructor(length: Float) : this(EdgeInsets(length, length, length, length))

    override fun toString(): String = String.format("PaddingModifier {padding: %f}", value)
}

fun View.padding(insets: EdgeInsets): View = modifier(PaddingModifier(insets))

fun View.padding(edges: EnumSet<Edge> = Edge.Set.all, length: Float? = null): View =
    modifier(PaddingModifier(edges, length ?: PaddingModifier.defaultPadding))

fun View.padding(length: Float): View = modifier(PaddingModifier(length))
