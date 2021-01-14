package kotlinx.kotlinuijson

import kotlinx.kotlinui.*
import kotlinx.serialization.*
import kotlinx.serialization.descriptors.*
import kotlinx.serialization.encoding.*
import kotlinx.system.*

@Serializable(with = JsonUISerializer::class)
class JsonUI(view: View) {
    val body: Any = view

    // MARK: - Register
    private val registered: Boolean = registerDefault()

    private fun registerDefault(): Boolean {
        Divider.register()
        return true
    }
}

object JsonUISerializer : KSerializer<JsonUI> {
    override val descriptor: SerialDescriptor =
        buildClassSerialDescriptor("JsonUI") {
            element<Float>("top")
        }

    override fun serialize(encoder: Encoder, value: JsonUI) =
        encoder.encodeStructure(descriptor) {
        }

    override fun deserialize(decoder: Decoder): JsonUI =
        decoder.decodeStructure(descriptor) {
            error("")
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
