package kotlinx.kotlinui

import kotlinx.serialization.*
import kotlinx.serialization.descriptors.*
import kotlinx.serialization.encoding.*
import kotlinx.serialization.json.*
import kotlin.reflect.*
import android.graphics.Typeface as UXFont

@Serializable(with = Font.Serializer::class)
data class Font internal constructor(val provider: AnyFontBox) {
    // region ANYFONTBOX

    @Serializable
    internal abstract class AnyFontBox(
        val provider: String
    ) {
        abstract fun apply(): Font

        //: Codable - JsonContentPolymorphicSerializer
        internal object Serializer : KSerializer<AnyFontBox> {
            override val descriptor: SerialDescriptor =
                buildClassSerialDescriptor("AnyFontBox")

            override fun serialize(encoder: Encoder, value: AnyFontBox) {
                val actualSerializer = findPolymorphicSerializer(value)
                actualSerializer.serialize(encoder, value)
            }

            override fun deserialize(decoder: Decoder): AnyFontBox {
                val input = decoder as JsonDecoder
                val tree = input.decodeJsonElement()
                val actualSerializer = findPolymorphicSerializer(tree)
                return input.json.decodeFromJsonElement(actualSerializer, tree)
            }

            fun findPolymorphicSerializer(value: AnyFontBox) =
                (when (value) {
                    is SystemProvider -> SystemProvider.Serializer
                    is TextStyleProvider -> TextStyleProvider.Serializer
                    is NamedProvider -> NamedProvider.Serializer
                    is PlatformFontProvider -> PlatformFontProvider.Serializer
                    is ModifierProvider<*> -> when (value.modifier.modifier) {
                        "italic" -> ModifierProvider.Serializer(ItalicModifier::class)
                        "lowercaseSmallCaps" -> ModifierProvider.Serializer(LowercaseSmallCapsModifier::class)
                        "uppercaseSmallCaps" -> ModifierProvider.Serializer(UppercaseSmallCapsModifier::class)
                        "monospacedDigit" -> ModifierProvider.Serializer(MonospacedDigitModifier::class)
                        "weight" -> ModifierProvider.Serializer(WeightModifier::class, WeightModifier.Serializer)
                        "bold" -> ModifierProvider.Serializer(BoldModifier::class)
                        "leading" -> ModifierProvider.Serializer(LeadingModifier::class, LeadingModifier.Serializer)
                        else -> error("${value.modifier.modifier}")
                    }
                    else -> error("$value")
                }) as KSerializer<AnyFontBox>

            fun findPolymorphicSerializer(tree: JsonElement) =
                (when (val provider = tree.jsonObject["provider"]?.jsonPrimitive?.content) {
                    "system" -> SystemProvider.Serializer
                    "textStyle" -> TextStyleProvider.Serializer
                    "named" -> NamedProvider.Serializer
                    "platform" -> PlatformFontProvider.Serializer
                    "italic" -> ModifierProvider.Serializer(ItalicModifier::class)
                    "lowercaseSmallCaps" -> ModifierProvider.Serializer(LowercaseSmallCapsModifier::class)
                    "uppercaseSmallCaps" -> ModifierProvider.Serializer(UppercaseSmallCapsModifier::class)
                    "monospacedDigit" -> ModifierProvider.Serializer(MonospacedDigitModifier::class)
                    "weight" -> ModifierProvider.Serializer(WeightModifier::class, WeightModifier.Serializer)
                    "bold" -> ModifierProvider.Serializer(BoldModifier::class)
                    "leading" -> ModifierProvider.Serializer(LeadingModifier::class, LeadingModifier.Serializer)
                    else -> error("$provider")
                }) as KSerializer<AnyFontBox>
        }
    }

