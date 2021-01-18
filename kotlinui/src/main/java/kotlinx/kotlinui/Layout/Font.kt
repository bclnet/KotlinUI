package kotlinx.kotlinui

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.buildClassSerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.encoding.decodeStructure
import kotlinx.serialization.encoding.encodeStructure

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

        //: Codable
        internal object Serializer : KSerializer<Weight> {
            override val descriptor: SerialDescriptor =
                PrimitiveSerialDescriptor("Weight", PrimitiveKind.STRING)

            override fun serialize(encoder: Encoder, value: Weight) {
                when (value) {
                    ultraLight -> encoder.encodeString("ultraLight")
                    thin -> encoder.encodeString("thin")
                    regular -> encoder.encodeString("regular")
                    medium -> encoder.encodeString("medium")
                    semibold -> encoder.encodeString("semibold")
                    bold -> encoder.encodeString("bold")
                    heavy -> encoder.encodeString("heavy")
                    black -> encoder.encodeString("black")
                    else -> encoder.encodeString(value.toString())
                }
            }

            override fun deserialize(decoder: Decoder): Weight =
                when (val value = decoder.decodeString()) {
                    "ultraLight" -> ultraLight
                    "thin" -> thin
                    "regular" -> regular
                    "medium" -> medium
                    "semibold" -> semibold
                    "bold" -> bold
                    "heavy" -> heavy
                    "black" -> black
                    else -> Weight(value.toFloat())
                }
        }
    }

    enum class TextStyle {
        largeTitle, title, headline, subheadline, body, callout, footnote, caption;

        //: Codable
        internal object Serializer : KSerializer<TextStyle> {
            override val descriptor: SerialDescriptor =
                PrimitiveSerialDescriptor("TextStyle", PrimitiveKind.STRING)

            override fun serialize(encoder: Encoder, value: TextStyle) {
                when (value) {
                    TextStyle.largeTitle -> encoder.encodeString("largeTitle")
                    TextStyle.title -> encoder.encodeString("title")
                    TextStyle.headline -> encoder.encodeString("headline")
                    TextStyle.subheadline -> encoder.encodeString("subheadline")
                    TextStyle.body -> encoder.encodeString("body")
                    TextStyle.callout -> encoder.encodeString("callout")
                    TextStyle.footnote -> encoder.encodeString("footnote")
                    TextStyle.caption -> encoder.encodeString("caption")
                }
            }

            override fun deserialize(decoder: Decoder): TextStyle =
                when (val value = decoder.decodeString()) {
                    "largeTitle" -> TextStyle.largeTitle
                    "title" -> TextStyle.title
                    "headline" -> TextStyle.headline
                    "subheadline" -> TextStyle.subheadline
                    "body" -> TextStyle.body
                    "callout" -> TextStyle.callout
                    "footnote" -> TextStyle.footnote
                    "caption" -> TextStyle.caption
                    else -> error(value)
                }
        }
    }

    enum class Design {
        default, serif, rounded, monospaced;

        //: Codable
        internal object Serializer : KSerializer<Design> {
            override val descriptor: SerialDescriptor =
                PrimitiveSerialDescriptor("Design", PrimitiveKind.STRING)

            override fun serialize(encoder: Encoder, value: Design) {
                when (value) {
                    Design.default -> encoder.encodeString("default")
                    Design.serif -> encoder.encodeString("serif")
                    Design.rounded -> encoder.encodeString("rounded")
                    Design.monospaced -> encoder.encodeString("monospaced")
                }
            }

            override fun deserialize(decoder: Decoder): Design =
                when (val value = decoder.decodeString()) {
                    "default" -> Design.default
                    "serif" -> Design.serif
                    "rounded" -> Design.rounded
                    "monospaced" -> Design.monospaced
                    else -> error(value)
                }
        }
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

    //: Codable
    internal object Serializer : KSerializer<Font> {
        override val descriptor: SerialDescriptor =
            buildClassSerialDescriptor("Font") {
            }

        override fun serialize(encoder: Encoder, value: Font) =
            encoder.encodeStructure(descriptor) {
            }

        override fun deserialize(decoder: Decoder): Font =
            decoder.decodeStructure(descriptor) {
                error("")
            }
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
