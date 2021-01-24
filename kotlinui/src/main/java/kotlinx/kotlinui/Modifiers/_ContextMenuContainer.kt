package kotlinx.kotlinui

import kotlinx.ptype.PType
import kotlinx.serialization.*
import kotlinx.serialization.descriptors.*
import kotlinx.serialization.encoding.*

@Serializable(with = ContextMenu.Serializer::class)
class ContextMenu<MenuItems : View> {
    //: Codable
    internal class Serializer<MenuItems : View>(private val menuItemsSerializer: KSerializer<MenuItems>) : KSerializer<ContextMenu<MenuItems>> {
        override val descriptor: SerialDescriptor =
            buildClassSerialDescriptor("ContextMenu") {
            }

        override fun serialize(encoder: Encoder, value: ContextMenu<MenuItems>) =
            encoder.encodeStructure(descriptor) {
            }

        override fun deserialize(decoder: Decoder): ContextMenu<MenuItems> =
            decoder.decodeStructure(descriptor) {
                ContextMenu<MenuItems>()
            }
    }
}

@Serializable(with = _ContextMenuContainer.Serializer::class)
class _ContextMenuContainer(
    val contextMenu: Any
) {
//    fun body(children: _VariadicView_Children): Content =
//        if (contextMenu == null) content.contextMenu { children }
//        else content.contextMenu(contextMenu)

    //: Codable
    internal object Serializer : KSerializer<_ContextMenuContainer> {
        override val descriptor: SerialDescriptor =
            buildClassSerialDescriptor("_ContextMenuContainer") {
                element<String>("contextMenu")
            }

        override fun serialize(encoder: Encoder, value: _ContextMenuContainer) =
            encoder.encodeStructure(descriptor) {
                error("")
            }

        override fun deserialize(decoder: Decoder): _ContextMenuContainer =
            decoder.decodeStructure(descriptor) {
                error("")
            }
    }

    @Serializable(with = Container.Serializer::class)
    class Container<Content : View>(
        val contextMenu: ContextMenu<Content>?,
        val content: Content
    ) : _VariadicView_ViewRoot {
        //: Codable
        internal class Serializer<Content : View>(private val contentSerializer: KSerializer<Content>) : KSerializer<Container<Content>> {
            override val descriptor: SerialDescriptor =
                buildClassSerialDescriptor("Container") {
                    element<String>("contextMenu")
                    element<String>("content")
                }

            override fun serialize(encoder: Encoder, value: Container<Content>) =
                encoder.encodeStructure(descriptor) {
                    if (value.contextMenu != null) encodeSerializableElement(descriptor, 0, ContextMenu.Serializer(contentSerializer), value.contextMenu)
                    encodeSerializableElement(descriptor, 1, contentSerializer, value.content)
                }

            override fun deserialize(decoder: Decoder): Container<Content> =
                decoder.decodeStructure(descriptor) {
                    var contextMenu: ContextMenu<Content>? = null
                    var content: Content? = null
                    while (true) {
                        when (val index = decodeElementIndex(descriptor)) {
                            0 -> contextMenu = decodeSerializableElement(descriptor, 0, ContextMenu.Serializer(contentSerializer))
                            1 -> content = decodeSerializableElement(descriptor, 1, contentSerializer)
                            CompositeDecoder.DECODE_DONE -> break
                            else -> error("Unexpected index: $index")
                        }
                    }
                    Container(contextMenu, content!!)
                }
        }
    }

    companion object {
        //: Register
        fun register() {
            PType.register<Container<AnyView>>()
            PType.register<StyleContextWriter<Any>>()
            PType.register<MenuContext>()
        }
    }
}

@Serializable(with = StyleContextWriter.Serializer::class)
class StyleContextWriter<Context> : ViewModifier {
//    fun body(content: AnyView): AnyView { AnyView(content) }

    //: Codable
    internal class Serializer<Context>(private val contextSerializer: KSerializer<Context>) : KSerializer<StyleContextWriter<Context>> {
        override val descriptor: SerialDescriptor = buildClassSerialDescriptor("StyleContextWriter") {}
        override fun serialize(encoder: Encoder, value: StyleContextWriter<Context>) {}
        override fun deserialize(decoder: Decoder): StyleContextWriter<Context> = StyleContextWriter()
    }
}

@Serializable(with = MenuContext.Serializer::class)
class MenuContext {
    //: Codable
    internal object Serializer : KSerializer<MenuContext> {
        override val descriptor: SerialDescriptor = buildClassSerialDescriptor("MenuContext") {}
        override fun serialize(encoder: Encoder, value: MenuContext) {}
        override fun deserialize(decoder: Decoder): MenuContext = MenuContext()
    }
}
