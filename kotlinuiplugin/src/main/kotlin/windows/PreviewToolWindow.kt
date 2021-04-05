package kotlinuiplugin.windows

import com.intellij.openapi.wm.ToolWindow
import kotlinuiplugin.utils.Utils
import javax.swing.*

class PreviewToolWindow(val toolWindow: ToolWindow) {
    //    private lateinit var refreshButton: JButton
//    private lateinit var hideButton: JButton
    private val previewContent: JPanel = JPanel()

    companion object {
        var instance: PreviewToolWindow? = null

//        fun show() = latestWindow?.toolWindow?.show()
//
//        fun hide() = latestWindow?.toolWindow?.hide(null)
    }

    init {
        previewContent.setBounds(0, 0, 452, 254)
        previewContent.layout = null

//        hideButton.addActionListener { e: ActionEvent? -> toolWindow.hide(null) }
//        refreshButton.addActionListener { e: ActionEvent? -> refresh() }
        refresh()
    }

    fun show() = toolWindow.show()

    fun hide() = toolWindow.hide(null)

    fun buildError(message: String) = toolWindow.show()

    private fun refresh() {
        previewContent.removeAll()
        PreviewToolWindowBuilder.sample(previewContent)
    }

    val content: JPanel =
        previewContent

}