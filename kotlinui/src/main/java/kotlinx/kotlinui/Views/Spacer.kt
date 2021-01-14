package kotlinx.kotlinui

import kotlinx.system.KTypeBase

class Spacer : KTypeBase(), View {
    override val body: View
        get() = error("Never")
}
