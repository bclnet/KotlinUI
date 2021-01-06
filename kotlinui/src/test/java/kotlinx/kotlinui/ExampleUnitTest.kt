package kotlinx.kotlinui

import org.junit.Test
import org.junit.Assert.*

class SampleView : View {
    override var body: View = VStack({
        ViewBuilder.buildBlock(
            Text("Hello World")
        ).padding()
    })
}

//class SampleView_Previews : PreviewProvider {
//    static View getPreviews() { returns
//        JsonPreview(() ->
//        SampleView()
//        )
//    }
//}

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }
}