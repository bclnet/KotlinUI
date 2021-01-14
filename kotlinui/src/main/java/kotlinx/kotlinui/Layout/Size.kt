package kotlinx.kotlinui

class Size(var width: Int, var height: Int) {
    companion object {
        var zero = Size(0, 0)
    }

    override fun equals(other: Any?): Boolean {
        if (other !is Size) return false
        val s = other as Size
        return width.equals(s.width) &&
            height.equals(s.height)
    }

    override fun hashCode(): Int {
        var result = width
        result = 31 * result + height
        return result
    }
}
