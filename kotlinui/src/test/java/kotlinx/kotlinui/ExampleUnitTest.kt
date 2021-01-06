package kotlinx.kotlinui

import org.junit.Test
import org.junit.Assert.*

class SampleView : View {
    override val body: View =
        VStack {
            Text("Hello World")
        }
        .padding()
}

object SampleView_Previews : PreviewProvider {
    override val previews: View =
        JsonPreview {
            SampleView()
        }
}

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