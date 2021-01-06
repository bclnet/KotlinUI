package kotlinx.kotlinui

import android.graphics.Matrix
import android.graphics.Rect
import android.util.SizeF
import kotlin.system.exitProcess
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
    override fun equals(o: Any?): Boolean {
        if (o !is TrimmedPath) return false
        val s = o as TrimmedPath
        return true
    }
}

internal class StrokedPath(var path: Path, var style: StrokeStyle) {
    override fun equals(o: Any?): Boolean {
        if (o !is StrokedPath) return false
        val s = o as StrokedPath
        return path.equals(s.path) &&
            style.equals(s.style)
    }
}

class Path : Shape {
    internal class PathBox(var cgPath: CGPath) {
        override fun equals(o: Any?): Boolean {
            if (o !is PathBox) return false
            val s = o as PathBox
            return cgPath.equals(s.cgPath)
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
        exitProcess(0)
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
        exitProcess(0)
    }

    constructor(string: String) {
        exitProcess(0)
    }

    override fun path(rect: Rect): Path = exitProcess(0)
    override val body: View = exitProcess(0)

    val cgPath: CGPath = exitProcess(0)

    val isEmpty: Boolean = exitProcess(0)

    val boundingRect: Rect = exitProcess(0)

    operator fun contains(p: Point): Boolean = contains(p, false)
    fun contains(p: Point, eoFill: Boolean): Boolean = exitProcess(0)

    fun forEach(body: (Element) -> Unit): Nothing = exitProcess(0)

    fun strokedPath(style: StrokeStyle): Path = exitProcess(0)

    fun trimmedPath(from: Float, to: Float): Path = exitProcess(0)

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

    val currentPoint: Point = exitProcess(0)

    fun applying(transform: Matrix? = null): Path = exitProcess(0)

    fun offsetBy(dx: Float, dy: Float): Path = exitProcess(0)
}
