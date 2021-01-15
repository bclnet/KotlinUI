package kotlinx.kotlinui

import android.os.Bundle
import kotlinx.kotlinuijson.DynaType
import kotlinx.serialization.*
import kotlinx.serialization.descriptors.*
import kotlinx.serialization.encoding.*
import java.util.Arrays

@Serializable(with = TextSerializer::class)
class Text : View {
    class AnyTextStorage<Storage>(var storage: Storage)
    class AnyTextModifier

    sealed class Storage {
        data class verbatim(val verbatim: String) : Storage()
        data class anyTextStorage(val anyTextStorage: AnyTextStorage<String>) : Storage()

        override fun equals(other: Any?): Boolean {
            if (other !is Storage) return false
            return when (this) {
                is verbatim -> other is verbatim && verbatim == other.verbatim
                is anyTextStorage -> other is anyTextStorage && anyTextStorage == other.anyTextStorage
            }
        }

        override fun hashCode(): Int =
            when (this) {
                is verbatim -> verbatim.hashCode()
                is anyTextStorage -> anyTextStorage.hashCode()
            }
    }

    sealed class Modifier {
        data class color(val color: Color?) : Modifier()
        data class font(val font: Font?) : Modifier()

        override fun equals(other: Any?): Boolean {
            if (other !is Modifier) return false
            return when (this) {
                is color -> other is color && color!! == other.color!!
                is font -> other is font && font!! == other.font!!
            }
        }

        override fun hashCode(): Int =
            when (this) {
                is color -> color.hashCode()
                is font -> font.hashCode()
            }
    }

    lateinit var _storage: Storage
    lateinit var _modifiers: Array<Modifier>

    constructor(verbatim: String) {
        _storage = Storage.verbatim(verbatim)
        _modifiers = arrayOf()
    }

    internal constructor(verbatim: String, modifiers: Array<Modifier>) {
        _storage = Storage.verbatim(verbatim)
        _modifiers = modifiers
    }

    internal constructor(anyTextStorage: AnyTextStorage<String>, modifiers: Array<Modifier>) {
        _storage = Storage.anyTextStorage(anyTextStorage)
        _modifiers = modifiers
    }

    //    constructor(content: String) {
//        _storage = Storage(StorageType.anyTextStorage)
//        _storage.anyTextStorage = AnyTextStorage<String>(content)
//    }
    constructor(
        key: LocalizedStringKey,
        tableName: String? = null,
        bundle: Bundle? = null,
        comment: String? = null
    ) {
        _storage = Storage.anyTextStorage(AnyTextStorage<String>(key.key))
    }

    override fun equals(other: Any?): Boolean {
        if (other !is Text) return false
        return _storage == other._storage &&
            _modifiers == other._modifiers
    }

    override fun hashCode(): Int {
        var result = _storage.hashCode()
        result = 31 * result + _modifiers.contentHashCode()
        result = 31 * result + body.hashCode()
        return result
    }

    override val body: Never
        get() = error("Never")

    companion object {
        fun register() {
            DynaType.register<Text>()
//            DynaType.register<Text.TruncationMode>()
        }
    }
}

internal class TextSerializer : KSerializer<Text> {
    override val descriptor: SerialDescriptor =
        buildClassSerialDescriptor("Text") {
        }

    override fun serialize(encoder: Encoder, value: Text) =
        encoder.encodeStructure(descriptor) {
        }

    override fun deserialize(decoder: Decoder): Text =
        decoder.decodeStructure(descriptor) {
            Text("")
        }
}

fun Text.foregroundColor(color: Color?): Text =
    textWithModifier(Text.Modifier.color(color))

fun Text.font(font: Font?): Text =
    textWithModifier(Text.Modifier.font(font))

private fun Text.textWithModifier(modifier: Text.Modifier): Text {
    val modifiers: Array<Text.Modifier> = Arrays.copyOf(_modifiers, _modifiers.size + 1)
    modifiers[modifiers.size - 1] = modifier
    return when (val storage = _storage) {
        is Text.Storage.verbatim -> Text(storage.verbatim, modifiers)
        is Text.Storage.anyTextStorage -> Text(storage.anyTextStorage, modifiers)
        else -> error("$storage")
    }
}

internal fun Text._makeView(view: _GraphValue<Text>, inputs: _ViewInputs): _ViewOutputs = error("Not Implemented")
