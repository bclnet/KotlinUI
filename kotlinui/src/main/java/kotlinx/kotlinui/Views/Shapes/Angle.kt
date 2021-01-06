package kotlinx.kotlinui

class Angle(var radians: Double = 0.0) {
    var degrees: Double
        get() = radians * (180.0 / Math.PI)
        set(newValue) {
            radians = newValue * (Math.PI / 180.0)
        }

    companion object {
        fun fromDegrees(degrees: Double): Angle = Angle(degrees * (Math.PI / 180.0))
        fun radians(radians: Double): Angle = Angle(radians)
        fun degrees(degrees: Double): Angle = Angle(degrees * (Math.PI / 180.0))
    }
}
