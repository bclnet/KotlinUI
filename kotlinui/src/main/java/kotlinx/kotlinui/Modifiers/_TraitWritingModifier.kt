package kotlinx.kotlinui

import kotlinx.ptype.PType
import kotlinx.serialization.*
import kotlinx.serialization.descriptors.*
import kotlinx.serialization.encoding.*
import android.content.ContentProvider as UXItemProvider

@Serializable(with = _TraitWritingModifier.Serializer::class)
data class _TraitWritingModifier<TraitKey : AnyTraitKey>(
    val value: TraitKey,
) : ViewModifier {
    //: Codable
    internal class Serializer<TraitKey : AnyTraitKey> : KSerializer<_TraitWritingModifier<TraitKey>> {
        val traitKeySerializer = PolymorphicSerializer(Any::class)

        override val descriptor: SerialDescriptor =
            buildClassSerialDescriptor(":_TraitWritingModifier") {
                element<String>("isDeleteDisabled")
                element<String>("isMoveDisabled")
                element<String>("itemProvider")
                element<String>("previewLayout")
                element<String>("zIndex")
            }

        override fun serialize(encoder: Encoder, value: _TraitWritingModifier<TraitKey>) =
            encoder.encodeStructure(descriptor) {
                when (value.value) {
                    is IsDeleteDisabledTraitKey -> encodeSerializableElement(descriptor, 0, IsDeleteDisabledTraitKey.Serializer, value.value)
                    is IsMoveDisabledTraitKey -> encodeSerializableElement(descriptor, 1, IsMoveDisabledTraitKey.Serializer, value.value)
                    is ItemProviderTraitKey -> encodeSerializableElement(descriptor, 2, ItemProviderTraitKey.Serializer, value.value)
                    is PreviewLayoutTraitKey -> encodeSerializableElement(descriptor, 3, PreviewLayoutTraitKey.Serializer, value.value)
                    is ZIndexTraitKey -> encodeSerializableElement(descriptor, 4, ZIndexTraitKey.Serializer, value.value)
                    else -> error("$value")
                }
            }

        override fun deserialize(decoder: Decoder): _TraitWritingModifier<TraitKey> =
            decoder.decodeStructure(descriptor) {
                lateinit var traitKey: TraitKey
                while (true) {
                    when (val index = decodeElementIndex(descriptor)) {
                        0 -> traitKey = decodeSerializableElement(descriptor, 0, IsDeleteDisabledTraitKey.Serializer) as TraitKey
                        1 -> traitKey = decodeSerializableElement(descriptor, 1, IsMoveDisabledTraitKey.Serializer) as TraitKey
                        2 -> traitKey = decodeSerializableElement(descriptor, 2, ItemProviderTraitKey.Serializer) as TraitKey
                        3 -> traitKey = decodeSerializableElement(descriptor, 3, PreviewLayoutTraitKey.Serializer) as TraitKey
                        4 -> traitKey = decodeSerializableElement(descriptor, 4, ZIndexTraitKey.Serializer) as TraitKey
                        CompositeDecoder.DECODE_DONE -> break
                        else -> error("Unexpected index: $index")
                    }
                }
                _TraitWritingModifier(traitKey)
            }
    }

    companion object {
        //: Register
        fun register() {
            PType.register<_TouchBarModifier<View>>()
        }
    }
}

interface AnyTraitKey

@Serializable(with = IsDeleteDisabledTraitKey.Serializer::class)
internal class IsDeleteDisabledTraitKey(
    val value: Boolean
) : AnyTraitKey {
//    fun body(content: AnyView): AnyView = AnyView(content.deleteDisabled(value))

    //: Codable
    internal object Serializer : KSerializer<IsDeleteDisabledTraitKey> {
        override val descriptor: SerialDescriptor =
            PrimitiveSerialDescriptor("IsDeleteDisabledTraitKey", PrimitiveKind.BOOLEAN)

        override fun serialize(encoder: Encoder, value: IsDeleteDisabledTraitKey) =
            encoder.encodeBoolean(value.value)

        override fun deserialize(decoder: Decoder): IsDeleteDisabledTraitKey =
            IsDeleteDisabledTraitKey(decoder.decodeBoolean())
    }
}

