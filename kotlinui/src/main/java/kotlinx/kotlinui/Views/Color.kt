package kotlinx.kotlinui

import kotlinx.ptype.PType
import kotlinx.serialization.*
import kotlinx.serialization.descriptors.*
import kotlinx.serialization.encoding.*
import kotlinx.serialization.json.*
import java.util.*
import android.graphics.Color as CGColor

//import androidx.compose.ui.graphics.Color as UXColor

@Serializable(with = Color.Serializer::class)
data class Color internal constructor(
    val provider: AnyColorBox
) : View {
    // region ANYCOLORBOX

    @Serializable
    internal abstract class AnyColorBox(
        val provider: String
    ) {
        abstract fun apply(): Color

        //: Codable - JsonContentPolymorphicSerializer
        object Serializer : KSerializer<AnyColorBox> {
            override val descriptor: SerialDescriptor =
                buildClassSerialDescriptor("AnyColorBox")

            override fun serialize(encoder: Encoder, value: AnyColorBox) {
                val actualSerializer = findPolymorphicSerializer(value)
                actualSerializer.serialize(encoder, value)
            }

            override fun deserialize(decoder: Decoder): AnyColorBox {
                val input = decoder as JsonDecoder
                val tree = input.decodeJsonElement()
                val actualSerializer = findPolymorphicSerializer(tree)
                return input.json.decodeFromJsonElement(actualSerializer, tree)
            }

            fun findPolymorphicSerializer(value: AnyColorBox) =
                (when (value) {
                    is __NSCFType -> __NSCFType.Serializer
                    is _Resolved -> _Resolved.Serializer
                    is DisplayP3 -> DisplayP3.Serializer
                    is NamedColor -> NamedColor.Serializer
//                    is PlatformColor -> PlatformColor.Serializer
                    is OpacityColor -> OpacityColor.Serializer
                    else -> error("$value")
                }) as KSerializer<AnyColorBox>

            fun findPolymorphicSerializer(tree: JsonElement) =
                (when (val provider = tree.jsonObject["provider"]?.jsonPrimitive?.content) {
                    "system" -> __NSCFType.Serializer
                    "resolved" -> _Resolved.Serializer
                    "displayP3" -> DisplayP3.Serializer
                    "named" -> NamedColor.Serializer
//                    "platform" -> PlatformColor.Serializer
                    "opacity" -> OpacityColor.Serializer
                    else -> error("$provider")
                }) as KSerializer<AnyColorBox>
        }
    }

    @Serializable(with = __NSCFType.Serializer::class)
    internal data class __NSCFType(
        val cgColor: CGColor
    ) : AnyColorBox("system") {
        override fun apply(): Color = Color(cgColor)

        //: Codable
        object Serializer : KSerializer<__NSCFType> {
            override val descriptor: SerialDescriptor =
                buildClassSerialDescriptor("__NSCFType") {
                    element<String>("provider")
                    element("value", CGColorSerializer.descriptor)
                }

            override fun serialize(encoder: Encoder, value: __NSCFType) =
                encoder.encodeStructure(descriptor) {
                    encodeStringElement(descriptor, 0, value.provider)
                    encodeSerializableElement(descriptor, 1, CGColorSerializer, value.cgColor)
                }

            override fun deserialize(decoder: Decoder): __NSCFType =
                decoder.decodeStructure(descriptor) {
                    lateinit var cgColor: CGColor
                    while (true) {
                        when (val index = decodeElementIndex(descriptor)) {
                            0 -> ""
                            1 -> cgColor = decodeSerializableElement(descriptor, 1, CGColorSerializer)
                            CompositeDecoder.DECODE_DONE -> break
                            else -> error("Unexpected index: $index")
                        }
                    }
                    __NSCFType(cgColor)
                }
        }
    }

    @Serializable(with = _Resolved.Serializer::class)
    internal data class _Resolved(
        val linearRed: Float,
        val linearGreen: Float,
        val linearBlue: Float,
        val opacity: Float
    ) : AnyColorBox("resolved") {
        override fun apply(): Color = Color(RGBColorSpace.sRGBLinear, linearRed.toDouble(), linearGreen.toDouble(), linearBlue.toDouble(), opacity.toDouble())
        override fun toString(): String = String.format("#%02X%02X%02X%02X", linearRed, linearGreen, linearBlue, opacity)

        //: Codable
        object Serializer : KSerializer<_Resolved> {
            override val descriptor: SerialDescriptor =
                buildClassSerialDescriptor("_Resolved") {
                    element<String>("provider")
                    element<Float>("red")
                    element<Float>("green")
                    element<Float>("blue")
                    element<Float>("opacity")
                }

            override fun serialize(encoder: Encoder, value: _Resolved) =
                encoder.encodeStructure(descriptor) {
                    encodeStringElement(descriptor, 0, value.provider)
                    encodeFloatElement(descriptor, 1, value.linearRed)
                    encodeFloatElement(descriptor, 2, value.linearGreen)
                    encodeFloatElement(descriptor, 3, value.linearBlue)
                    encodeFloatElement(descriptor, 4, value.opacity)
                }

            override fun deserialize(decoder: Decoder): _Resolved =
                decoder.decodeStructure(descriptor) {
                    var linearRed: Float = 0f
                    var linearGreen: Float = 0f
                    var linearBlue: Float = 0f
                    var opacity: Float = 0f
                    while (true) {
                        when (val index = decodeElementIndex(descriptor)) {
                            0 -> ""
                            1 -> linearRed = decodeFloatElement(descriptor, 1)
                            2 -> linearGreen = decodeFloatElement(descriptor, 2)
                            3 -> linearBlue = decodeFloatElement(descriptor, 3)
                            4 -> opacity = decodeFloatElement(descriptor, 4)
                            CompositeDecoder.DECODE_DONE -> break
                            else -> error("Unexpected index: $index")
                        }
                    }
                    _Resolved(linearRed, linearGreen, linearBlue, opacity)
                }
        }
    }

    @Serializable(with = DisplayP3.Serializer::class)
    internal data class DisplayP3(
        val red: Float,
        val green: Float,
        val blue: Float,
        val opacity: Float
    ) : AnyColorBox("displayP3") {
        override fun apply(): Color = Color(RGBColorSpace.sRGBLinear, red.toDouble(), green.toDouble(), blue.toDouble(), opacity.toDouble())

        //: Codable
        object Serializer : KSerializer<DisplayP3> {
            override val descriptor: SerialDescriptor =
                buildClassSerialDescriptor("DisplayP3") {
                    element<String>("provider")
                    element<Float>("red")
                    element<Float>("green")
                    element<Float>("blue")
                    element<Float>("opacity")
                }

            override fun serialize(encoder: Encoder, value: DisplayP3) =
                encoder.encodeStructure(descriptor) {
                    encodeStringElement(descriptor, 0, value.provider)
                    encodeFloatElement(descriptor, 1, value.red)
                    encodeFloatElement(descriptor, 2, value.green)
                    encodeFloatElement(descriptor, 3, value.blue)
                    encodeFloatElement(descriptor, 4, value.opacity)
                }

            override fun deserialize(decoder: Decoder): DisplayP3 =
                decoder.decodeStructure(descriptor) {
                    var red: Float = 0f
                    var green: Float = 0f
                    var blue: Float = 0f
                    var opacity: Float = 0f
                    while (true) {
                        when (val index = decodeElementIndex(descriptor)) {
                            0 -> ""
                            1 -> red = decodeFloatElement(descriptor, 1)
                            2 -> green = decodeFloatElement(descriptor, 2)
                            3 -> blue = decodeFloatElement(descriptor, 3)
                            4 -> opacity = decodeFloatElement(descriptor, 4)
                            CompositeDecoder.DECODE_DONE -> break
                            else -> error("Unexpected index: $index")
                        }
                    }
                    DisplayP3(red, green, blue, opacity)
                }
        }
    }

    @Serializable(with = NamedColor.Serializer::class)
    internal data class NamedColor(
        val name: String,
        val bundle: ResourceBundle?,
    ) : AnyColorBox("named") {
        override fun apply(): Color = Color(name, bundle)

        //: Codable
        object Serializer : KSerializer<NamedColor> {
            override val descriptor: SerialDescriptor =
                buildClassSerialDescriptor("NamedColor") {
                    element<String>("provider")
                    element<String>("name")
                    element("bundle", ResourceBundleSerializer.descriptor)
                }

            override fun serialize(encoder: Encoder, value: NamedColor) =
                encoder.encodeStructure(descriptor) {
                    encodeStringElement(descriptor, 0, value.provider)
                    encodeStringElement(descriptor, 1, value.name)
                    if (value.bundle != null) encodeSerializableElement(descriptor, 2, ResourceBundleSerializer, value.bundle!!)
                }

            override fun deserialize(decoder: Decoder): NamedColor =
                decoder.decodeStructure(descriptor) {
                    lateinit var name: String
                    var bundle: ResourceBundle? = null
                    while (true) {
                        when (val index = decodeElementIndex(descriptor)) {
                            0 -> ""
                            1 -> name = decodeStringElement(descriptor, 1)
                            2 -> bundle = decodeSerializableElement(descriptor, 2, ResourceBundleSerializer)
                            CompositeDecoder.DECODE_DONE -> break
                            else -> error("Unexpected index: $index")
                        }
                    }
                    NamedColor(name, bundle)
                }
        }
    }

//    @Serializable(with = PlatformColor.Serializer::class)
//    internal data class PlatformColor(
//        val color: UXColor,
//    ) : AnyColorBox("platform") {
//        override fun apply(): Color = Color(color)
//
//        //: Codable
//        object Serializer : KSerializer<PlatformColor> {
//            override val descriptor: SerialDescriptor =
//                buildClassSerialDescriptor("PlatformColor") {
//                    element<String>("provider")
//                    element<UXColorSerializer>("bundle")
//                }
//
//            override fun serialize(encoder: Encoder, value: PlatformColor) =
//                encoder.encodeStructure(descriptor) {
//                    encodeStringElement(descriptor, 0, value.provider)
//                    encodeSerializableElement(descriptor, 1, UXColorSerializer, value.color)
//                }
//
//            override fun deserialize(decoder: Decoder): PlatformColor =
//                decoder.decodeStructure(descriptor) {
//                    lateinit var color: UXColor
//                    var bundle: ResourceBundle? = null
//                    while (true) {
//                        when (val index = decodeElementIndex(descriptor)) {
//                            0 -> ""
//                            1 -> color = decodeSerializableElement(descriptor, 1, UXColorSerializer)
//                            CompositeDecoder.DECODE_DONE -> break
//                            else -> error("Unexpected index: $index")
//                        }
//                    }
//                    PlatformColor(color)
//                }
//        }
//    }

    @Serializable(with = OpacityColor.Serializer::class)
    internal data class OpacityColor(
        val base: Color,
        val opacity: Double,
    ) : AnyColorBox("opacity") {
        override fun apply(): Color = base.opacity(opacity)

        //: Codable
        object Serializer : KSerializer<OpacityColor> {
            override val descriptor: SerialDescriptor =
                buildClassSerialDescriptor("OpacityColor") {
                    element<String>("provider")
                    element<Color>("base")
                    element<Double>("opacity")
                }

            override fun serialize(encoder: Encoder, value: OpacityColor) =
                encoder.encodeStructure(descriptor) {
                    encodeStringElement(descriptor, 0, value.provider)
                    encodeSerializableElement(descriptor, 1, Color.Serializer, value.base)
                    encodeDoubleElement(descriptor, 2, value.opacity)
                }

            override fun deserialize(decoder: Decoder): OpacityColor =
                decoder.decodeStructure(descriptor) {
                    lateinit var base: Color
                    var opacity: Double = 0.0
                    while (true) {
                        when (val index = decodeElementIndex(descriptor)) {
                            0 -> ""
                            1 -> base = decodeSerializableElement(descriptor, 1, Color.Serializer)
                            2 -> opacity = decodeDoubleElement(descriptor, 2)
                            CompositeDecoder.DECODE_DONE -> break
                            else -> error("Unexpected index: $index")
                        }
                    }
                    OpacityColor(base, opacity)
                }
        }
    }

    // endregion

    constructor(name: String, bundle: ResourceBundle? = null) : this(NamedColor(name, bundle))
    constructor(colorSpace: RGBColorSpace = RGBColorSpace.sRGB, red: Double, green: Double, blue: Double, opacity: Double = 1.0) : this(
        when (colorSpace) {
            RGBColorSpace.sRGB -> _Resolved(red.toFloat(), green.toFloat(), blue.toFloat(), opacity.toFloat())
            RGBColorSpace.sRGBLinear -> _Resolved(red.toFloat(), green.toFloat(), blue.toFloat(), opacity.toFloat())
            RGBColorSpace.displayP3 -> DisplayP3(red.toFloat(), green.toFloat(), blue.toFloat(), opacity.toFloat())
        }
    )

    constructor(colorSpace: RGBColorSpace = RGBColorSpace.sRGB, white: Double, opacity: Double = 1.0) : this(
        when (colorSpace) {
            RGBColorSpace.sRGB -> _Resolved(white.toFloat(), white.toFloat(), white.toFloat(), opacity.toFloat())
            RGBColorSpace.sRGBLinear -> _Resolved(white.toFloat(), white.toFloat(), white.toFloat(), opacity.toFloat())
            RGBColorSpace.displayP3 -> DisplayP3(white.toFloat(), white.toFloat(), white.toFloat(), opacity.toFloat())
        }
    )

    constructor(hue: Double, saturation: Double, brightness: Double, opacity: Double = 1.0) : this(hsbToRGB(hue, saturation, brightness).let {
        _Resolved(it[0].toFloat(), it[1].toFloat(), it[2].toFloat(), opacity.toFloat())
    })

    constructor(cgColor: CGColor) : this(__NSCFType(cgColor))
//    constructor(color: UXColor) : this(PlatformColor(color))

    override val body: View
        get() = error("Never")

    override fun toString(): String = "$provider"

    //: Codable
    internal object Serializer : KSerializer<Color> {
        override val descriptor: SerialDescriptor =
            buildClassSerialDescriptor("Color") {
                element<String>("provider")
            }

        override fun serialize(encoder: Encoder, value: Color) =
            when (value) {
                clear -> encoder.encodeString("clear")
                black -> encoder.encodeString("black")
                white -> encoder.encodeString("white")
                gray -> encoder.encodeString("gray")
                red -> encoder.encodeString("red")
                green -> encoder.encodeString("green")
                blue -> encoder.encodeString("blue")
                orange -> encoder.encodeString("orange")
                yellow -> encoder.encodeString("yellow")
                pink -> encoder.encodeString("pink")
                purple -> encoder.encodeString("purple")
                primary -> encoder.encodeString("primary")
                secondary -> encoder.encodeString("secondary")
                accentColor -> encoder.encodeString("accentColor")
                else -> encoder.encodeSerializableValue(AnyColorBox.Serializer, value.provider)
            }

        override fun deserialize(decoder: Decoder): Color {
            val input = decoder as JsonDecoder
            val tree = input.decodeJsonElement()
            return if (tree is JsonObject) Color(input.json.decodeFromJsonElement(AnyColorBox.Serializer, tree))
            else when (val provider = tree.jsonPrimitive?.content) {
                "clear" -> clear
                "black" -> black
                "white" -> white
                "gray" -> gray
                "red" -> red
                "green" -> green
                "blue" -> blue
                "orange" -> orange
                "yellow" -> yellow
                "pink" -> pink
                "purple" -> purple
                "primary" -> primary
                "secondary" -> secondary
                "accentColor" -> accentColor
                else -> error(provider)
            }
        }
    }

    enum class RGBColorSpace {
        sRGB, sRGBLinear, displayP3
    }

    companion object {
        val clear = Color(CGColor.valueOf(CGColor.TRANSPARENT))
        val black = Color(CGColor.valueOf(CGColor.BLACK))
        val white = Color(CGColor.valueOf(CGColor.WHITE))
        val gray = Color(CGColor.valueOf(CGColor.GRAY))
        val red = Color(CGColor.valueOf(CGColor.RED))
        val green = Color(CGColor.valueOf(CGColor.GREEN))
        val blue = Color(CGColor.valueOf(CGColor.BLUE))
        val orange = Color(CGColor.valueOf(1f, 0f, 0f))
        val yellow = Color(CGColor.valueOf(CGColor.YELLOW))
        val pink = Color(CGColor.valueOf(2f, 0f, 0f))
        val purple = Color(CGColor.valueOf(3f, 0f, 0f))
        val primary = Color(CGColor.valueOf(4f, 0f, 0f))
        val secondary = Color(CGColor.valueOf(5f, 0f, 0f))
        val accentColor = Color(CGColor.valueOf(6f, 0f, 0f))

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

        //: Register
        fun register() {
            PType.register<Color>()
        }
    }
}

fun Color.opacity(opacity: Double): Color =
    Color(Color.OpacityColor(this, opacity))

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
