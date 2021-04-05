package kotlinuiplugin.utils

import com.intellij.notification.NotificationDisplayType
import com.intellij.notification.NotificationGroup
import com.intellij.notification.NotificationListener
import com.intellij.notification.NotificationType
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.project.Project
import java.util.*
import javax.swing.event.HyperlinkEvent
import com.intellij.CommonBundle
import com.intellij.reference.SoftReference
import org.jetbrains.annotations.NonNls
import org.jetbrains.annotations.PropertyKey
import java.lang.ref.Reference

object Utils {

    @NonNls
    private val BUNDLE_NAME = "messages.strings"
    private var ourBundle: Reference<ResourceBundle>? = null

    private fun getBundle(): ResourceBundle {
        var bundle = SoftReference.dereference(ourBundle)
        if (bundle == null) {
            bundle = ResourceBundle.getBundle(BUNDLE_NAME)
            ourBundle = SoftReference(bundle)
        }
        return bundle!!
    }

    fun message(@PropertyKey(resourceBundle = "messages.strings") key: String, vararg params: Any): String =
        CommonBundle.message(getBundle(), key, *params)

    fun createNotification(
        title: String,
        message: String,
        project: Project?,
        type: NotificationType,
        listener: NotificationListener?
    ) =
        NotificationGroup(
            "KotlinUI$title",
            NotificationDisplayType.BALLOON,
            true
        ).createNotification(title, message, type, listener).notify(project)

    fun createHyperLink(pre: String, link: String, post: String) =
        message("utils.hyperlink.code", pre, link, post)

    fun restartListener() =
        NotificationListener { _, event ->
            if (event.eventType === HyperlinkEvent.EventType.ACTIVATED)
                ApplicationManager.getApplication().restart()
        }
}