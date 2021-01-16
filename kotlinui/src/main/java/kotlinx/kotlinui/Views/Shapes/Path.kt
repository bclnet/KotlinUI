package kotlinx.kotlinui

import android.graphics.Matrix
import android.graphics.Rect
import android.util.SizeF
import android.graphics.Path as CGPath

data class FixedRoundedRect(var rect: Rect, var cornerSize: SizeF, var style: RoundedCornerStyle)

data class StrokeStyle(
    var lineWidth: Float = 1f,
    var lineCap: LineCap = LineCap.butt,
    var lineJoin: LineJoin = LineJoin.miter,
    var miterLimit: Float = 10f,
    var dash: FloatArray = FloatArray(0),
    var dashPhase: Float = 0f
) {
    enum class LineCap {
        butt, round, square
    }

    enum class LineJoin {
        miter, round, bevel
    }
}

enum class RoundedCornerStyle {
    circular, continuous
}

internal class TrimmedPath

internal data class StrokedPath(var path: Path, var style: StrokeStyle)

class Path : Shape {
    internal data class PathBox(var cgPath: CGPath)

    internal sealed class Storage {
        class empty : Storage()
        data class rect(val rect: Rect) : Storage()
        data class ellipse(val ellipse: Rect) : Storage()
        data class roundedRect(val roundedRect: FixedRoundedRect) : Storage()
        data class stroked(val stroked: StrokedPath) : Storage()
        data class trimmed(val trimmed: TrimmedPath) : Storage()
        data class path(val path: PathBox) : Storage()
    }

    sealed class Element {
        data class move(val to: Point) : Element()
        data class line(val to: Point) : Element()
        data class quadCurve(val to: Point, val control: Point) : Element()
        data class curve(val to: Point, val control1: Point, val control2: Point) : Element()
        class closeSubpath : Element()
    }

    private lateinit var storage: Storage

    constructor() {
        error("Not Implemented")
    }

    constructor(path: CGPath) {
        storage = Storage.path(PathBox(path))
    }

    constructor(rect: Rect) {
        storage = Storage.rect(rect)
    }

    constructor(
        roundedRect: Rect,
        cornerSize: SizeF,
        style: RoundedCornerStyle = RoundedCornerStyle.circular
    ) {
        storage = Storage.roundedRect(FixedRoundedRect(roundedRect, cornerSize, style))
    }

    constructor(
        roundedRect: Rect,
        cornerRadius: Float,
        style: RoundedCornerStyle = RoundedCornerStyle.circular
    ) {
        val cornerSize = SizeF(cornerRadius, cornerRadius)
        storage = Storage.roundedRect(FixedRoundedRect(roundedRect, cornerSize, style))
    }

//    constructor(ellipse: Rect) {
//    	storage = Storage.ellipse(ellipse);
//    }

    constructor(callback: (Path) -> Unit) {
        error("Not Implemented")
    }

    constructor(string: String) {
        error("Not Implemented")
    }

    override fun path(rect: Rect): Path = error("Never")

    override val body: Never
        get() = error("Never")

    val cgPath: CGPath
        get() = error("Not Implemented")

    val isEmpty: Boolean
        get() = error("Not Implemented")

    val boundingRect: Rect
        get() = error("Not Implemented")

    operator fun contains(p: Point): Boolean = contains(p, false)
    fun contains(p: Point, eoFill: Boolean): Boolean = error("Not Implemented")

    fun forEach(body: (Element) -> Unit): Nothing = error("Not Implemented")

    fun strokedPath(style: StrokeStyle): Path = error("Not Implemented")

    fun trimmedPath(from: Float, to: Float): Path = error("Not Implemented")

    // MUTATING
    fun move(to: Point) {}

    fun addLine(to: Point) {}

    fun addQuadCurve(to: Point, control: Point) {}

    fun addCurve(to: Point, control1: Point, control2: Point) {}

    fun closeSubpath() {}

    fun addRect(rect: Rect, transform: Matrix? = null) {}

    fun addRoundedRect(
        rect: Rect, cornerSize: SizeF, style: RoundedCornerStyle = RoundedCornerStyle.circular,
        transform: Matrix? = null
    ) {
    }

    fun addEllipse(rect: Rect, transform: Matrix? = null) {}

    fun addRects(rects: Array<Rect>, transform: Matrix? = null) {}

    fun addLines(lines: Array<Point>) {}

    fun addRelativeArc(
        center: Point, radius: Float, startAngle: Angle, delta: Angle,
        transform: Matrix? = null
    ) {
    }

    fun addArc(
        center: Point, radius: Float, startAngle: Angle, endAngle: Angle, clockwise: Boolean,
        transform: Matrix? = null
    ) {
    }

    fun addArc(tangent1End: Point, tangent2End: Point, radius: Float, transform: Matrix? = null) {}

    fun addPath(path: Path, transform: Matrix? = null) {}

    val currentPoint: Point
        get() = error("Not Implemented")

    fun applying(transform: Matrix? = null): Path = error("Not Implemented")

    fun offsetBy(dx: Float, dy: Float): Path = error("Not Implemented")
}