    @Serializable(with = NamedProvider.Serializer::class)
    internal data class NamedProvider(
        val name: String,
        val size: Float,
        val textStyle: TextStyle?
    ) : AnyFontBox("named") {
        override fun apply(): Font =
            if (textStyle != null) custom(name, size, textStyle)
            else custom(name, size)

        //: Codable
        internal object Serializer : KSerializer<NamedProvider> {
            override val descriptor: SerialDescriptor =
                buildClassSerialDescriptor("NamedProvider") {
                    element<String>("provider")
                    element<String>("name")
                    element<Float>("size")
                    element<TextStyle>("textStyle")
                }

            override fun serialize(encoder: Encoder, value: NamedProvider) =
                encoder.encodeStructure(descriptor) {
                    encodeStringElement(descriptor, 0, value.provider)
                    encodeStringElement(descriptor, 1, value.name)
                    encodeFloatElement(descriptor, 2, value.size)
                    if (value.textStyle != null) encodeSerializableElement(descriptor, 3, TextStyle.Serializer, value.textStyle)
                }

            override fun deserialize(decoder: Decoder): NamedProvider =
                decoder.decodeStructure(descriptor) {
                    lateinit var name: String
                    var size = 0f
                    var textStyle: TextStyle? = null
                    while (true) {
                        when (val index = decodeElementIndex(descriptor)) {
                            0 -> ""
                            1 -> name = decodeStringElement(descriptor, 1)
                            2 -> size = decodeFloatElement(descriptor, 2)
                            3 -> textStyle = decodeSerializableElement(descriptor, 3, TextStyle.Serializer)
                            CompositeDecoder.DECODE_DONE -> break
                            else -> error("Unexpected index: $index")
                        }
                    }
                    NamedProvider(name, size, textStyle)
                }
        }
    }

    @Serializable(with = PlatformFontProvider.Serializer::class)
    internal data class PlatformFontProvider(
        val font: UXFont
    ) : AnyFontBox("platform") {
        override fun apply(): Font = Font(font)

        //: Codable
        internal object Serializer : KSerializer<PlatformFontProvider> {
            override val descriptor: SerialDescriptor =
                buildClassSerialDescriptor("PlatformFontProvider") {
                    element<String>("provider")
                    element<String>("font")
                    element<Float>("size")
                }

            override fun serialize(encoder: Encoder, value: PlatformFontProvider) =
                encoder.encodeStructure(descriptor) {
                    encodeStringElement(descriptor, 0, value.provider)
                    encodeStringElement(descriptor, 1, value.font.toString())
                    encodeFloatElement(descriptor, 2, 0f)
                }

            override fun deserialize(decoder: Decoder): PlatformFontProvider =
                decoder.decodeStructure(descriptor) {
                    lateinit var name: String
                    var size = 0f
                    while (true) {
                        when (val index = decodeElementIndex(descriptor)) {
                            0 -> ""
                            1 -> name = decodeStringElement(descriptor, 1)
                            2 -> size = decodeFloatElement(descriptor, 2)
                            CompositeDecoder.DECODE_DONE -> break
                            else -> error("Unexpected index: $index")
                        }
                    }
                    PlatformFontProvider(UXFont.create(name, 0))
                }
        }
    }

    @Serializable(with = SystemProvider.Serializer::class)
    internal data class SystemProvider(
        var size: Float,
        var weight: Weight,
        var design: Design
    ) : AnyFontBox("system") {
        override fun apply(): Font = system(size, weight, design)

        //: Codable
        internal object Serializer : KSerializer<SystemProvider> {
            override val descriptor: SerialDescriptor =
                buildClassSerialDescriptor("SystemProvider") {
                    element<String>("provider")
                    element<Float>("size")
                    element("weight", Weight.Serializer.descriptor)
                    element<Design>("design")
                }

            override fun serialize(encoder: Encoder, value: SystemProvider) =
                encoder.encodeStructure(descriptor) {
                    encodeStringElement(descriptor, 0, value.provider)
                    encodeFloatElement(descriptor, 1, value.size)
                    if (value.weight != Weight.regular) encodeSerializableElement(descriptor, 2, Weight.Serializer, value.weight)
                    if (value.design != Design.default) encodeSerializableElement(descriptor, 3, Design.Serializer, value.design)
                }

            override fun deserialize(decoder: Decoder): SystemProvider =
                decoder.decodeStructure(descriptor) {
                    var size = 0f
                    var weight = Weight.regular
                    var design = Design.default
                    while (true) {
                        when (val index = decodeElementIndex(descriptor)) {
                            0 -> ""
                            1 -> size = decodeFloatElement(descriptor, 1)
                            2 -> weight = decodeSerializableElement(descriptor, 2, Weight.Serializer)
                            3 -> design = decodeSerializableElement(descriptor, 3, Design.Serializer)
                            CompositeDecoder.DECODE_DONE -> break
                            else -> error("Unexpected index: $index")
                        }
                    }
                    SystemProvider(size, weight, design)
                }
        }
    }

