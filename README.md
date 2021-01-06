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
    override var body: View = VStack({
        ViewBuilder.buildBlock(
            Text("Hello World")
        ).padding()
    })
}

class SampleView_Previews : PreviewProvider {
    static View getPreviews() { returns
        JsonPreview(() ->
            SampleView()
        )
    }
}
```

## References

The extension method `var` will let KotlinUIJson know this is intended to be a variable.

```kotlin
VStack(() ->
    Text("Title Here".var(self));
)
```
