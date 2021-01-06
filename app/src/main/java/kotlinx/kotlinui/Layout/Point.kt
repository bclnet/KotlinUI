package kotlinx.kotlinui

class Point(var x: Int, var y: Int) {
    companion object {
        var zero = Point(0, 0)
    }

    override fun equals(o: Any?): Boolean {
        if (o !is Point) return false
        val s = o as Point
        return x.equals(s.x) &&
            y.equals(s.y)
    }
}
