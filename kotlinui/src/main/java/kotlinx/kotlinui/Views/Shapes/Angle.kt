package kotlinx.kotlinui

data class Angle(var radians: Double = 0.0) {
    enum class Init { radians, degrees }

    constructor(value: Double, init: Init = Init.radians) : this(when (init) {
        Init.radians -> value
        Init.degrees -> value * (Math.PI / 180.0)
    })

    var degrees: Double
        get() = radians * (180.0 / Math.PI)
        set(newValue) {
            radians = newValue * (Math.PI / 180.0)
        }

    companion object {
        fun radians(radians: Double): Angle = Angle(radians)
        fun degrees(degrees: Double): Angle = Angle(degrees * (Math.PI / 180.0))
    }
}
