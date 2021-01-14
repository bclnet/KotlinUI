package kotlinx.kotlinui

import android.graphics.Matrix
import android.graphics.Rect
import android.util.SizeF
import kotlinx.system.KTypeBase
import android.graphics.Path as CGPath

class FixedRoundedRect(var rect: Rect, var cornerSize: SizeF, var style: RoundedCornerStyle) {
    override fun equals(o: Any?): Boolean {
        if (o !is FixedRoundedRect) return false
        val s = o as FixedRoundedRect
        return rect.equals(s.rect) &&
            cornerSize.equals(s.cornerSize) &&
            style.equals(s.style)
    }
}

class StrokeStyle(
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

internal class TrimmedPath {
    override fun equals(other: Any?): Boolean {
        if (other !is TrimmedPath) return false
        val s = other as TrimmedPath
        return true
    }

    override fun hashCode(): Int {
        return javaClass.hashCode()
    }
}

internal class StrokedPath(var path: Path, var style: StrokeStyle) {
    override fun equals(other: Any?): Boolean {
        if (other !is StrokedPath) return false
        val s = other as StrokedPath
        return path.equals(s.path) &&
            style.equals(s.style)
    }

    override fun hashCode(): Int {
        var result = path.hashCode()
        result = 31 * result + style.hashCode()
        return result
    }
}

class Path : KTypeBase, Shape {
    internal class PathBox(var cgPath: CGPath) {
        override fun equals(other: Any?): Boolean {
            if (other !is PathBox) return false
            val s = other as PathBox
            return cgPath.equals(s.cgPath)
        }

        override fun hashCode(): Int {
            return cgPath.hashCode()
        }
    }

    internal enum class StorageType { empty, rect, ellipse, roundedRect, stroked, trimmed, path }
    internal class Storage(var type: StorageType) {
        var rect: Rect? = null
        var ellipse: Rect? = null
        var roundedRect: FixedRoundedRect? = null
        var stroked: StrokedPath? = null
        var trimmed: TrimmedPath? = null
        var path: PathBox? = null
    }

    enum class ElementType { move, line, quadCurve, curve }
    class Element(var type: ElementType) {
        var to: Point? = null
        var control: Point? = null
        var control2: Point? = null
    }

    internal var storage: Storage = Storage(StorageType.empty)

    constructor() {
        error("Not Implemented")
    }

    constructor(path: CGPath) {
        storage = Storage(StorageType.path)
        storage.path = PathBox(path)
    }

    constructor(rect: Rect) {
        storage = Storage(StorageType.rect)
        storage.rect = rect
    }

    constructor(
        roundedRect: Rect,
        cornerSize: SizeF,
        style: RoundedCornerStyle = RoundedCornerStyle.circular
    ) {
        storage = Storage(StorageType.roundedRect)
        storage.roundedRect = FixedRoundedRect(roundedRect, cornerSize, style)
    }

    constructor(
        roundedRect: Rect,
        cornerRadius: Float,
        style: RoundedCornerStyle = RoundedCornerStyle.circular
    ) {
        val cornerSize = SizeF(cornerRadius, cornerRadius)
        storage = Storage(StorageType.roundedRect)
        storage.roundedRect = FixedRoundedRect(roundedRect, cornerSize, style)
    }

//    constructor(ellipse: Rect) {
//    	storage = Storage(StorageType.ellipse);
//    	storage.ellipse = ellipse;
//    }

    constructor(callback: (Path) -> Unit) {
        error("Not Implemented")
    }

    constructor(string: String) {
        error("Not Implemented")
    }

    override fun path(rect: Rect): Path = error("Never")
    override val body: View
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

    val currentPoint: Point = error("Not Implemented")

    fun applying(transform: Matrix? = null): Path = error("Not Implemented")

    fun offsetBy(dx: Float, dy: Float): Path = error("Not Implemented")
}
