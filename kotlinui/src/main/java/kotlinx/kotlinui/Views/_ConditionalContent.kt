package kotlinx.kotlinui

import kotlinx.ptype.PType
import kotlinx.serialization.*
import kotlinx.serialization.descriptors.*
import kotlinx.serialization.encoding.*

@Serializable(with = _ConditionalContent.Serializer::class)
data class _ConditionalContent<TrueContent : View, FalseContent : View>(
    val storage: Storage
) : View {
    sealed class Storage {
        data class trueContent<TrueContent : View>(val trueContent: TrueContent) : Storage()
        data class falseContent<FalseContent : View>(val falseContent: FalseContent) : Storage()
    }

    override val body: Never
        get() = error("Never")

    //: Codable
    internal class Serializer<TrueContent : View, FalseContent : View> : KSerializer<_ConditionalContent<TrueContent, FalseContent>> {
        val trueContentSerializer = PolymorphicSerializer(Any::class)
        val faultContentSerializer = PolymorphicSerializer(Any::class)

        override val descriptor: SerialDescriptor =
            buildClassSerialDescriptor("_ConditionalContent") {
                element<View>("true")
                element<View>("false")
            }

        override fun serialize(encoder: Encoder, value: _ConditionalContent<TrueContent, FalseContent>) =
            encoder.encodeStructure(descriptor) {
                when (value.storage) {
                    is Storage.trueContent<*> -> encodeSerializableElement(descriptor, 0, trueContentSerializer, value.storage)
                    is Storage.falseContent<*> -> encodeSerializableElement(descriptor, 1, faultContentSerializer, value.storage)
                }
            }

        override fun deserialize(decoder: Decoder): _ConditionalContent<TrueContent, FalseContent> =
            decoder.decodeStructure(descriptor) {
                lateinit var storage: Storage
                while (true) {
                    when (val index = decodeElementIndex(_ZStackLayout.Serializer.descriptor)) {
                        0 -> storage = Storage.trueContent(decodeSerializableElement(descriptor, 0, trueContentSerializer) as TrueContent)
                        1 -> storage = Storage.falseContent(decodeSerializableElement(descriptor, 1, faultContentSerializer) as FalseContent)
                        CompositeDecoder.DECODE_DONE -> break
                        else -> error("Unexpected index: $index")
                    }
                }
                _ConditionalContent(storage)
            }
    }

    companion object {
        //: Register
        fun register() {
            PType.register<_ConditionalContent<AnyView, AnyView>>()
        }
    }
}

//internal fun <TrueContent : View, FalseContent : View> _ConditionalContent<TrueContent, FalseContent>._makeView(view: _GraphValue<_ConditionalContent<TrueContent, FalseContent>>, inputs: _ViewInputs): _ViewOutputs = error("Not Implemented")
//internal fun <TrueContent : View, FalseContent : View> _ConditionalContent<TrueContent, FalseContent>._makeViewList(view: _GraphValue<_ConditionalContent<TrueContent, FalseContent>>, inputs: _ViewListInputs): _ViewListOutputs = error("Not Implemented")