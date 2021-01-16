package kotlinx.kotlinui

data class Font private constructor(private val provider: AnyFontBox) {
    private abstract class AnyFontBox

    private data class SystemProvider(var size: Float, var weight: Weight, var design: Design) : AnyFontBox()

    private data class TextStyleProvider(var style: TextStyle, var design: Design) : AnyFontBox()

    data class Weight(var value: Float) {
        companion object {
            var ultraLight = Weight(100f)
            var thin = Weight(200f)
            var light = Weight(300f)
            var regular = Weight(400f)
            var medium = Weight(500f)
            var semibold = Weight(600f)
            var bold = Weight(700f)
            var heavy = Weight(800f)
            var black = Weight(900f)
        }
    }

    enum class TextStyle {
        largeTitle, title, headline, subheadline, body, callout, footnote, caption
    }

    enum class Design {
        default, serif, rounded, monospaced
    }

    companion object {
        fun system(style: TextStyle, design: Design = Design.default): Font {
            val provider: AnyFontBox = TextStyleProvider(style, design)
            return Font(provider)
        }

        fun system(
            size: Float,
            weight: Weight = Weight.regular,
            design: Design = Design.default
        ): Font {
            val provider: AnyFontBox = SystemProvider(size, weight, design)
            return Font(provider)
        }

        fun custom(name: String, size: Float): Font {
            error("Not Implemented")
        }

        var largeTitle = system(TextStyle.largeTitle)
        var title = system(TextStyle.title)
        var headline = system(TextStyle.headline)
        var subheadline = system(TextStyle.subheadline)
        var body = system(TextStyle.body)
        var callout = system(TextStyle.callout)
        var footnote = system(TextStyle.footnote)
        var caption = system(TextStyle.caption)
    }
}

//func View.font(font: Font?) : View = environment(\.font, font)

object FontEnvironmentKey : EnvironmentKey {
    override var key: String = "font"
    override var defaultValue: Any? = null
}

var EnvironmentValues.font: Font?
    get() = this[FontEnvironmentKey]
    set(newValue) {
        this[FontEnvironmentKey] = newValue
    }
