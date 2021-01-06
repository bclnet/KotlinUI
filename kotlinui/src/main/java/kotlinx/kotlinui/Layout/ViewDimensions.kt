package kotlinx.kotlinui

class ViewDimensions {
    var width: Float = 0f

    var height: Float = 0f

    fun subscript(guide: HorizontalAlignment): Float = 0f

    fun subscript(guide: VerticalAlignment): Float = 0f

    override fun equals(o: Any?): Boolean {
        if (o !is ViewDimensions) return false
        val s = o as ViewDimensions
        return width.equals(s.width) &&
            height.equals(s.height)
    }
}
