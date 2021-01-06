package kotlinx.kotlinui

class Alignment(var horizontal: HorizontalAlignment, var vertical: VerticalAlignment) {
    companion object {
        var center = Alignment(HorizontalAlignment.center, VerticalAlignment.center)
        var leading = Alignment(HorizontalAlignment.leading, VerticalAlignment.center)
        var trailing = Alignment(HorizontalAlignment.trailing, VerticalAlignment.center)
        var top = Alignment(HorizontalAlignment.center, VerticalAlignment.top)
        var bottom = Alignment(HorizontalAlignment.center, VerticalAlignment.bottom)
        var topLeading = Alignment(HorizontalAlignment.leading, VerticalAlignment.top)
        var topTrailing = Alignment(HorizontalAlignment.trailing, VerticalAlignment.top)
        var bottomLeading = Alignment(HorizontalAlignment.leading, VerticalAlignment.bottom)
        var bottomTrailing = Alignment(HorizontalAlignment.trailing, VerticalAlignment.bottom)
    }
}

enum class HorizontalAlignment {
    leading, center, trailing
}

enum class VerticalAlignment {
    top, center, bottom, firstTextBaseline, lastTextBaseline
}
