package kotlinx.kotlinui

import kotlinx.serialization.json.*
import kotlinx.serialization.modules.*
import java.lang.Exception

class JsonPreview<Content : View>(content: ViewBuilder.() -> Content) : View {
    // The json preview's content.
    val content: Content = content(ViewBuilder)

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
                data = json.encodeToString(JsonUI.serializer(), JsonUI<Text>(view))
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
        get() = GeometryReader { geometry ->
            VStack {
                HStack {
                    content
                        .frame(geometry.size.width / 2f, geometry.size.height * 0.7f)
                        .overlay(
                            RoundedRectangle(16f)
                                .stroke(Color.black, 4f)
                        ) +
                    content2
                        .frame(geometry.size.width / 2f, geometry.size.height * 0.7f)
                        .overlay(
                            RoundedRectangle(16f)
                                .stroke(Color.black, 4f)
                        )
                }
                Spacer() +
                ZStack {
                    ScrollView {
                        HStack {
                            Text(data) +
                            Spacer()
                        }
                            .padding()
                    } +
                    VStack {
                        HStack(VerticalAlignment.top) {
                            Spacer() +
                            Button({
                                print("copied to clipboard")
                            }, label = {
                                Image("")
                                    .font(Font.system(Font.TextStyle.title))
                            })
                        }
                            .padding() +
                        Spacer()
                    }
                }
                    .frame(geometry.size.width, geometry.size.height * 0.3f - 10f)
                    .overlay(
                        RoundedRectangle(16f)
                            .stroke(Color.black, 4f)
                    )
            }
        }
            .padding()
}

