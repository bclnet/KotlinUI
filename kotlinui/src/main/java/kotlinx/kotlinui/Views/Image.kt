package kotlinx.kotlinui

import android.content.Context
import android.widget.ImageView
import android.view.View as XView
import kotlinx.serialization.*
import kotlinx.serialization.descriptors.*
import kotlinx.serialization.encoding.*
import java.util.*
import android.graphics.drawable.Drawable as UXImage
import android.media.Image as CGImage

@Serializable(with = Image.Serializer::class)
data class Image internal constructor(
    val _provider: AnyImageBox
) : View, IAnyView, ViewBuildable {

    // region STORAGE

    internal data class Location(val system: Boolean, val bundle: ResourceBundle?)

    @Serializable
    internal abstract class AnyImageBox { //: AnyImageProviderBox
        abstract fun apply(): Image
    }

    @Serializable(with = NamedImageProvider.Serializer::class)
    internal data class NamedImageProvider(
        val label: Text?,
        val location: Location,
        val name: String,
        val decorative: Boolean,
        val backupLocation: Any?
    ) : AnyImageBox() {
        override fun apply(): Image = when {
            location.system -> Image(Init.systemName, name)
            decorative -> Image(Init.decorative, name, location.bundle)
            label != null -> Image(name, location.bundle, label)
            else -> Image(name, location.bundle)
        }

        //: Codable
        object Serializer : KSerializer<NamedImageProvider> {
            override val descriptor: SerialDescriptor =
                buildClassSerialDescriptor("NamedImageProvider") {
                    element<Text?>("label")
                    element<Boolean>("system")
                    element("bundle", ResourceBundleSerializer.descriptor)
                    element<String>("name")
                    element<Boolean>("decorative")
                }

            override fun serialize(encoder: Encoder, value: NamedImageProvider) =
                encoder.encodeStructure(descriptor) {
                    if (value.label != null) encodeSerializableElement(descriptor, 0, Text.Serializer, value.label)
                    if (value.location.system) encodeBooleanElement(descriptor, 1, value.location.system)
                    if (value.location.bundle != null) encodeSerializableElement(descriptor, 2, ResourceBundleSerializer, value.location.bundle)
                    encodeStringElement(descriptor, 3, value.name)
                    if (value.decorative) encodeBooleanElement(descriptor, 4, value.decorative)
                }

            override fun deserialize(decoder: Decoder): NamedImageProvider =
                decoder.decodeStructure(descriptor) {
                    var label: Text? = null
                    var location_system = false
                    var location_bundle: ResourceBundle? = null
                    lateinit var name: String
                    var decorative = false
                    val backupLocation: Any? = null
                    while (true) {
                        when (val index = decodeElementIndex(descriptor)) {
                            0 -> label = decodeSerializableElement(descriptor, 0, Text.Serializer)
                            1 -> location_system = decodeBooleanElement(descriptor, 1)
                            2 -> location_bundle = decodeSerializableElement(descriptor, 2, ResourceBundleSerializer)
                            3 -> name = decodeStringElement(descriptor, 3)
                            4 -> decorative = decodeBooleanElement(descriptor, 4)
                            CompositeDecoder.DECODE_DONE -> break
                            else -> error("Unexpected index: $index")
                        }
                    }
                    NamedImageProvider(label, Location(location_system, location_bundle), name, decorative, backupLocation)
                }
        }
    }

    @Serializable(with = RenderingModeProvider.Serializer::class)
    internal data class RenderingModeProvider(
        val base: Image,
        val renderingMode: TemplateRenderingMode
    ) : AnyImageBox() {
        override fun apply(): Image = base.renderingMode(renderingMode)

        //: Codable
        object Serializer : KSerializer<RenderingModeProvider> {
            override val descriptor: SerialDescriptor =
                buildClassSerialDescriptor("RenderingModeProvider") {
                    element<Image>("base")
                    element("mode", TemplateRenderingMode.Serializer.descriptor)
                }

            override fun serialize(encoder: Encoder, value: RenderingModeProvider) =
                encoder.encodeStructure(descriptor) {
                    encodeSerializableElement(descriptor, 0, Image.Serializer, value.base)
                    if (value.renderingMode != TemplateRenderingMode.original) encodeSerializableElement(descriptor, 1, TemplateRenderingMode.Serializer, value.renderingMode)
                }

            override fun deserialize(decoder: Decoder): RenderingModeProvider =
                decoder.decodeStructure(descriptor) {
                    lateinit var base: Image
                    var renderingMode = TemplateRenderingMode.original
                    while (true) {
                        when (val index = decodeElementIndex(descriptor)) {
                            0 -> base = decodeSerializableElement(descriptor, 0, Image.Serializer)
                            1 -> renderingMode = decodeSerializableElement(descriptor, 1, TemplateRenderingMode.Serializer)
                            CompositeDecoder.DECODE_DONE -> break
                            else -> error("Unexpected index: $index")
                        }
                    }
                    RenderingModeProvider(base, renderingMode)
                }
        }
    }

    @Serializable(with = InterpolationProvider.Serializer::class)
    internal data class InterpolationProvider(
        val base: Image,
        val interpolation: Interpolation
    ) : AnyImageBox() {
        override fun apply(): Image = base.interpolation(interpolation)

        //: Codable
        object Serializer : KSerializer<InterpolationProvider> {
            override val descriptor: SerialDescriptor =
                buildClassSerialDescriptor("InterpolationProvider") {
                    element<Image>("base")
                    element("interpolation", Interpolation.Serializer.descriptor)
                }

            override fun serialize(encoder: Encoder, value: InterpolationProvider) =
                encoder.encodeStructure(descriptor) {
                    encodeSerializableElement(descriptor, 0, Image.Serializer, value.base)
                    if (value.interpolation != Interpolation.none) encodeSerializableElement(descriptor, 1, Interpolation.Serializer, value.interpolation)
                }

            override fun deserialize(decoder: Decoder): InterpolationProvider =
                decoder.decodeStructure(descriptor) {
                    lateinit var base: Image
                    var interpolation = Interpolation.none
                    while (true) {
                        when (val index = decodeElementIndex(descriptor)) {
                            0 -> base = decodeSerializableElement(descriptor, 0, Image.Serializer)
                            1 -> interpolation = decodeSerializableElement(descriptor, 1, Interpolation.Serializer)
                            CompositeDecoder.DECODE_DONE -> break
                            else -> error("Unexpected index: $index")
                        }
                    }
                    InterpolationProvider(base, interpolation)
                }
        }
    }

    @Serializable(with = AntialiasedProvider.Serializer::class)
    internal data class AntialiasedProvider(
        val base: Image,
        val isAntialiased: Boolean
    ) : AnyImageBox() {
        override fun apply(): Image = base.antialiased(isAntialiased)

        //: Codable
        object Serializer : KSerializer<AntialiasedProvider> {
            override val descriptor: SerialDescriptor =
                buildClassSerialDescriptor("AntialiasedProvider") {
                    element<Image>("base")
                    element<Boolean>("antialiased")
                }

            override fun serialize(encoder: Encoder, value: AntialiasedProvider) =
                encoder.encodeStructure(descriptor) {
                    encodeSerializableElement(descriptor, 0, Image.Serializer, value.base)
                    if (!value.isAntialiased) encodeBooleanElement(descriptor, 1, value.isAntialiased)
                }

            override fun deserialize(decoder: Decoder): AntialiasedProvider =
                decoder.decodeStructure(descriptor) {
                    lateinit var base: Image
                    var isAntialiased = true
                    while (true) {
                        when (val index = decodeElementIndex(descriptor)) {
                            0 -> base = decodeSerializableElement(descriptor, 0, Image.Serializer)
                            1 -> isAntialiased = decodeBooleanElement(descriptor, 1)
                            CompositeDecoder.DECODE_DONE -> break
                            else -> error("Unexpected index: $index")
                        }
                    }
                    AntialiasedProvider(base, isAntialiased)
                }
        }
    }

    @Serializable(with = CGImageProvider.Serializer::class)
    internal data class CGImageProvider(
        val image: CGImage,
        val label: Text?,
        val decorative: Boolean,
        val scale: Float,
        val orientation: Orientation
    ) : AnyImageBox() {
        override fun apply(): Image =
            if (decorative) Image(image, scale, orientation)
            else Image(image, scale, orientation, label!!)

        //: Codable
        object Serializer : KSerializer<CGImageProvider> {
            override val descriptor: SerialDescriptor =
                buildClassSerialDescriptor("CGImageProvider") {
                    element("image", CGImageSerializer.descriptor)
                    element<Text>("label")
                    element<Boolean>("decorative")
                    element<Float>("scale")
                    element("orientation", Orientation.Serializer.descriptor)
                }

            override fun serialize(encoder: Encoder, value: CGImageProvider) =
                encoder.encodeStructure(descriptor) {
                    encodeSerializableElement(descriptor, 0, CGImageSerializer, value.image)
                    if (value.label != null) encodeSerializableElement(descriptor, 1, Text.Serializer, value.label)
                    if (value.decorative) encodeBooleanElement(descriptor, 2, value.decorative)
                    if (value.scale != 0f) encodeFloatElement(descriptor, 3, value.scale)
                    if (value.orientation != Orientation.up) encodeSerializableElement(descriptor, 4, Orientation.Serializer, value.orientation)
                }

            override fun deserialize(decoder: Decoder): CGImageProvider =
                decoder.decodeStructure(descriptor) {
                    lateinit var image: CGImage
                    var label: Text? = null
                    var decorative = false
                    var scale = 0f
                    var orientation = Orientation.up
                    while (true) {
                        when (val index = decodeElementIndex(descriptor)) {
                            0 -> image = decodeSerializableElement(descriptor, 0, CGImageSerializer)
                            1 -> label = decodeSerializableElement(descriptor, 1, Text.Serializer)
                            2 -> decorative = decodeBooleanElement(descriptor, 2)
                            3 -> scale = decodeFloatElement(descriptor, 3)
                            4 -> orientation = decodeSerializableElement(descriptor, 4, Orientation.Serializer)
                            CompositeDecoder.DECODE_DONE -> break
                            else -> error("Unexpected index: $index")
                        }
                    }
                    CGImageProvider(image, label, decorative, scale, orientation)
                }
        }
    }

    @Serializable(with = PlatformProvider.Serializer::class)
    internal data class PlatformProvider(
        val image: UXImage
    ) : AnyImageBox() {
        override fun apply(): Image = Image(image)

        //: Codable
        object Serializer : KSerializer<PlatformProvider> {
            override val descriptor: SerialDescriptor =
                buildClassSerialDescriptor("PlatformProvider") {
                    element("image", UXImageSerializer.descriptor)
                }

            override fun serialize(encoder: Encoder, value: PlatformProvider) =
                encoder.encodeStructure(descriptor) {
                    encodeSerializableElement(descriptor, 0, UXImageSerializer, value.image)
                }

            override fun deserialize(decoder: Decoder): PlatformProvider =
                decoder.decodeStructure(descriptor) {
                    lateinit var image: UXImage
                    while (true) {
                        when (val index = decodeElementIndex(descriptor)) {
                            0 -> image = decodeSerializableElement(descriptor, 0, UXImageSerializer)
                            CompositeDecoder.DECODE_DONE -> break
                            else -> error("Unexpected index: $index")
                        }
                    }
                    PlatformProvider(image)
                }
        }
    }

    @Serializable(with = ResizableProvider.Serializer::class)
    internal data class ResizableProvider(
        val base: Image,
        val capInsets: EdgeInsets,
        val resizingMode: ResizingMode
    ) : AnyImageBox() {
        override fun apply(): Image = base.resizable(capInsets, resizingMode)

        //: Codable
        object Serializer : KSerializer<ResizableProvider> {
            override val descriptor: SerialDescriptor = buildClassSerialDescriptor("ResizableProvider") {
                element<Image>("base")
                element<EdgeInsets>("capInsets")
                element<ResizingMode>("resizingMode")
            }

            override fun serialize(encoder: Encoder, value: ResizableProvider) =
                encoder.encodeStructure(descriptor) {
                    encodeSerializableElement(descriptor, 0, Image.Serializer, value.base)
                    if (!value.capInsets.isEmpty) encodeSerializableElement(descriptor, 1, EdgeInsets.Serializer, value.capInsets)
                    if (value.resizingMode != ResizingMode.stretch) encodeSerializableElement(descriptor, 2, ResizingMode.Serializer, value.resizingMode)
                }

            override fun deserialize(decoder: Decoder): ResizableProvider =
                decoder.decodeStructure(descriptor) {
                    lateinit var base: Image
                    var capInsets = EdgeInsets()
                    var resizingMode = ResizingMode.stretch
                    while (true) {
                        when (val index = decodeElementIndex(descriptor)) {
                            0 -> base = decodeSerializableElement(descriptor, 0, Image.Serializer)
                            1 -> capInsets = decodeSerializableElement(descriptor, 1, EdgeInsets.Serializer)
                            2 -> resizingMode = decodeSerializableElement(descriptor, 2, ResizingMode.Serializer)
                            CompositeDecoder.DECODE_DONE -> break
                            else -> error("Unexpected index: $index")
                        }
                    }
                    ResizableProvider(base, capInsets, resizingMode)
                }
        }
    }

    // endregion

    enum class Init { decorative, systemName }

    // constructor(decorative: String, bundle: ResourceBundle? = null)
    // constructor(systemName: String)
    constructor(init: Init, string: String, bundle: ResourceBundle? = null) : this(
        when (init) {
            Init.decorative -> NamedImageProvider(null, Location(false, null), string, true, null)
            Init.systemName -> NamedImageProvider(null, Location(true, null), string, false, null)
        }
    )

    constructor(name: String, bundle: ResourceBundle? = null) : this(NamedImageProvider(null, Location(false, bundle), name, false, null))
    constructor(name: String, bundle: ResourceBundle? = null, label: Text) : this(NamedImageProvider(label, Location(false, bundle), name, false, null))
    constructor(image: UXImage) : this(PlatformProvider(image))
    constructor(image: CGImage, scale: Float, orientation: Orientation, label: Text) : this(CGImageProvider(image, label, false, scale, orientation))
    constructor(image: CGImage, scale: Float, orientation: Orientation) : this(CGImageProvider(image, null, false, scale, orientation))

    override val body: Never
        get() = error("Never")

    override val anyView: AnyView
        get() = AnyView(this)

    //: Codable
    internal object Serializer : KSerializer<Image> {
        override val descriptor: SerialDescriptor =
            buildClassSerialDescriptor("Image") {
                element<AnyImageBox>("named")
                element<AnyImageBox>("renderingMode")
                element<AnyImageBox>("interpolation")
                element<AnyImageBox>("antialiased")
                element<AnyImageBox>("cgimage")
                element<AnyImageBox>("platform")
                element<AnyImageBox>("resizable")
            }

        override fun serialize(encoder: Encoder, value: Image) =
            encoder.encodeStructure(descriptor) {
                when (val provider = value._provider) {
                    is NamedImageProvider -> encodeSerializableElement(descriptor, 0, NamedImageProvider.Serializer, provider)
                    is RenderingModeProvider -> encodeSerializableElement(descriptor, 1, RenderingModeProvider.Serializer, provider)
                    is InterpolationProvider -> encodeSerializableElement(descriptor, 2, InterpolationProvider.Serializer, provider)
                    is AntialiasedProvider -> encodeSerializableElement(descriptor, 3, AntialiasedProvider.Serializer, provider)
                    is CGImageProvider -> encodeSerializableElement(descriptor, 4, CGImageProvider.Serializer, provider)
                    is PlatformProvider -> encodeSerializableElement(descriptor, 5, PlatformProvider.Serializer, provider)
                    is ResizableProvider -> encodeSerializableElement(descriptor, 6, ResizableProvider.Serializer, provider)
                }
            }

        override fun deserialize(decoder: Decoder): Image =
            decoder.decodeStructure(descriptor) {
                lateinit var provider: AnyImageBox
                while (true) {
                    provider = when (val index = decodeElementIndex(descriptor)) {
                        0 -> decodeSerializableElement(descriptor, 0, NamedImageProvider.Serializer)
                        1 -> decodeSerializableElement(descriptor, 1, RenderingModeProvider.Serializer)
                        2 -> decodeSerializableElement(descriptor, 2, InterpolationProvider.Serializer)
                        3 -> decodeSerializableElement(descriptor, 3, AntialiasedProvider.Serializer)
                        4 -> decodeSerializableElement(descriptor, 4, CGImageProvider.Serializer)
                        5 -> decodeSerializableElement(descriptor, 5, PlatformProvider.Serializer)
                        6 -> decodeSerializableElement(descriptor, 6, ResizableProvider.Serializer)
                        CompositeDecoder.DECODE_DONE -> break
                        else -> error("Unexpected index: $index")
                    }
                }
                Image(provider)
            }
    }

    //: ViewBuildable
    override fun buildView(context: Context?): XView {
        when (_provider) {
            is NamedImageProvider -> {
            }
            is RenderingModeProvider -> {
            }
            is InterpolationProvider -> {
            }
            is AntialiasedProvider -> {
            }
            is CGImageProvider -> {
            }
            is PlatformProvider -> {
            }
            is ResizableProvider -> {
            }
            else -> error("")
        }
        val view = ImageView(context)
        return view
    }


// region ADDITIONAL TYPES

    @Serializable(with = Orientation.Serializer::class)
    enum class Orientation {
        up, upMirrored, down, downMirrored, left, leftMirrored, right, rightMirrored;

        internal object Serializer : KSerializer<Orientation> {
            override val descriptor: SerialDescriptor =
                PrimitiveSerialDescriptor("Orientation", PrimitiveKind.STRING)

            override fun serialize(encoder: Encoder, value: Orientation) {
                when (value) {
                    up -> encoder.encodeString("up")
                    upMirrored -> encoder.encodeString("upMirrored")
                    down -> encoder.encodeString("down")
                    downMirrored -> encoder.encodeString("downMirrored")
                    left -> encoder.encodeString("left")
                    leftMirrored -> encoder.encodeString("leftMirrored")
                    right -> encoder.encodeString("right")
                    rightMirrored -> encoder.encodeString("rightMirrored")
                }
            }

            override fun deserialize(decoder: Decoder): Orientation =
                when (val value = decoder.decodeString()) {
                    "up" -> up
                    "upMirrored" -> upMirrored
                    "down" -> down
                    "downMirrored" -> downMirrored
                    "left" -> left
                    "leftMirrored" -> leftMirrored
                    "right" -> right
                    "rightMirrored" -> rightMirrored
                    else -> error(value)
                }
        }
    }

    @Serializable(with = TemplateRenderingMode.Serializer::class)
    enum class TemplateRenderingMode {
        template, original;

        internal object Serializer : KSerializer<TemplateRenderingMode> {
            override val descriptor: SerialDescriptor =
                PrimitiveSerialDescriptor("TemplateRenderingMode", PrimitiveKind.STRING)

            override fun serialize(encoder: Encoder, value: TemplateRenderingMode) {
                when (value) {
                    template -> encoder.encodeString("template")
                    original -> encoder.encodeString("original")
                }
            }

            override fun deserialize(decoder: Decoder): TemplateRenderingMode =
                when (val value = decoder.decodeString()) {
                    "template" -> template
                    "original" -> original
                    else -> error(value)
                }
        }
    }

    @Serializable(with = Scale.Serializer::class)
    enum class Scale {
        small, medium, large;

        internal object Serializer : KSerializer<Scale> {
            override val descriptor: SerialDescriptor =
                PrimitiveSerialDescriptor("Scale", PrimitiveKind.STRING)

            override fun serialize(encoder: Encoder, value: Scale) {
                when (value) {
                    small -> encoder.encodeString("small")
                    medium -> encoder.encodeString("medium")
                    large -> encoder.encodeString("large")
                }
            }

            override fun deserialize(decoder: Decoder): Scale =
                when (val value = decoder.decodeString()) {
                    "small" -> small
                    "medium" -> medium
                    "large" -> large
                    else -> error(value)
                }
        }
    }

    @Serializable(with = Interpolation.Serializer::class)
    enum class Interpolation {
        none, low, medium, high;

        internal object Serializer : KSerializer<Interpolation> {
            override val descriptor: SerialDescriptor =
                PrimitiveSerialDescriptor("Interpolation", PrimitiveKind.STRING)

            override fun serialize(encoder: Encoder, value: Interpolation) {
                when (value) {
                    none -> encoder.encodeString("none")
                    low -> encoder.encodeString("low")
                    medium -> encoder.encodeString("medium")
                    high -> encoder.encodeString("high")
                }
            }

            override fun deserialize(decoder: Decoder): Interpolation =
                when (val value = decoder.decodeString()) {
                    "none" -> none
                    "low" -> low
                    "medium" -> medium
                    "high" -> high
                    else -> error(value)
                }
        }
    }

    @Serializable(with = ResizingMode.Serializer::class)
    enum class ResizingMode {
        tile, stretch;

        internal object Serializer : KSerializer<ResizingMode> {
            override val descriptor: SerialDescriptor =
                PrimitiveSerialDescriptor("ResizingMode", PrimitiveKind.STRING)

            override fun serialize(encoder: Encoder, value: ResizingMode) {
                when (value) {
                    tile -> encoder.encodeString("tile")
                    stretch -> encoder.encodeString("stretch")
                }
            }

            override fun deserialize(decoder: Decoder): ResizingMode =
                when (val value = decoder.decodeString()) {
                    "tile" -> tile
                    "stretch" -> stretch
                    else -> error(value)
                }
        }
    }

// endregion
}

fun Image.resizable(capInsets: EdgeInsets, resizingMode: Image.ResizingMode): Image =
    Image(Image.ResizableProvider(this, capInsets, resizingMode))

fun Image.renderingMode(renderingMode: Image.TemplateRenderingMode?): Image =
    Image(Image.RenderingModeProvider(this, renderingMode ?: Image.TemplateRenderingMode.original))

fun Image.interpolation(interpolation: Image.Interpolation): Image =
    Image(Image.InterpolationProvider(this, interpolation))

fun Image.antialiased(antialiased: Boolean): Image =
    Image(Image.AntialiasedProvider(this, antialiased))