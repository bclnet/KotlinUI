package kotlinx.kotlinui

class Point(var x: Int, var y: Int) {
    companion object {
        var zero = Point(0, 0)
    }

    override fun equals(other: Any?): Boolean {
        if (other !is Point) return false
        val s = other as Point
        return x.equals(s.x) &&
            y.equals(s.y)
    }

    override fun hashCode(): Int {
        var result = x
        result = 31 * result + y
        return result
    }
}
