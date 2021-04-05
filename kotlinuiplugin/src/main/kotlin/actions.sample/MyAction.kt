package kotlinuiplugin.actions

import com.intellij.notification.NotificationType
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import kotlinuiplugin.utils.Utils

class MyAction : AnAction() {
    override fun actionPerformed(e: AnActionEvent) =
        Utils.createNotification(
            Utils.message("action.title"),
            Utils.message("action.message"),
            e.project,
            NotificationType.INFORMATION,
            null
        )
}