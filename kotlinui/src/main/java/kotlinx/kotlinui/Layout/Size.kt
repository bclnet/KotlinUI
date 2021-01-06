package kotlinx.kotlinui

class Size(var width: Int, var height: Int) {
    companion object {
        var zero = Size(0, 0)
    }

    override fun equals(o: Any?): Boolean {
        if (o !is Size) return false
        val s = o as Size
        return width.equals(s.width) &&
            height.equals(s.height)
    }
}
