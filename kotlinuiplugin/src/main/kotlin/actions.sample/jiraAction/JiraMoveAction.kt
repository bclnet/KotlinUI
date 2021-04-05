package kotlinuiplugin.actions.jiraAction

import com.intellij.ide.HelpTooltip
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.Presentation
import com.intellij.openapi.actionSystem.CommonDataKeys
import com.intellij.openapi.module.Module

class JiraMoveAction : AnAction() {
    override fun actionPerformed(event: AnActionEvent) {
        val dialog = JiraMoveDialog(event.project!!)
        dialog.show()
    }

//    override fun update(e: AnActionEvent) {
//        super.update(e)
//
//        val dataContext = e.dataContext
//        val presentation = e.presentation
//        val maybeSelectedModule = CommonDataKeys.VIRTUAL_FILE.getData(dataContext)
//
//        if (maybeSelectedModule == null) {
////            HelpTooltip.hide(presentation)
//            return
//        }
//        show(presentation)
//    }
//
//    private fun show(presentation: Presentation) {
//        presentation.isEnabled = true
//        presentation.isVisible = true
//    }
//
//    private fun hide(presentation: Presentation) {
//        presentation.isEnabled = false
//        presentation.isVisible = false
//    }
}