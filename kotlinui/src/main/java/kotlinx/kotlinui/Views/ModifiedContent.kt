package kotlinx.kotlinui

import android.content.Context
import android.view.View as XView
import kotlinx.ptype.PType
import kotlinx.serialization.*
import kotlinx.serialization.descriptors.*
import kotlinx.serialization.encoding.*

@Serializable(with = ModifiedContent.Serializer::class)
data class ModifiedContent<Content, Modifier : ViewModifier>(
    val content: Content,
    val modifier: Modifier
) : View, ViewBuildable {
    override val body: Never
        get() = error("Never")

    //: Codable
    internal class Serializer<Content, Modifier : ViewModifier> : KSerializer<ModifiedContent<Content, Modifier>> {
        val contentSerializer = PolymorphicSerializer(Any::class)
        val modifierSerializer = PolymorphicSerializer(Any::class)

        override val descriptor: SerialDescriptor =
            buildClassSerialDescriptor(":ModifiedContent") {
                element("shape", contentSerializer.descriptor)
                element("modifier", modifierSerializer.descriptor)
            }

        override fun serialize(encoder: Encoder, value: ModifiedContent<Content, Modifier>) =
            encoder.encodeStructure(descriptor) {
                encodeSerializableElement(descriptor, 0, contentSerializer, value.content as Any)
                encodeSerializableElement(descriptor, 1, modifierSerializer, value.modifier as Any)
            }

        override fun deserialize(decoder: Decoder): ModifiedContent<Content, Modifier> =
            decoder.decodeStructure(descriptor) {
                lateinit var content: Any
                lateinit var modifier: Any
                while (true) {
                    when (val index = decodeElementIndex(descriptor)) {
                        0 -> content = decodeSerializableElement(descriptor, 0, contentSerializer)
                        1 -> modifier = decodeSerializableElement(descriptor, 1, modifierSerializer)
                        CompositeDecoder.DECODE_DONE -> break
                        else -> error("Unexpected index: $index")
                    }
                }
                ModifiedContent(content as Content, modifier as Modifier)
            }
    }

    //: ViewBuildable
    override fun buildView(context: Context?): XView {
        when (content) {
            is View -> {
                val view = content.builder.buildView(context)
                return view
            }
            is ViewModifier -> error("Not Implemented")
            else -> error("$content: Wrong type")
        }
    }

    companion object {
        //: Register
        fun register() {
            PType.register<ModifiedContent<Any, ViewModifier>>()
        }
    }
}

fun <T : ViewModifier> View.modifier(modifier: T): ModifiedContent<View, T> =
    ModifiedContent(this, modifier)

//internal fun <Content, Modifier : ViewModifier> ModifiedContent<Content, Modifier>._makeView(view: _GraphValue<ModifiedContent<Content, Modifier>>, inputs: _ViewInputs): _ViewOutputs = error("Not Implemented")
//internal fun <Content, Modifier : ViewModifier> ModifiedContent<Content, Modifier>._makeViewList(view: _GraphValue<ModifiedContent<Content, Modifier>>, inputs: _ViewListInputs): _ViewListOutputs = error("Not Implemented")