    @Serializable(with = SystemProvider.Serializer::class)
    internal data class TextStyleProvider(
        val style: TextStyle,
        val design: Design,
        val weight: Weight?
    ) : AnyFontBox("textStyle") {
        override fun apply(): Font =
            if (weight == null) Font.system(style, design)
            else Font.system(style, design) //TODO: need to fix

        //: Codable
        internal object Serializer : KSerializer<TextStyleProvider> {
            override val descriptor: SerialDescriptor =
                buildClassSerialDescriptor("TextStyleProvider") {
                    element<String>("provider")
                    element<TextStyle>("style")
                    element<Design>("design")
                    element("weight", Weight.Serializer.descriptor)
                }

            override fun serialize(encoder: Encoder, value: TextStyleProvider) =
                encoder.encodeStructure(descriptor) {
                    encodeStringElement(descriptor, 0, value.provider)
                    encodeSerializableElement(descriptor, 1, TextStyle.Serializer, value.style)
                    encodeSerializableElement(descriptor, 2, Design.Serializer, value.design)
                    if (value.weight != null) encodeSerializableElement(descriptor, 3, Weight.Serializer, value.weight)
                }

            override fun deserialize(decoder: Decoder): TextStyleProvider =
                decoder.decodeStructure(descriptor) {
                    lateinit var style: TextStyle
                    lateinit var design: Design
                    var weight: Weight? = null
                    while (true) {
                        when (val index = decodeElementIndex(descriptor)) {
                            0 -> ""
                            1 -> style = decodeSerializableElement(descriptor, 1, TextStyle.Serializer)
                            2 -> design = decodeSerializableElement(descriptor, 2, Design.Serializer)
                            3 -> weight = decodeSerializableElement(descriptor, 3, Weight.Serializer)
                            CompositeDecoder.DECODE_DONE -> break
                            else -> error("Unexpected index: $index")
                        }
                    }
                    TextStyleProvider(style, design, weight)
                }
        }
    }

    @Serializable(with = ModifierProvider.Serializer::class)
    internal data class ModifierProvider<Modifier : AnyModifier>(
        val base: Font,
        val modifier: Modifier
    ) : AnyFontBox("modifier") {
        override fun apply(): Font = modifier.apply(base)

        //: Codable
        internal class Serializer<Modifier : AnyModifier>(
            private val klass: KClass<Modifier>,
            private val modifierSerializer: KSerializer<Modifier>? = null
        ) : KSerializer<ModifierProvider<Modifier>> {
            override val descriptor: SerialDescriptor =
                buildClassSerialDescriptor("ModifierProvider") {
                    element<String>("provider")
                    element<Font>("base")
                    element<AnyModifier>("modifier")
                }

            override fun serialize(encoder: Encoder, value: ModifierProvider<Modifier>) =
                encoder.encodeStructure(descriptor) {
                    encodeStringElement(descriptor, 0, value.modifier.modifier)
                    encodeSerializableElement(descriptor, 1, Font.Serializer, value.base)
                    if (modifierSerializer != null) encodeSerializableElement(descriptor, 2, modifierSerializer, value.modifier)
                }

            override fun deserialize(decoder: Decoder): ModifierProvider<Modifier> =
                decoder.decodeStructure(descriptor) {
                    lateinit var base: Font
                    var modifier: Modifier? = null
                    while (true) {
                        when (val index = decodeElementIndex(descriptor)) {
                            0 -> ""
                            1 -> base = decodeSerializableElement(descriptor, 1, Font.Serializer)
                            2 -> modifier = decodeSerializableElement(descriptor, 2, modifierSerializer!!)
                            CompositeDecoder.DECODE_DONE -> break
                            else -> error("Unexpected index: $index")
                        }
                    }
                    ModifierProvider(base, modifier ?: klass.java.newInstance())
                }
        }
    }

