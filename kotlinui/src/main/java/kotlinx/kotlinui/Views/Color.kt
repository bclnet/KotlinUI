package kotlinx.kotlinui

import kotlinx.serialization.*
import kotlinx.serialization.descriptors.*
import kotlinx.serialization.encoding.*

@Serializable(with = ColorSerializer::class)
class Color : View {
    open class AnyColorBox {
        override fun equals(other: Any?): Boolean {
            if (other !is AnyColorBox) return false
            val s = other as AnyColorBox
            return this.equals(s)
        }

        override fun hashCode(): Int = this.hashCode()
    }

    enum class SystemColor {
        clear, black, white, gray, red, green, blue, orange, yellow, pink, purple, primary, secondary, accentColor
    }

    class SystemColorType internal constructor(var value: SystemColor) : AnyColorBox() {
        override fun toString(): String = value.toString()
    }

    class DisplayP3 internal constructor(
        var red: Double,
        var green: Double,
        var blue: Double,
        var opacity: Double
    ) : AnyColorBox()

    class _Resolved internal constructor(
        var linearRed: Double,
        var linearGreen: Double,
        var linearBlue: Double,
        var opacity: Double
    ) : AnyColorBox() {
        override fun toString(): String =
            String.format("#%02X%02X%02X%02X", linearRed, linearGreen, linearBlue, opacity)
    }

    enum class RGBColorSpace {
        sRGB, sRGBLinear, displayP3
    }

    var provider: AnyColorBox? = null

    constructor(
        colorSpace: RGBColorSpace = RGBColorSpace.sRGB,
        red: Double,
        green: Double,
        blue: Double,
        opacity: Double = 1.0
    ) {
        when (colorSpace) {
            RGBColorSpace.sRGB -> provider = _Resolved(red, green, blue, opacity)
            RGBColorSpace.sRGBLinear -> provider = _Resolved(red, green, blue, opacity)
            RGBColorSpace.displayP3 -> provider = DisplayP3(red, green, blue, opacity)
        }
    }

    constructor(
        colorSpace: RGBColorSpace = RGBColorSpace.sRGB,
        white: Double,
        opacity: Double = 1.0
    ) {
        when (colorSpace) {
            RGBColorSpace.sRGB -> provider = _Resolved(white, white, white, opacity)
            RGBColorSpace.sRGBLinear -> provider = _Resolved(white, white, white, opacity)
            RGBColorSpace.displayP3 -> provider = DisplayP3(white, white, white, opacity)
        }
    }

    constructor(hue: Double, saturation: Double, brightness: Double, opacity: Double = 1.0) {
        val rgb = hsbToRGB(hue, saturation, brightness);
        provider = _Resolved(rgb[0], rgb[1], rgb[3], opacity);
    }

    private constructor(systemColor: SystemColor) {
        provider = SystemColorType(systemColor)
    }

    override val body: View
        get() = error("Never")

    override fun toString(): String = "$provider"

    companion object {
        var clear = Color(SystemColor.clear)
        var black = Color(SystemColor.black)
        var white = Color(SystemColor.white)
        var gray = Color(SystemColor.gray)
        var red = Color(SystemColor.red)
        var green = Color(SystemColor.green)
        var blue = Color(SystemColor.blue)
        var orange = Color(SystemColor.orange)
        var yellow = Color(SystemColor.yellow)
        var pink = Color(SystemColor.pink)
        var purple = Color(SystemColor.purple)
        var primary = Color(SystemColor.primary)
        var secondary = Color(SystemColor.secondary)
        var accentColor = Color(SystemColor.accentColor)

        private fun hsbToRGB(hue: Double, saturation: Double, brightness: Double): DoubleArray {
            val i: Double = Math.floor(hue * 6)
            val f = hue * 6 - i
            val p = brightness * (1 - saturation)
            val q = brightness * (1 - f * saturation)
            val t = brightness * (1 - (1 - f) * saturation)
            return when ((i % 6).toInt()) {
                0 -> doubleArrayOf(brightness, t, p)
                1 -> doubleArrayOf(q, brightness, p)
                2 -> doubleArrayOf(p, brightness, t)
                3 -> doubleArrayOf(p, q, brightness)
                4 -> doubleArrayOf(t, p, brightness)
                5 -> doubleArrayOf(brightness, p, q)
                else -> error("${i % 6}")
            }
        }
    }
}

class ColorSerializer : KSerializer<Color> {
    override val descriptor: SerialDescriptor =
        buildClassSerialDescriptor("Color") {
        }

    override fun serialize(encoder: Encoder, value: Color) =
        encoder.encodeStructure(descriptor) {
        }

    override fun deserialize(decoder: Decoder): Color =
        decoder.decodeStructure(descriptor) {
            error("")
        }
}

//fun foregroundColor(color: Color?): View = environment(\.foregroundColor, color)

object ForegroundColorEnvironmentKey : EnvironmentKey {
    override var key: String = "foregroundColor"
    override var defaultValue: Any? = null
}

var EnvironmentValues.foregroundColor: Color?
    get() = this[ForegroundColorEnvironmentKey]
    set(newValue) {
        this[ForegroundColorEnvironmentKey] = newValue
    }

//fun View.colorScheme(colorScheme: ColorScheme): View = environment(\.colorScheme, colorScheme)

enum class ColorScheme { light, dark }

object ColorSchemeEnvironmentKey : EnvironmentKey {
    override var key: String = "colorScheme"
    override var defaultValue: Any? = ColorScheme.dark
}

var EnvironmentValues.colorScheme: ColorScheme
    get() = this[ColorSchemeEnvironmentKey]
    set(newValue) {
        this[ColorSchemeEnvironmentKey] = newValue
    }
