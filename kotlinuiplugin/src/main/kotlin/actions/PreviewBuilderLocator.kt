package kotlinuiplugin.actions

import com.intellij.openapi.components.ServiceManager
import com.intellij.openapi.components.service
import com.intellij.openapi.module.ModuleUtil
import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile
import kotlinuiplugin.components.ProjectComponent
import kotlinuiplugin.services.PreviewModuleService
import javax.inject.Inject

class PreviewBuilderLocator @Inject constructor(
    private val builder: PreviewBuilder,
    private val project: Project,
    private val component: ProjectComponent
) {
    fun load(projectFile: VirtualFile) {
        val module = ModuleUtil.findModuleForFile(projectFile, project) ?: return
        val svc = module.getService(PreviewModuleService::class.java)
        svc.work(projectFile)
    }
}