    // endregion

    // region ANYMODIFIER

    @Serializable
    internal abstract class AnyModifier(val modifier: String) {
        abstract fun apply(font: Font): Font
    }

    @Serializable
    internal class ItalicModifier : AnyModifier("italic") {
        override fun apply(font: Font): Font = font.italic()
        override fun equals(other: Any?): Boolean = other is ItalicModifier
    }

    @Serializable
    internal class LowercaseSmallCapsModifier : AnyModifier("lowercaseSmallCaps") {
        override fun apply(font: Font): Font = font.lowercaseSmallCaps()
        override fun equals(other: Any?): Boolean = other is LowercaseSmallCapsModifier
    }

    @Serializable
    internal class UppercaseSmallCapsModifier : AnyModifier("uppercaseSmallCaps") {
        override fun apply(font: Font): Font = font.uppercaseSmallCaps()
        override fun equals(other: Any?): Boolean = other is UppercaseSmallCapsModifier
    }

    @Serializable
    internal class MonospacedDigitModifier : AnyModifier("monospacedDigit") {
        override fun apply(font: Font): Font = font.monospacedDigit()
        override fun equals(other: Any?): Boolean = other is MonospacedDigitModifier
    }

    @Serializable(with = WeightModifier.Serializer::class)
    internal data class WeightModifier(
        val weight: Weight
    ) : AnyModifier("weight") {
        override fun apply(font: Font): Font = font.weight(weight)

        //: Codable
        internal object Serializer : KSerializer<WeightModifier> {
            override val descriptor: SerialDescriptor =
                PrimitiveSerialDescriptor("WeightModifier", PrimitiveKind.STRING)

            override fun serialize(encoder: Encoder, value: WeightModifier) =
                encoder.encodeSerializableValue(Weight.Serializer, value.weight)

            override fun deserialize(decoder: Decoder): WeightModifier =
                WeightModifier(decoder.decodeSerializableValue(Weight.Serializer))
        }
    }

    @Serializable
    internal class BoldModifier : AnyModifier("bold") {
        override fun apply(font: Font): Font = font.bold()
        override fun equals(other: Any?): Boolean = other is BoldModifier
    }

    @Serializable(with = LeadingModifier.Serializer::class)
    internal data class LeadingModifier(
        val leading: Leading
    ) : AnyModifier("leading") {
        override fun apply(font: Font): Font = font.leading(leading)

        //: Codable
        internal object Serializer : KSerializer<LeadingModifier> {
            override val descriptor: SerialDescriptor =
                PrimitiveSerialDescriptor("LeadingModifier", PrimitiveKind.STRING)

            override fun serialize(encoder: Encoder, value: LeadingModifier) =
                encoder.encodeSerializableValue(Leading.Serializer, value.leading)

            override fun deserialize(decoder: Decoder): LeadingModifier =
                LeadingModifier(decoder.decodeSerializableValue(Leading.Serializer))
        }
    }

    // endregion

    constructor(font: UXFont) : this(PlatformFontProvider(font))

    companion object {
        var largeTitle = system(TextStyle.largeTitle)
        var title = system(TextStyle.title)
        var headline = system(TextStyle.headline)
        var subheadline = system(TextStyle.subheadline)
        var body = system(TextStyle.body)
        var callout = system(TextStyle.callout)
        var footnote = system(TextStyle.footnote)
        var caption = system(TextStyle.caption)
        var title2 = system(TextStyle.title2)
        var title3 = system(TextStyle.title3)
        var caption2 = system(TextStyle.caption2)

        fun system(style: TextStyle, design: Design = Design.default): Font =
            Font(TextStyleProvider(style, design, null))

        fun system(size: Float, weight: Weight = Weight.regular, design: Design = Design.default): Font =
            Font(SystemProvider(size, weight, design))

        //        enum class customInit { Size, FixedSize, RelativeTo }
        fun custom(name: String, size: Float): Font =
            Font(NamedProvider(name, size, null))

//        fun custom(name: String, fixedSize: Float): Font =
//            Font(NamedProvider(name, size, null))

        fun custom(name: String, size: Float, relativeTo: TextStyle): Font =
            Font(NamedProvider(name, size, relativeTo))
    }

