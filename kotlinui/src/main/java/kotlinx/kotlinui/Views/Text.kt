package kotlinx.kotlinui

import android.os.Bundle
import java.util.Arrays
import kotlin.system.exitProcess

class Text : View {
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

        override fun equals(o: Any?): Boolean {
            if (o !is Storage) return false
            val s = o as Storage
            return if (type == StorageType.verbatim &&
                s.type == StorageType.verbatim
            ) verbatim.equals(s.verbatim)
            else if (type == StorageType.anyTextStorage && s.type == StorageType.anyTextStorage) anyTextStorage!!.storage.equals(
                s.anyTextStorage!!.storage
            )
            else false
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
        override fun equals(o: Any?): Boolean {
            if (o !is Modifier) return false
            val s = o as Modifier
            return if (type == ModifierType.color &&
                s.type == ModifierType.color
            ) color!!.equals(s.color)
            else if (type == ModifierType.font && s.type == ModifierType.font) font!!.equals(
                s.font
            )
            else false
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

    override fun equals(o: Any?): Boolean {
        if (o !is Text) return false
        val s = o as Text
        return _storage.equals(s._storage) &&
            _modifiers.equals(s._modifiers)
    }

    override val body: View = exitProcess(0)
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
    var modifiers: Array<Text.Modifier> = Arrays.copyOf(_modifiers, _modifiers.size + 1)
    modifiers[modifiers.size - 1] = modifier
    when (_storage.type) {
        Text.StorageType.verbatim -> return Text(_storage.verbatim!!, modifiers)
        Text.StorageType.anyTextStorage -> return Text(_storage.anyTextStorage as Text.AnyTextStorage<String>, modifiers)
        else -> exitProcess(0)
    }
}

internal fun Text._makeView(view: _GraphValue<Text>, inputs: _ViewInputs): _ViewOutputs = exitProcess(0)