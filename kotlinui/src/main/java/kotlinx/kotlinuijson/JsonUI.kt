package kotlinx.kotlinuijson

import kotlinx.kotlinui.*
import kotlinx.system.*
import kotlin.system.exitProcess

//class MyCodingUserInfoKey {
//    companion object {
//        val json = CodingUserInfoKey("jsonData")
//        val jsonContext = CodingUserInfoKey("jsonContext")
//    }
//}
//
//class JsonUI : Codable {
//    val body: Any? = null
//    //var anyView: AnyView? = body as? AnyView
//}
//
////: Codable
//private sealed class CodingKeys {
//    class _ui : CodingKey()
//}

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
