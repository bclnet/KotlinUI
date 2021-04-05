package kotlinuiplugin.actions

import com.intellij.notification.NotificationType
import com.intellij.openapi.actionSystem.*
import kotlinuiplugin.utils.Utils

import com.intellij.openapi.vfs.VirtualFile

class PreviewAction : AnAction() {
    override fun actionPerformed(event: AnActionEvent) {
        val file = CommonDataKeys.VIRTUAL_FILE.getData(event.dataContext)!!
        val builder = PreviewBuilder(event.project!!, file)
        //builder.show()
//        Utils.createNotification(
//            Utils.message("action.title"),
//            Utils.message("action.message"),
//            event.project,
//            NotificationType.INFORMATION,
//            null
//        )
    }
}