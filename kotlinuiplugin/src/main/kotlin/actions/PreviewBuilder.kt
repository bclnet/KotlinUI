package kotlinuiplugin.actions

import com.intellij.notification.NotificationType
import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile
import kotlinuiplugin.configuration.DaggerPreviewDIComponent
import kotlinuiplugin.configuration.PreviewModule
import kotlinuiplugin.utils.Utils
import kotlinuiplugin.windows.PreviewToolWindow
import javax.inject.Inject

class PreviewBuilder constructor(private val project: Project, private val projectFile: VirtualFile) {

    @Inject
    lateinit var locator: PreviewBuilderLocator

//    private val panel: JiraMovePanel = JiraMovePanel()

    init {
        DaggerPreviewDIComponent.builder()
            .previewModule(PreviewModule(this, project))
            .build().inject(this)
        locator.load(projectFile)

        PreviewToolWindow.instance?.show()
    }

    fun success() {

        Utils.createNotification(
            Utils.message("common.success"),
            Utils.message("issue.moved"),
            project,
            NotificationType.INFORMATION,
            null
        )
    }

    fun error(throwable: Throwable) =
        Utils.createNotification(
            Utils.message("common.error"),
            throwable.localizedMessage,
            project,
            NotificationType.ERROR,
            null
        )
}