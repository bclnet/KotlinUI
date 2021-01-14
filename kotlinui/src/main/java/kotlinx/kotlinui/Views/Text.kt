package kotlinx.kotlinui

import android.os.Bundle
import kotlinx.system.KTypeBase
import kotlinx.serialization.*
import kotlinx.serialization.descriptors.*
import kotlinx.serialization.encoding.*
import java.util.Arrays

@Serializable(with = TextSerializer::class)
class Text : KTypeBase, View {
    class AnyTextStorage<Storage>(var storage: Storage)
    class AnyTextModifier

//    sealed class Storage {
//        data class verbatim(val verbatim: String) : Storage()
//        data class anyTextStorage(val anyTextStorage: AnyTextStorage<String>?) : Storage()
//    }

    enum class StorageType { verbatim, anyTextStorage }
    class Storage(var type: StorageType) {
        var verbatim: String? = null
        var anyTextStorage: AnyTextStorage<String>? = null

        override fun equals(other: Any?): Boolean {
            if (other !is Storage) return false
            val s = other as Storage
            return if (type == StorageType.verbatim &&
                s.type == StorageType.verbatim
            ) verbatim.equals(s.verbatim)
            else if (type == StorageType.anyTextStorage && s.type == StorageType.anyTextStorage) anyTextStorage!!.storage.equals(
                s.anyTextStorage!!.storage
            )
            else false
        }

        override fun hashCode(): Int {
            var result = type.hashCode()
            result = 31 * result + (verbatim?.hashCode() ?: 0)
            result = 31 * result + (anyTextStorage?.hashCode() ?: 0)
            return result
        }
    }

    enum class ModifierType { color, font }
    class Modifier(var type: ModifierType) {
        var color: Color? = null
        var font: Font? = null

        // case italic
        // case weight(Font.Weight?)
        // case kerning(CGFloat)
        // case tracking(CGFloat)
        // case baseline(CGFloat)
        // case rounded
        // case anyTextModifier(AnyTextModifier)
        override fun equals(other: Any?): Boolean {
            if (other !is Modifier) return false
            val s = other as Modifier
            return if (type == ModifierType.color &&
                s.type == ModifierType.color
            ) color!!.equals(s.color)
            else if (type == ModifierType.font && s.type == ModifierType.font) font!!.equals(
                s.font
            )
            else false
        }

        override fun hashCode(): Int {
            var result = type.hashCode()
            result = 31 * result + (color?.hashCode() ?: 0)
            result = 31 * result + (font?.hashCode() ?: 0)
            return result
        }
    }

    var _storage: Storage = Storage(StorageType.verbatim)
    var _modifiers: Array<Modifier> = arrayOf()

    constructor(verbatim: String) {
        _storage = Storage(StorageType.verbatim)
        _storage.verbatim = verbatim
    }

    internal constructor(verbatim: String, modifiers: Array<Modifier>) {
        _storage = Storage(StorageType.verbatim)
        _storage.verbatim = verbatim
        _modifiers = modifiers
    }

    internal constructor(anyTextStorage: AnyTextStorage<String>, modifiers: Array<Modifier>) {
        _storage = Storage(StorageType.anyTextStorage)
        _storage.anyTextStorage = anyTextStorage
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
        _storage = Storage(StorageType.anyTextStorage)
        _storage.anyTextStorage = AnyTextStorage<String>(key.key)
    }

    override fun equals(other: Any?): Boolean {
        if (other !is Text) return false
        val s = other as Text
        return _storage.equals(s._storage) &&
            _modifiers.equals(s._modifiers)
    }

    override fun hashCode(): Int {
        var result = _storage.hashCode()
        result = 31 * result + _modifiers.contentHashCode()
        result = 31 * result + body.hashCode()
        return result
    }

    override val body: View
        get() = error("Never")
}

class TextSerializer : KSerializer<Text> {
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

fun Text.foregroundColor(color: Color?): Text {
    val modifier = Text.Modifier(Text.ModifierType.color)
    modifier.color = color
    return textWithModifier(modifier)
}

fun Text.font(font: Font?): Text {
    val modifier = Text.Modifier(Text.ModifierType.font)
    modifier.font = font
    return textWithModifier(modifier)
}

private fun Text.textWithModifier(modifier: Text.Modifier): Text {
    val modifiers: Array<Text.Modifier> = Arrays.copyOf(_modifiers, _modifiers.size + 1)
    modifiers[modifiers.size - 1] = modifier
    when (_storage.type) {
        Text.StorageType.verbatim -> return Text(_storage.verbatim!!, modifiers)
        Text.StorageType.anyTextStorage -> return Text(_storage.anyTextStorage as Text.AnyTextStorage<String>, modifiers)
        else -> error("${_storage.type}")
    }
}

internal fun Text._makeView(view: _GraphValue<Text>, inputs: _ViewInputs): _ViewOutputs = error("Not Implemented")