    //: Codable
    internal object Serializer : KSerializer<Font> {
        override val descriptor: SerialDescriptor =
            buildClassSerialDescriptor("Font") {
                element<String>("provider")
            }

        override fun serialize(encoder: Encoder, value: Font) =
            when (value) {
                largeTitle -> encoder.encodeString("largeTitle")
                title -> encoder.encodeString("title")
                headline -> encoder.encodeString("headline")
                subheadline -> encoder.encodeString("subheadline")
                body -> encoder.encodeString("body")
                callout -> encoder.encodeString("callout")
                footnote -> encoder.encodeString("footnote")
                caption -> encoder.encodeString("caption")
                title2 -> encoder.encodeString("title2")
                title3 -> encoder.encodeString("title3")
                caption2 -> encoder.encodeString("caption2")
                else -> encoder.encodeSerializableValue(AnyFontBox.Serializer, value.provider)
            }

        override fun deserialize(decoder: Decoder): Font {
            val input = decoder as JsonDecoder
            val tree = input.decodeJsonElement()
            return if (tree is JsonObject) Font(input.json.decodeFromJsonElement(AnyFontBox.Serializer, tree))
            else when (val provider = tree.jsonPrimitive?.content) {
                "largeTitle" -> largeTitle
                "title" -> title
                "headline" -> headline
                "subheadline" -> subheadline
                "body" -> body
                "callout" -> callout
                "footnote" -> footnote
                "caption" -> caption
                "title2" -> title2
                "title3" -> title3
                "caption2" -> caption2
                else -> error(provider)
            }
        }
    }

    // region ADDITIONALTYPES

    enum class ContentSizeCategory {
        accessibilityExtraExtraExtraLarge, accessibilityExtraExtraLarge, accessibilityExtraLarge, accessibilityLarge, accessibilityMedium,
        extraExtraExtraLarge, extraExtraLarge, extraLarge, extraSmall, large, medium, small;

        //: Codable
        internal object Serializer : KSerializer<ContentSizeCategory> {
            override val descriptor: SerialDescriptor =
                PrimitiveSerialDescriptor("ContentSizeCategory", PrimitiveKind.STRING)

            override fun serialize(encoder: Encoder, value: ContentSizeCategory) =
                encoder.encodeString(
                    when (value) {
                        accessibilityExtraExtraExtraLarge -> "accessibilityExtraExtraExtraLarge"
                        accessibilityExtraExtraLarge -> "accessibilityExtraExtraLarge"
                        accessibilityExtraLarge -> "accessibilityExtraLarge"
                        accessibilityLarge -> "accessibilityLarge"
                        accessibilityMedium -> "accessibilityMedium"
                        extraExtraExtraLarge -> "extraExtraExtraLarge"
                        extraExtraLarge -> "extraExtraLarge"
                        extraLarge -> "extraLarge"
                        extraSmall -> "extraSmall"
                        large -> "large"
                        medium -> " medium"
                        small -> "small"
                    }
                )

            override fun deserialize(decoder: Decoder): ContentSizeCategory =
                when (val value = decoder.decodeString()) {
                    "accessibilityExtraExtraExtraLarge" -> accessibilityExtraExtraExtraLarge
                    "accessibilityExtraExtraLarge" -> accessibilityExtraExtraLarge
                    "accessibilityExtraLarge" -> accessibilityExtraLarge
                    "accessibilityLarge" -> accessibilityLarge
                    "accessibilityMedium" -> accessibilityMedium
                    "extraExtraExtraLarge" -> extraExtraExtraLarge
                    "extraExtraLarge" -> extraExtraLarge
                    "extraLarge" -> extraLarge
                    "extraSmall" -> extraSmall
                    "large" -> large
                    " medium" -> medium
                    "small" -> small
                    else -> error(value)
                }
        }
    }

