package kotlinx.kotlinuijson

import kotlinx.kotlinui.*
import kotlinx.serialization.json.*
import kotlinx.serialization.modules.*
import java.lang.Exception

class JsonPreview<Content : View>(content: ViewBuilder.() -> Content) : View {
    // The json preview's content.
    val content: Content = content(ViewBuilder())

    // The json preview's content.
    lateinit var content2: AnyView

    // The json preview's data.
    lateinit var data: String

    init {
        val view = this.content
        val context = JsonContext[view]
        with(context) {
            // data
            try {
                val json = Json {
                    serializersModule = SerializersModule { contextual(JsonUISerializer.UserInfoJsonContext(context)) }
                    prettyPrint = true
                }
                data = json.encodeToString(JsonUI.serializer(), JsonUI(view))
                println(data)
            } catch (e: Exception) {
                data = e.localizedMessage!!
                content2 = AnyView(Text("ERROR"))
                return@with
            }

            // content2
            try {
                val json = Json {
                    serializersModule = SerializersModule { contextual(JsonUISerializer.UserInfoJson(data)) }
                }
                val jsonUI = json.decodeFromString(JsonUI.serializer(), data)
                content2 = jsonUI.anyView ?: AnyView(Text("ERROR:notAnyView"))
            } catch (e: Exception) {
                data = e.localizedMessage!!
                content2 = AnyView(Text("ERROR: ${e.localizedMessage}"))
                return@with
            }
        }
        JsonContext.remove(view)
    }

    override val body: View
        get() = Text("Body")
//        get() = GeometryReader { geometry ->
//            VStack {
//                HStack {
//                    content
//                        .frame(width: geometry.size.width / 2, height: geometry.size.height * 0.7)
//                        .overlay(
//                            RoundedRectangle(cornerRadius: 16)
//                                .stroke(Color.black, lineWidth: 4)
//                        )
//                    content2
//                        .frame(width: geometry.size.width / 2, height: geometry.size.height * 0.7)
//                        .overlay(
//                            RoundedRectangle(cornerRadius: 16)
//                                .stroke(Color.black, lineWidth: 4)
//                        )
//                }
//                Spacer()
//                ZStack {
//                    ScrollView {
//                        HStack {
//                            Text(String(data: data, encoding: .utf8)!)
//                            Spacer()
//                        }
//                        .padding()
//                    }
//                    VStack {
//                        HStack(alignment: .top) {
//                            Spacer()
//                            Button(action: {
//                                #if os(macOS)
//                                NSPasteboard.general.setString(String(data: data, encoding: .utf8)!, forType: .string)
//                                #else
//                                UIPasteboard.general.string = String(data: data, encoding: .utf8)!
//                                #endif
//                                print("copied to clipboard")
//                            }, label: {
//                                if #available(macOS 11.0, *) {
//                                    Image(systemName: "doc.text")
//                                        .font(Font.system(.title))
//                                } else {
//                                    Image("")
//                                        .font(Font.system(.title))
//                                }
//                            })
//                        }
//                        .padding()
//                        Spacer()
//                    }
//                }
//                .frame(width: geometry.size.width, height: geometry.size.height * 0.3 - 10)
//                .overlay(
//                    RoundedRectangle(cornerRadius: 16)
//                        .stroke(Color.black, lineWidth: 4)
//                )
//            }
//        }
//        .padding()
}

