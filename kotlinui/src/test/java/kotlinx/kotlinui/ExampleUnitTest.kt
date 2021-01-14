package kotlinx.kotlinui

import kotlinx.kotlinuijson.JsonPreview
import org.junit.Test
import org.junit.Assert.*

class SampleView : View {
    override var body: View =
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

class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }
}