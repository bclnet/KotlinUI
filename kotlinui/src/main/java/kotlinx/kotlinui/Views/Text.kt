package kotlinx.kotlinui

import android.content.Context
import android.graphics.Paint
import android.graphics.Typeface
import android.view.View as XView
import android.icu.util.DateInterval
import android.text.format.DateUtils
import android.widget.TextView
import kotlinx.ptype.PType
import kotlinx.serialization.*
import kotlinx.serialization.descriptors.*
import kotlinx.serialization.encoding.*
import java.util.*

@Serializable(with = Text.Serializer::class)
data class Text internal constructor(
    val _storage: Storage,
    val _modifiers: Array<Modifier>
) : View, IAnyView, ViewBuildable {
    override fun equals(other: Any?): Boolean = other is Text && _storage == other._storage && _modifiers contentEquals other._modifiers
    override fun hashCode(): Int {
        var result = _storage.hashCode()
        result = 31 * result + _modifiers.contentHashCode()
        return result
    }

    // region ANYTEXTSTORAGE

    @Serializable
    internal sealed class Storage {
        data class text(val string: String) : Storage()
        data class verbatim(val verbatim: String) : Storage()
        data class anyTextStorage(val anyTextStorage: AnyTextStorage) : Storage()
    }

    @Serializable
    internal abstract class AnyTextStorage {
        abstract fun apply(): Text
    }

    @Serializable(with = AttachmentTextStorage.Serializer::class)
    internal data class AttachmentTextStorage(
        val image: Image
    ) : AnyTextStorage() {
        override fun apply(): Text = Text(image)

        //: Codable
        object Serializer : KSerializer<AttachmentTextStorage> {
            override val descriptor: SerialDescriptor =
                buildClassSerialDescriptor(":Text.AttachmentTextStorage") {
                    element<Image>("image")
                }

            override fun serialize(encoder: Encoder, value: AttachmentTextStorage) =
                encoder.encodeStructure(descriptor) {
                    encodeSerializableElement(descriptor, 0, Image.Serializer, value.image)
                }

            override fun deserialize(decoder: Decoder): AttachmentTextStorage =
                decoder.decodeStructure(descriptor) {
                    lateinit var image: Image
                    while (true) {
                        when (val index = decodeElementIndex(descriptor)) {
                            0 -> image = decodeSerializableElement(descriptor, 0, Image.Serializer)
                            CompositeDecoder.DECODE_DONE -> break
                            else -> error("Unexpected index: $index")
                        }
                    }
                    AttachmentTextStorage(image)
                }
        }
    }

    @Serializable(with = LocalizedTextStorage.Serializer::class)
    internal data class LocalizedTextStorage(
        val key: LocalizedStringKey,
        val table: String?,
        val bundle: ResourceBundle?
    ) : AnyTextStorage() {
        override fun apply(): Text = Text(key, table, bundle, null)

        //: Codable
        object Serializer : KSerializer<LocalizedTextStorage> {
            override val descriptor: SerialDescriptor =
                buildClassSerialDescriptor(":Text.LocalizedTextStorage") {
                    element<LocalizedStringKey>("text")
                    element<String?>("table")
                    element("bundle", ResourceBundleSerializer.descriptor)
                }

            override fun serialize(encoder: Encoder, value: LocalizedTextStorage) =
                encoder.encodeStructure(descriptor) {
                    encodeSerializableElement(descriptor, 0, LocalizedStringKey.Serializer, value.key)
                    if (value.table != null) encodeStringElement(descriptor, 1, value.table)
                    if (value.bundle != null) encodeSerializableElement(descriptor, 2, ResourceBundleSerializer, value.bundle)
                }

            override fun deserialize(decoder: Decoder): LocalizedTextStorage =
                decoder.decodeStructure(descriptor) {
                    lateinit var key: LocalizedStringKey
                    var table: String? = null
                    var bundle: ResourceBundle? = null
                    while (true) {
                        when (val index = decodeElementIndex(descriptor)) {
                            0 -> key = decodeSerializableElement(descriptor, 0, LocalizedStringKey.Serializer)
                            1 -> table = decodeStringElement(descriptor, 1)
                            2 -> bundle = decodeSerializableElement(descriptor, 2, ResourceBundleSerializer)
                            CompositeDecoder.DECODE_DONE -> break
                            else -> error("Unexpected index: $index")
                        }
                    }
                    LocalizedTextStorage(key, table, bundle)
                }
        }
    }

    @Serializable(with = FormatterTextStorage.Serializer::class)
    internal data class FormatterTextStorage(
        val object_: Any,
        val formatter: Formatter,
    ) : AnyTextStorage() {
        override fun apply(): Text = error("Not Implemented")

        //: Codable
        object Serializer : KSerializer<FormatterTextStorage> {
            override val descriptor: SerialDescriptor = buildClassSerialDescriptor("FormatterTextStorage")
            override fun serialize(encoder: Encoder, value: FormatterTextStorage) = error("Not Implemented")
            override fun deserialize(decoder: Decoder): FormatterTextStorage = error("Not Implemented")
        }
    }

    @Serializable(with = DateTextStorage.Serializer::class)
    internal data class DateTextStorage(
        val storage: Storage
    ) : AnyTextStorage() {
        override fun apply(): Text =
            when (storage) {
                is Storage.absolute -> Text(storage.date, storage.style)
                is Storage.interval -> Text(storage.interval)
            }

        //: Codable
        object Serializer : KSerializer<DateTextStorage> {
            override val descriptor: SerialDescriptor = Storage.Serializer.descriptor
            override fun serialize(encoder: Encoder, value: DateTextStorage) = Storage.Serializer.serialize(encoder, value.storage)
            override fun deserialize(decoder: Decoder): DateTextStorage = DateTextStorage(Storage.Serializer.deserialize(decoder))
        }

        @Serializable(with = Storage.Serializer::class)
        sealed class Storage {
            data class absolute(var date: Date, var style: DateStyle) : Storage()
            data class interval(var interval: DateInterval) : Storage()

            //: Codable
            object Serializer : KSerializer<Storage> {
                override val descriptor: SerialDescriptor =
                    buildClassSerialDescriptor(":Text.Storage") {
                        element<String>("value")
                        element("date", DateSerializer.descriptor)
                        element<DateStyle>("style")
                        element("interval", DateIntervalSerializer.descriptor)
                    }

                override fun serialize(encoder: Encoder, value: Storage) =
                    encoder.encodeStructure(descriptor) {
                        when (value) {
                            is absolute -> {
                                encodeSerializableElement(descriptor, 1, DateSerializer, value.date)
                                encodeSerializableElement(descriptor, 2, DateStyle.Serializer, value.style)
                                encodeStringElement(descriptor, 0, "absolute")
                            }
                            is interval -> {
                                encodeSerializableElement(descriptor, 3, DateIntervalSerializer, value.interval)
                                encodeStringElement(descriptor, 0, "interval")
                            }
                        }
                    }

                override fun deserialize(decoder: Decoder): Storage =
                    decoder.decodeStructure(descriptor) {
                        lateinit var value: String
                        lateinit var date: Date
                        lateinit var style: DateStyle
                        lateinit var interval: DateInterval
                        while (true) {
                            when (val index = decodeElementIndex(descriptor)) {
                                0 -> value = decodeStringElement(descriptor, 0)
                                1 -> date = decodeSerializableElement(descriptor, 1, DateSerializer)
                                2 -> style = decodeSerializableElement(descriptor, 2, DateStyle.Serializer)
                                3 -> interval = decodeSerializableElement(descriptor, 3, DateIntervalSerializer)
                                CompositeDecoder.DECODE_DONE -> break
                                else -> error("Unexpected index: $index")
                            }
                        }
                        return when (value) {
                            "absolute" -> absolute(date, style)
                            "interval" -> interval(interval)
                            else -> error(value)
                        }
                    }
            }
        }
    }

    // endregion

    // region ANYTEXTMODIFIER

    internal data class LineStyle(val active: Boolean, val color: Color?)

    @Serializable
    internal sealed class Modifier {
        data class color(val color: Color?) : Modifier()
        data class font(val font: Font?) : Modifier()
        class italic : Modifier()
        data class weight(val weight: Font.Weight?) : Modifier()
        data class kerning(val kerning: Float) : Modifier()
        data class tracking(val tracking: Float) : Modifier()
        data class baseline(val baseline: Float) : Modifier()
        data class anyTextModifier(val anyTextModifier: AnyTextModifier) : Modifier()

        fun apply(text: Text): Text =
            when (this) {
                is color -> text.foregroundColor(color)
                is font -> text.font(font)
                is italic -> text.italic()
                is weight -> text.fontWeight(weight)
                is kerning -> text.kerning(kerning)
                is tracking -> text.tracking(tracking)
                is baseline -> text.baselineOffset(baseline)
                is anyTextModifier -> (anyTextModifier as AnyTextModifier).apply(text)
            }

//        override fun equals(other: Any?): Boolean {
//            if (other !is Modifier) return false
//            return when (this) {
//                is color -> other is color && color!! == other.color!!
//                is font -> other is font && font!! == other.font!!
//                is italic -> other is italic
//                is weight -> other is weight && weight!! == other.weight!!
//                is kerning -> other is kerning && kerning == other.kerning
//                is tracking -> other is tracking && tracking == other.tracking
//                is baseline -> other is baseline && baseline == other.baseline
//                is anyTextModifier -> other is anyTextModifier && anyTextModifier == other.anyTextModifier
//            }
//        }

//        override fun hashCode(): Int =
//            when (this) {
//                is color -> color.hashCode()
//                is font -> font.hashCode()
//                is italic -> 0
//                is weight -> weight.hashCode()
//                is kerning -> kerning.hashCode()
//                is tracking -> tracking.hashCode()
//                is baseline -> baseline.hashCode()
//                is anyTextModifier -> anyTextModifier.hashCode()
//            }

        //: Codable
        object Serializer : KSerializer<Modifier> {
            override val descriptor: SerialDescriptor =
                buildClassSerialDescriptor("Modifier") {
                    element<Color?>("color")
                    element<Font?>("font")
                    element<String>("italic")
                    element<Font.Weight?>("weight")
                    element<Float>("kerning")
                    element<Float>("tracking")
                    element<Float>("baseline")
                    element<BoldTextModifier>("bold")
                    element<StrikethroughTextModifier>("strikethrough")
                    element<UnderlineTextModifier>("underline")
                }

            override fun serialize(encoder: Encoder, value: Modifier) =
                encoder.encodeStructure(descriptor) {
                    when (value) {
                        is color -> encodeSerializableElement(descriptor, 0, Color.Serializer, value.color!!)
                        is font -> encodeSerializableElement(descriptor, 1, Font.Serializer, value.font!!)
                        is italic -> encodeStringElement(descriptor, 2, "1")
                        is weight -> encodeSerializableElement(descriptor, 3, Font.Weight.Serializer, value.weight!!)
                        is kerning -> encodeFloatElement(descriptor, 4, value.kerning)
                        is tracking -> encodeFloatElement(descriptor, 5, value.tracking)
                        is baseline -> encodeFloatElement(descriptor, 6, value.baseline)
                        is anyTextModifier -> when (value.anyTextModifier) {
                            is BoldTextModifier -> encodeSerializableElement(descriptor, 7, BoldTextModifier.Serializer, value.anyTextModifier)
                            is StrikethroughTextModifier -> encodeSerializableElement(descriptor, 8, StrikethroughTextModifier.Serializer, value.anyTextModifier)
                            is UnderlineTextModifier -> encodeSerializableElement(descriptor, 9, UnderlineTextModifier.Serializer, value.anyTextModifier)
                            else -> error("$value.anyTextModifier")
                        }
                    }
                }

            override fun deserialize(decoder: Decoder): Modifier =
                decoder.decodeStructure(descriptor) {
                    lateinit var modifier: Modifier
                    while (true) {
                        when (val index = decodeElementIndex(descriptor)) {
                            0 -> modifier = color(decodeSerializableElement(descriptor, 0, Color.Serializer))
                            1 -> modifier = font(decodeSerializableElement(descriptor, 1, Font.Serializer))
                            2 -> modifier = italic()
                            3 -> modifier = weight(decodeSerializableElement(descriptor, 3, Font.Weight.Serializer))
                            4 -> modifier = kerning(decodeFloatElement(descriptor, 4))
                            5 -> modifier = tracking(decodeFloatElement(descriptor, 5))
                            6 -> modifier = baseline(decodeFloatElement(descriptor, 6))
                            7 -> modifier = anyTextModifier(decodeSerializableElement(descriptor, 7, BoldTextModifier.Serializer))
                            8 -> modifier = anyTextModifier(decodeSerializableElement(descriptor, 8, StrikethroughTextModifier.Serializer))
                            9 -> modifier = anyTextModifier(decodeSerializableElement(descriptor, 9, UnderlineTextModifier.Serializer))
                            CompositeDecoder.DECODE_DONE -> break
                            else -> error("Unexpected index: $index")
                        }
                    }
                    modifier
                }
        }
    }

    internal abstract class AnyTextModifier {
        abstract fun apply(text: Text): Text
    }

    @Serializable(with = BoldTextModifier.Serializer::class)
    internal class BoldTextModifier : AnyTextModifier() {
        override fun apply(text: Text): Text = text.bold()

        //: Codable
        object Serializer : KSerializer<BoldTextModifier> {
            override val descriptor: SerialDescriptor =
                buildClassSerialDescriptor("BoldTextModifier") {
                }

            override fun serialize(encoder: Encoder, value: BoldTextModifier) =
                encoder.encodeStructure(descriptor) {
                }

            override fun deserialize(decoder: Decoder): BoldTextModifier =
                decoder.decodeStructure(descriptor) {
                    BoldTextModifier()
                }
        }
    }

    @Serializable(with = StrikethroughTextModifier.Serializer::class)
    internal data class StrikethroughTextModifier(
        val lineStyle: LineStyle?
    ) : AnyTextModifier() {
        override fun apply(text: Text): Text = text.strikethrough(lineStyle?.active ?: true, lineStyle?.color)

        //: Codable
        object Serializer : KSerializer<StrikethroughTextModifier> {
            override val descriptor: SerialDescriptor =
                buildClassSerialDescriptor("StrikethroughTextModifier") {
                    element<Boolean>("active")
                    element<Color>("color")
                }

            override fun serialize(encoder: Encoder, value: StrikethroughTextModifier) =
                encoder.encodeStructure(descriptor) {
                    val lineStyle = value.lineStyle ?: return
                    if (lineStyle.active) encodeBooleanElement(descriptor, 0, true)
                    if (lineStyle.color != null) encodeSerializableElement(descriptor, 1, Color.Serializer, lineStyle.color)
                }

            override fun deserialize(decoder: Decoder): StrikethroughTextModifier =
                decoder.decodeStructure(descriptor) {
                    var active = true
                    var color: Color? = null
                    while (true) {
                        when (val index = decodeElementIndex(descriptor)) {
                            0 -> active = decodeBooleanElement(descriptor, 0)
                            1 -> color = decodeSerializableElement(descriptor, 1, Color.Serializer)
                            CompositeDecoder.DECODE_DONE -> break
                            else -> error("Unexpected index: $index")
                        }
                    }
                    StrikethroughTextModifier(LineStyle(active, color))
                }
        }
    }

    @Serializable(with = UnderlineTextModifier.Serializer::class)
    internal data class UnderlineTextModifier(
        val lineStyle: LineStyle?
    ) : AnyTextModifier() {
        override fun apply(text: Text): Text = text.underline(lineStyle?.active ?: true, lineStyle?.color)

        //: Codable
        object Serializer : KSerializer<UnderlineTextModifier> {
            override val descriptor: SerialDescriptor =
                buildClassSerialDescriptor("UnderlineTextModifier") {
                    element<Boolean>("active")
                    element<Color>("color")
                }

            override fun serialize(encoder: Encoder, value: UnderlineTextModifier) =
                encoder.encodeStructure(descriptor) {
                    val lineStyle = value.lineStyle ?: return
                    if (lineStyle.active) encodeBooleanElement(descriptor, 0, true)
                    if (lineStyle.color != null) encodeSerializableElement(descriptor, 1, Color.Serializer, lineStyle.color)
                }

            override fun deserialize(decoder: Decoder): UnderlineTextModifier =
                decoder.decodeStructure(descriptor) {
                    var active = true
                    var color: Color? = null
                    while (true) {
                        when (val index = decodeElementIndex(descriptor)) {
                            0 -> active = decodeBooleanElement(descriptor, 0)
                            1 -> color = decodeSerializableElement(descriptor, 1, Color.Serializer)
                            CompositeDecoder.DECODE_DONE -> break
                            else -> error("Unexpected index: $index")
                        }
                    }
                    UnderlineTextModifier(LineStyle(active, color))
                }
        }
    }

    // endregion

    enum class Init { string, verbatim }

    constructor(init: Init, string: String) : this(
        when (init) {
            Init.string -> Storage.text(string)
            Init.verbatim -> Storage.verbatim(string)
        }, arrayOf()
    )

    constructor(
        key: LocalizedStringKey,
        tableName: String? = null,
        bundle: ResourceBundle? = null,
        comment: String? = null
    ) : this(Storage.anyTextStorage(LocalizedTextStorage(key, tableName, bundle)), arrayOf())

    constructor(string: String) : this(Storage.text(string), arrayOf())
    constructor(image: Image) : this(Storage.anyTextStorage(AttachmentTextStorage(image)), arrayOf())
    constructor(range: ClosedRange<Date>) : this(Storage.anyTextStorage(DateTextStorage(DateTextStorage.Storage.interval(DateInterval(range.start.time, range.endInclusive.time)))), arrayOf())
    constructor(interval: DateInterval) : this(Storage.anyTextStorage(DateTextStorage(DateTextStorage.Storage.interval(interval))), arrayOf())
    constructor(date: Date, style: DateStyle) : this(Storage.anyTextStorage(DateTextStorage(DateTextStorage.Storage.absolute(date, style))), arrayOf())

    override val body: Never
        get() = error("Never")

    override val anyView: AnyView
        get() = AnyView(this)

    //: Codable
    internal object Serializer : KSerializer<Text> {
        override val descriptor: SerialDescriptor =
            buildClassSerialDescriptor(":Text") {
                element<String>("text")
                element<String>("verbatim")
                element<AnyTextStorage>("local")
                element<AnyTextStorage>("attach")
                element<AnyTextStorage>("format")
                element<AnyTextStorage>("date")
                element<Array<Modifier>>("modifiers")
            }

        override fun serialize(encoder: Encoder, value: Text) =
            encoder.encodeStructure(descriptor) {
                when (val storage = value._storage) {
                    is Storage.text -> encodeStringElement(descriptor, 0, storage.string)
                    is Storage.verbatim -> encodeStringElement(descriptor, 1, storage.verbatim)
                    is Storage.anyTextStorage -> when (storage.anyTextStorage) {
                        is LocalizedTextStorage -> encodeSerializableElement(descriptor, 2, LocalizedTextStorage.Serializer, storage.anyTextStorage)
                        is AttachmentTextStorage -> encodeSerializableElement(descriptor, 3, AttachmentTextStorage.Serializer, storage.anyTextStorage)
                        is FormatterTextStorage -> encodeSerializableElement(descriptor, 4, FormatterTextStorage.Serializer, storage.anyTextStorage)
                        is DateTextStorage -> encodeSerializableElement(descriptor, 5, DateTextStorage.Serializer, storage.anyTextStorage)
                        else -> error("$storage.anyTextStorage")
                    }
                }
                val modifiers = value._modifiers
                if (!modifiers.isEmpty())
                    encodeSerializableElement(descriptor, 6, serializer<Array<Modifier>>(), modifiers)
            }

        override fun deserialize(decoder: Decoder): Text =
            decoder.decodeStructure(descriptor) {
                lateinit var storage: Storage
                var modifiers: Array<Modifier>? = null
                while (true) {
                    when (val index = decodeElementIndex(descriptor)) {
                        0 -> storage = Storage.text(decodeStringElement(descriptor, 0))
                        1 -> storage = Storage.verbatim(decodeStringElement(descriptor, 1))
                        2 -> storage = Storage.anyTextStorage(decodeSerializableElement(descriptor, 2, LocalizedTextStorage.Serializer))
                        3 -> storage = Storage.anyTextStorage(decodeSerializableElement(descriptor, 3, AttachmentTextStorage.Serializer))
                        4 -> storage = Storage.anyTextStorage(decodeSerializableElement(descriptor, 4, FormatterTextStorage.Serializer))
                        5 -> storage = Storage.anyTextStorage(decodeSerializableElement(descriptor, 5, DateTextStorage.Serializer))
                        6 -> modifiers = decodeSerializableElement(descriptor, 6, serializer<Array<Modifier>>())
                        CompositeDecoder.DECODE_DONE -> break
                        else -> error("Unexpected index: $index")
                    }
                }
                Text(storage, modifiers ?: arrayOf())
            }
    }

    //: ViewBuildable
    override fun buildView(context: Context?): XView {
        val view = TextView(context)
        var font = EnvironmentValues.font
        when (_storage) {
            is Storage.text -> view.text = _storage.string
            is Storage.verbatim -> view.text = _storage.verbatim
            is Storage.anyTextStorage -> {
                when (val anyStorage = _storage.anyTextStorage) {
                    is AttachmentTextStorage -> error("Not Implemented")
                    is LocalizedTextStorage -> error("Not Implemented")
                    is FormatterTextStorage -> error("Not Implemented")
                    is DateTextStorage -> {
                        when (val storage = anyStorage.storage) {
                            is DateTextStorage.Storage.absolute -> when (storage.style) {
                                DateStyle.date -> DateUtils.formatDateTime(context, storage.date.time, 0)
                                DateStyle.offset -> DateUtils.getRelativeTimeSpanString(System.currentTimeMillis(), storage.date.time, 0L, DateUtils.FORMAT_ABBREV_ALL)
                                DateStyle.relative -> DateUtils.getRelativeTimeSpanString(storage.date.time, System.currentTimeMillis(), 0L, DateUtils.FORMAT_ABBREV_ALL)
                                DateStyle.time -> DateUtils.formatDateTime(context, storage.date.time, DateUtils.FORMAT_SHOW_TIME)
                                DateStyle.timer -> DateUtils.formatElapsedTime(storage.date.time)
                            }
                            is DateTextStorage.Storage.interval -> DateUtils.formatDateRange(context, storage.interval.fromDate, storage.interval.toDate, DateUtils.FORMAT_ABBREV_TIME)
                        }
                    }
                    else -> error("")
                }
            }
        }
        var typefaceWeight = 0f
        var typefaceStyle = 0
        _modifiers.forEach {
            when (it) {
                is Modifier.color -> view.setTextColor(0)
                is Modifier.font -> if (it.font != null) font = it.font
                is Modifier.italic -> typefaceStyle += Typeface.ITALIC
                is Modifier.weight -> if (it.weight != null) typefaceWeight = it.weight.value
                is Modifier.kerning -> ""
                is Modifier.tracking -> ""
                is Modifier.baseline -> ""
                is Modifier.anyTextModifier ->
                    when (val anyModifier = it.anyTextModifier) {
                        is BoldTextModifier -> typefaceStyle += Typeface.BOLD
                        is StrikethroughTextModifier -> view.paintFlags += Paint.STRIKE_THRU_TEXT_FLAG
                        is UnderlineTextModifier -> view.paintFlags += Paint.UNDERLINE_TEXT_FLAG
                    }
            }
        }
        view.setTypeface(view.typeface, typefaceStyle)
        return view
    }


    companion object {
        //: Register
        fun register() {
            PType.register<Text>()
            PType.register<TruncationMode>(primitiveSerial = true)
        }
    }

    // region ADDITIONALTYPES

    @Serializable(with = DateStyle.Serializer::class)
    data class DateStyle private constructor(var id: Int) {
        companion object {
            var date = DateStyle(0)
            var offset = DateStyle(1)
            var relative = DateStyle(2)
            var time = DateStyle(3)
            var timer = DateStyle(4)
        }

        internal object Serializer : KSerializer<DateStyle> {
            override val descriptor: SerialDescriptor =
                PrimitiveSerialDescriptor(":Text.DateStyle", PrimitiveKind.STRING)

            override fun serialize(encoder: Encoder, value: DateStyle) =
                encoder.encodeString(
                    when (value) {
                        date -> "date"
                        offset -> "offset"
                        relative -> "relative"
                        time -> "time"
                        timer -> "timer"
                        else -> error("$value")
                    }
                )

            override fun deserialize(decoder: Decoder): DateStyle =
                when (val value = decoder.decodeString()) {
                    "date" -> date
                    "offset" -> offset
                    "relative" -> relative
                    "bottom" -> date
                    "time" -> time
                    "timer" -> timer
                    else -> error(value)
                }
        }
    }

    @Serializable(with = TruncationMode.Serializer::class)
    enum class TruncationMode {
        head, tail, middle;

        internal object Serializer : KSerializer<TruncationMode> {
            override val descriptor: SerialDescriptor =
                PrimitiveSerialDescriptor(":Text.TruncationMode", PrimitiveKind.STRING)

            override fun serialize(encoder: Encoder, value: TruncationMode) =
                encoder.encodeString(
                    when (value) {
                        head -> "head"
                        tail -> "tail"
                        middle -> "middle"
                    }
                )

            override fun deserialize(decoder: Decoder): TruncationMode =
                when (val value = decoder.decodeString()) {
                    "head" -> head
                    "tail" -> tail
                    "middle" -> middle
                    else -> error(value)
                }
        }
    }

    @Serializable(with = Case.Serializer::class)
    enum class Case {
        uppercase, lowercase;

        internal object Serializer : KSerializer<Case> {
            override val descriptor: SerialDescriptor =
                PrimitiveSerialDescriptor(":Text.Case", PrimitiveKind.STRING)

            override fun serialize(encoder: Encoder, value: Case) =
                encoder.encodeString(
                    when (value) {
                        uppercase -> "uppercase"
                        lowercase -> "lowercase"
                    }
                )

            override fun deserialize(decoder: Decoder): Case =
                when (val value = decoder.decodeString()) {
                    "uppercase" -> uppercase
                    "lowercase" -> lowercase
                    else -> error(value)
                }
        }
    }

    // endregion
}

