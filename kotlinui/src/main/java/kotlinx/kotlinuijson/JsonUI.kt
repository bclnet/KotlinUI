@file:OptIn(ExperimentalStdlibApi::class)

package kotlinx.kotlinuijson

import kotlinx.kotlinui.*
import kotlinx.serialization.*
import kotlinx.serialization.descriptors.*
import kotlinx.serialization.encoding.*
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.contextual
import kotlinx.system.*
import java.lang.Exception
import kotlin.reflect.KType
import kotlin.reflect.typeOf

inline fun <reified T> JsonUI(view: View) = JsonUI(typeOf<T>(), view)

@Serializable(with = JsonUISerializer::class)
class JsonUI(val type: KType, view: View) {
    val body: Any = view
    val anyView: AnyView?
        get() = body as? AnyView

    // MARK: - Register
    private val registered: Boolean = registerDefault()

    private fun registerDefault(): Boolean {
        Divider.register()
        Spacer.register()
        Stepper.register()
        Text.register()
        TextField.register()
        Toggle.register()
        TouchBar.register()
        TupleView.register()
        VSplitView.register()
        VStack.register()
        ZStack.register()
        return true
    }
}

internal object JsonUISerializer : KSerializer<JsonUI> {
    internal class UserInfoJson(val data: String) : KSerializerUserInfo<UserInfoJson>()
    internal class UserInfoJsonContext(val context: JsonContext) : KSerializerUserInfo<UserInfoJsonContext>()

    override val descriptor: SerialDescriptor =
        buildClassSerialDescriptor("JsonUI") {
            element<JsonContext>("_ui")
        }

    override fun serialize(encoder: Encoder, value: JsonUI) {
        val userInfoJsonContext = encoder.serializersModule.getContextual(UserInfoJsonContext::class) as? UserInfoJsonContext ?: throw Exception(".jsonContext")
        val context = userInfoJsonContext.context
        encoder.encodeStructure(descriptor) {
            encodeSerializableElement(descriptor, 0, JsonContextSerializer, context)
            context.encodeSuper(this, descriptor, 1, Pair(value.type, value.body))
        }
    }

    override fun deserialize(decoder: Decoder): JsonUI {
        val userInfoJson = decoder.serializersModule.getContextual(UserInfoJson::class) as? UserInfoJson ?: throw Exception(".json")
        val userInfoJsonContext = decoder.serializersModule.getContextual(UserInfoJsonContext::class) as? UserInfoJsonContext ?: return decoder.decodeStructure(descriptor) {
            val index = decodeElementIndex(descriptor)
            if (index != 0) throw Exception(".json Parse")
            val nextContext = decodeSerializableElement(descriptor, 0, JsonContextSerializer)
            val json = Json {
                serializersModule = SerializersModule {
                    contextual(userInfoJson)
                    contextual(UserInfoJsonContext(nextContext))
                }
            }
            json.decodeFromString(JsonUISerializer, userInfoJson.data)
        }
        return decoder.decodeStructure(descriptor) {
            lateinit var body: Any
            while (true) {
                when (val index = decodeElementIndex(descriptor)) {
//                    1 -> body = decodeSerializableElement(descriptor, 1, serializer(type.underlyingType))!!
                    CompositeDecoder.DECODE_DONE -> break
                    else -> error("Unexpected index: $index")
                }
            }
            body = Text("Test")
            JsonUI<Text>(body)
        }
    }
}

//
//fun decode(decoder: Decoder) {
//    val json = decoder.userInfo[CodingUserInfoKey.json] as? String ?: throw Exception()
//    decoder.userInfo[CodingUserInfoKey.jsonContext] as? JsonContext
//    guard decoder . userInfo [.jsonContext] as? JsonContext != nil else {
//        let nextContext : JsonContext
//            do {
//                var container = try decoder.unkeyedContainer()
//                    nextContext = try container.decode(JsonContext.self)
//                    } catch {
//                        let container = try decoder.container(keyedBy: CodingKeys. self)
//                            nextContext = try container.decode(JsonContext.self, forKey:._ui)
//                            }
//                    let nextDecoder = JSONDecoder ()
//                    nextDecoder.userInfo[.json] = json
//                    nextDecoder.userInfo[.jsonContext] = nextContext
//                    body = try nextDecoder.decode(JsonUI.self, from: json).body
//                        return
//                    }
//    guard let context =
//        decoder.userInfo[.jsonContext] as? JsonContext else { fatalError(".jsonContext") }
//    let value = try context.decodeDynaSuper(from: decoder)
//        body = AnyView.any(value)
//    }