    enum class Leading {
        loose, standard, tight;

        //: Codable
        internal object Serializer : KSerializer<Leading> {
            override val descriptor: SerialDescriptor =
                PrimitiveSerialDescriptor("Leading", PrimitiveKind.STRING)

            override fun serialize(encoder: Encoder, value: Leading) =
                encoder.encodeString(
                    when (value) {
                        loose -> "loose"
                        standard -> "standard"
                        tight -> "tight"
                    }
                )

            override fun deserialize(decoder: Decoder): Leading =
                when (val value = decoder.decodeString()) {
                    "loose" -> loose
                    "standard" -> standard
                    "tight" -> tight
                    else -> error(value)
                }
        }
    }

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

            override fun serialize(encoder: Encoder, value: Weight) =
                encoder.encodeString(
                    when (value) {
                        ultraLight -> "ultraLight"
                        thin -> "thin"
                        light -> "light"
                        regular -> "regular"
                        medium -> "medium"
                        semibold -> "semibold"
                        bold -> "bold"
                        heavy -> "heavy"
                        black -> "black"
                        else -> value.toString()
                    }
                )

            override fun deserialize(decoder: Decoder): Weight =
                when (val value = decoder.decodeString()) {
                    "ultraLight" -> ultraLight
                    "thin" -> thin
                    "light" -> light
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
        largeTitle, title, headline, subheadline, body, callout, footnote, caption, title2, title3, caption2;

        //: Codable
        internal object Serializer : KSerializer<TextStyle> {
            override val descriptor: SerialDescriptor =
                PrimitiveSerialDescriptor("TextStyle", PrimitiveKind.STRING)

            override fun serialize(encoder: Encoder, value: TextStyle) =
                encoder.encodeString(
                    when (value) {
                        largeTitle -> "largeTitle"
                        title -> "title"
                        headline -> "headline"
                        subheadline -> "subheadline"
                        body -> "body"
                        callout -> "callout"
                        footnote -> "footnote"
                        caption -> "caption"
                        title2 -> "title2"
                        title3 -> "title3"
                        caption2 -> "caption2"
                    }
                )

            override fun deserialize(decoder: Decoder): TextStyle =
                when (val value = decoder.decodeString()) {
                    "largeTitle" -> largeTitle
                    "title" -> title
                    "headline" -> headline
                    "subheadline" -> subheadline
                    "body" -> body
                    "callout" -> callout
                    "footnote" -> footnote
                    "caption" -> caption
                    "title2" -> title2
                    "title3" -> title3
                    "caption2" -> caption2
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

            override fun serialize(encoder: Encoder, value: Design) =
                encoder.encodeString(
                    when (value) {
                        default -> "default"
                        serif -> "serif"
                        rounded -> "rounded"
                        monospaced -> "monospaced"
                    }
                )

            override fun deserialize(decoder: Decoder): Design =
                when (val value = decoder.decodeString()) {
                    "default" -> default
                    "serif" -> serif
                    "rounded" -> rounded
                    "monospaced" -> monospaced
                    else -> error(value)
                }
        }
    }

    // endregion
}

fun Font.bold(): Font =
    fontWithModifier(Font.BoldModifier())

fun Font.italic(): Font =
    fontWithModifier(Font.ItalicModifier())

fun Font.monospacedDigit(): Font =
    fontWithModifier(Font.MonospacedDigitModifier())

fun Font.smallCaps(): Font =
    fontWithModifier(Font.LowercaseSmallCapsModifier())

fun Font.lowercaseSmallCaps(): Font =
    fontWithModifier(Font.LowercaseSmallCapsModifier())

fun Font.uppercaseSmallCaps(): Font =
    fontWithModifier(Font.UppercaseSmallCapsModifier())

fun Font.leading(leading: Font.Leading): Font =
    fontWithModifier(Font.LeadingModifier(leading))

fun Font.weight(weight: Font.Weight): Font =
    fontWithModifier(Font.WeightModifier(weight))

private fun <Modifier : Font.AnyModifier> Font.fontWithModifier(modifier: Modifier): Font =
    Font(Font.ModifierProvider(this, modifier))

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