@Serializable(with = TextAlignment.Serializer::class)
enum class TextAlignment {
    leading, center, trailing;

    internal object Serializer : KSerializer<TextAlignment> {
        override val descriptor: SerialDescriptor =
            PrimitiveSerialDescriptor(":TextAlignment", PrimitiveKind.STRING)

        override fun serialize(encoder: Encoder, value: TextAlignment) =
            encoder.encodeString(
                when (value) {
                    leading -> "leading"
                    center -> "center"
                    trailing -> "trailing"
                }
            )

        override fun deserialize(decoder: Decoder): TextAlignment =
            when (val value = decoder.decodeString()) {
                "leading" -> leading
                "center" -> center
                "trailing" -> trailing
                else -> error(value)
            }
    }
}

fun Text.foregroundColor(color: Color?): Text =
    textWithModifier(Text.Modifier.color(color))

fun Text.font(font: Font?): Text =
    textWithModifier(Text.Modifier.font(font))

fun Text.italic(): Text =
    textWithModifier(Text.Modifier.italic())

fun Text.fontWeight(weight: Font.Weight?): Text =
    textWithModifier(Text.Modifier.weight(weight))

fun Text.kerning(kerning: Float): Text =
    textWithModifier(Text.Modifier.kerning(kerning))

fun Text.tracking(tracking: Float): Text =
    textWithModifier(Text.Modifier.tracking(tracking))

fun Text.baselineOffset(baseline: Float): Text =
    textWithModifier(Text.Modifier.baseline(baseline))

fun Text.bold(): Text =
    textWithModifier(Text.Modifier.anyTextModifier(Text.BoldTextModifier()))

fun Text.strikethrough(active: Boolean = true, color: Color? = null): Text =
    textWithModifier(Text.Modifier.anyTextModifier(Text.StrikethroughTextModifier(Text.LineStyle(active, color))))

fun Text.underline(active: Boolean = true, color: Color? = null): Text =
    textWithModifier(Text.Modifier.anyTextModifier(Text.StrikethroughTextModifier(Text.LineStyle(active, color))))

private fun Text.textWithModifier(modifier: Text.Modifier): Text {
    val modifiers: Array<Text.Modifier> = Arrays.copyOf(_modifiers, _modifiers.size + 1)
    modifiers[modifiers.size - 1] = modifier
    return Text(_storage, modifiers)
}

//internal fun Text._makeView(view: _GraphValue<Text>, inputs: _ViewInputs): _ViewOutputs = error("Not Implemented")
