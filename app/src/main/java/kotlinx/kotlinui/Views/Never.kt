package kotlinx.kotlinui

import kotlin.system.exitProcess

class Never : View {
    override var body: View = exitProcess(0)
}
