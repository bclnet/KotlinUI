# KotlinUI

A UI layout in kotlin

# KotlinUIJson

A Json representation of KotlinUI enabling runtime defined views

## Example

JsonPreview will provide json output with a before, and after representation.

```kotlin
import kotlinx.kotlinui
import kotlinx.kotlinuijson

class SampleView : View {
    override val body: View =
        VStack {
            Text("Hello World")
            .padding()
        }
}

object SampleView_Previews : PreviewProvider {
    override val previews: View =
        JsonPreview {
            SampleView()
        }
}
```

## References

The extension method `var` will let KotlinUIJson know this is intended to be a variable.

```kotlin
VStack {
    Text("Title Here".var(self));
}
```
