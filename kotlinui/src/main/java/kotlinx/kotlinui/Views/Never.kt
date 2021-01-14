package kotlinx.kotlinui

import kotlinx.system.KTypeBase

class Never : KTypeBase(), View {
    override val body: View
        get() = error("Never")
}