@Serializable(with = IsMoveDisabledTraitKey.Serializer::class)
internal class IsMoveDisabledTraitKey(
    val value: Boolean
) : AnyTraitKey {
//    fun body (content: AnyView): AnyView = AnyView(content.moveDisabled(value))

    //: Codable
    internal object Serializer : KSerializer<IsMoveDisabledTraitKey> {
        override val descriptor: SerialDescriptor =
            PrimitiveSerialDescriptor("IsMoveDisabledTraitKey", PrimitiveKind.BOOLEAN)

        override fun serialize(encoder: Encoder, value: IsMoveDisabledTraitKey) =
            encoder.encodeBoolean(value.value)

        override fun deserialize(decoder: Decoder): IsMoveDisabledTraitKey =
            IsMoveDisabledTraitKey(decoder.decodeBoolean())
    }
}

@Serializable(with = ItemProviderTraitKey.Serializer::class)
internal class ItemProviderTraitKey(
    val value: () -> UXItemProvider?
) : AnyTraitKey {
//    fun body (content: AnyView): AnyView = AnyView(content.itemProvider(value))

    //: Codable
    internal object Serializer : KSerializer<ItemProviderTraitKey> {
        override val descriptor: SerialDescriptor =
            PrimitiveSerialDescriptor("ItemProviderTraitKey", PrimitiveKind.STRING)

        override fun serialize(encoder: Encoder, value: ItemProviderTraitKey) {
            val item = value.value()
            if (item == null) {
                encoder.encodeNull()
                return
            }
            encoder.encodeSerializableValue(UXItemProviderSerializer, item)
        }

        override fun deserialize(decoder: Decoder): ItemProviderTraitKey {
            if (decoder.decodeNull() != null)
                return ItemProviderTraitKey { null }
            return ItemProviderTraitKey({ decoder.decodeSerializableValue(UXItemProviderSerializer) })
        }
    }
}

@Serializable(with = PreviewLayoutTraitKey.Serializer::class)
internal class PreviewLayoutTraitKey(
    val value: PreviewLayout
) : AnyTraitKey {
//    fun body (content: AnyView): AnyView = AnyView(content.moveDisabled(value))

    //: Codable
    internal object Serializer : KSerializer<PreviewLayoutTraitKey> {
        override val descriptor: SerialDescriptor =
            PrimitiveSerialDescriptor("PreviewLayoutTraitKey", PrimitiveKind.STRING)

        override fun serialize(encoder: Encoder, value: PreviewLayoutTraitKey) =
            encoder.encodeSerializableValue(PreviewLayout.Serializer, value.value)

        override fun deserialize(decoder: Decoder): PreviewLayoutTraitKey =
            PreviewLayoutTraitKey(decoder.decodeSerializableValue(PreviewLayout.Serializer))

    }
}

@Serializable(with = ZIndexTraitKey.Serializer::class)
internal class ZIndexTraitKey(
    val value: Double
) : AnyTraitKey {
//    fun body (content: AnyView): AnyView = AnyView(content.zIndex(value))

    //: Codable
    internal object Serializer : KSerializer<ZIndexTraitKey> {
        override val descriptor: SerialDescriptor =
            PrimitiveSerialDescriptor("ZIndexTraitKey", PrimitiveKind.DOUBLE)

        override fun serialize(encoder: Encoder, value: ZIndexTraitKey) =
            encoder.encodeDouble(value.value)

        override fun deserialize(decoder: Decoder): ZIndexTraitKey =
            ZIndexTraitKey(decoder.decodeDouble())
    }
}
