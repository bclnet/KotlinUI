package kotlinx.kotlinui

class Font(var provider: AnyFontBox) {
    open class AnyFontBox {
        override fun hashCode(): Int = this.hashCode()

        override fun equals(other: Any?): Boolean {
            if (other !is AnyFontBox) return false
            val s = other as AnyFontBox
            return this.equals(s)
        }
    }

    class SystemProvider(var size: Float, var weight: Weight, var design: Design) : AnyFontBox() {
        override fun equals(other: Any?): Boolean {
            if (other !is SystemProvider) return false
            val s = other as SystemProvider
            return size == s.size && weight.equals(s.weight) && design.equals(s.design)
        }

        override fun hashCode(): Int {
            var result = super.hashCode()
            result = 31 * result + size.hashCode()
            result = 31 * result + weight.hashCode()
            result = 31 * result + design.hashCode()
            return result
        }
    }

    class TextStyleProvider(var style: TextStyle, var design: Design) : AnyFontBox() {
        override fun equals(other: Any?): Boolean {
            if (other !is TextStyleProvider) return false
            val s = other as TextStyleProvider
            return style.equals(s.style) && design.equals(s.design)
        }

        override fun hashCode(): Int {
            var result = super.hashCode()
            result = 31 * result + style.hashCode()
            result = 31 * result + design.hashCode()
            return result
        }
    }

    override fun equals(other: Any?): Boolean {
        if (other !is Font) return false
        val s = other as Font?
        return provider.equals(s!!.provider)
    }

    override fun hashCode(): Int {
        return provider.hashCode()
    }

    class Weight(var value: Float) {
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
