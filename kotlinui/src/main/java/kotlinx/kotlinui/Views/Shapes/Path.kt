package kotlinx.kotlinui

import android.graphics.Matrix
import android.graphics.Rect
import android.util.SizeF
import kotlinx.ptype.PType
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import android.graphics.Path as CGPath

internal class TrimmedPath

internal data class StrokedPath(
    val path: Path,
    val style: StrokeStyle
)

data class Path internal constructor(
    val storage: Storage
) : Shape {
    internal data class PathBox(val cgPath: CGPath)

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

    constructor() : this(Storage.empty())

    constructor(path: CGPath) : this(Storage.path(PathBox(path)))

    constructor(rect: Rect) : this(Storage.rect(rect))

    constructor(
        roundedRect: Rect,
        cornerSize: SizeF,
        style: RoundedCornerStyle = RoundedCornerStyle.circular
    ) : this(Storage.roundedRect(FixedRoundedRect(roundedRect, cornerSize, style)))

    constructor(
        roundedRect: Rect,
        cornerRadius: Float,
        style: RoundedCornerStyle = RoundedCornerStyle.circular
    ) : this(Storage.roundedRect(FixedRoundedRect(roundedRect, SizeF(cornerRadius, cornerRadius), style)))

    //    constructor(ellipse: Rect): this (Storage.ellipse(ellipse))
    constructor(callback: (Path) -> Unit) : this(Storage.empty())
    constructor(string: String) : this(Storage.empty())

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

    companion object {
        //: Register
        fun register() {
            PType.register<Path>()
        }
    }
}
