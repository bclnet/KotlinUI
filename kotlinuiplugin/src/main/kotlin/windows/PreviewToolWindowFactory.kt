package kotlinuiplugin.windows

import com.intellij.openapi.project.Project
import com.intellij.openapi.wm.ToolWindow
import com.intellij.openapi.wm.ToolWindowFactory
import com.intellij.ui.content.ContentFactory
import kotlinuiplugin.components.ProjectComponent

class PreviewToolWindowFactory : ToolWindowFactory {
    override fun createToolWindowContent(project: Project, toolWindow: ToolWindow) {
        val window = PreviewToolWindow(toolWindow)
        val contentFactory = ContentFactory.SERVICE.getInstance()
        val content = contentFactory.createContent(window.content, "", false)
        toolWindow.contentManager.addContent(content)
        PreviewToolWindow.instance = window
    }
}