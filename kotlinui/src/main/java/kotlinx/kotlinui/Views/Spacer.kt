package kotlinx.kotlinui

import kotlinx.ptype.PType

class Spacer : View {
    override val body: Never
        get() = error("Never")

    companion object {
        fun register() {
            PType.register<Spacer>()
        }
    }
}
