package kotlinx.kotlinui

class Never : View {
    override val body: Never
        get() = error("Never")
}
