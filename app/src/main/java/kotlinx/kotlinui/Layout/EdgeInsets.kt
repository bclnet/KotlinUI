package kotlinx.kotlinui

class EdgeInsets constructor(
    var top: Float = 0f,
    var leading: Float = 0f,
    var bottom: Float = 0f,
    var trailing: Float = 0f
) {
    constructor(all: Float) : this(all, all, all, all) {}

    override fun equals(o: Any?): Boolean {
        if (o !is EdgeInsets) return false
        val s = o as EdgeInsets
        return top.equals(s.top) &&
            leading.equals(s.leading) &&
            bottom.equals(s.bottom) &&
            trailing.equals(s.trailing)
    }
}
