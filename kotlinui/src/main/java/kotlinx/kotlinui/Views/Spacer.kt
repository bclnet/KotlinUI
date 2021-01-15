package kotlinx.kotlinui

import kotlinx.kotlinuijson.DynaType

class Spacer : View {
    override val body: Never
        get() = error("Never")

    companion object {
        fun register() {
            DynaType.register<Spacer>()
        }
    }
}
