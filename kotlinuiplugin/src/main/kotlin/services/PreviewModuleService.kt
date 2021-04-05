package kotlinuiplugin.services

import com.intellij.ide.highlighter.JavaClassFileType
import com.intellij.ide.highlighter.JavaFileType
import com.intellij.notification.NotificationType
import com.intellij.openapi.components.Service
import com.intellij.openapi.fileTypes.FileType
import com.intellij.openapi.fileTypes.FileTypeManager
import com.intellij.openapi.module.Module
import com.intellij.openapi.roots.CompilerModuleExtension
import com.intellij.openapi.roots.ProjectFileIndex
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.psi.search.FileTypeIndex
import com.intellij.psi.search.GlobalSearchScope
import com.intellij.util.indexing.FileBasedIndex
import com.intellij.util.lang.UrlClassLoader
import com.intellij.util.lang.UrlClassLoader.build
import kotlinuiplugin.configuration.PreviewRun
import kotlinuiplugin.utils.Utils
import kotlinuiplugin.windows.PreviewToolWindow
import java.net.URL
import java.net.URLClassLoader
import java.util.*

@Service
class PreviewModuleService(var module: Module) {
    // https://intellij-support.jetbrains.com/hc/en-us/community/posts/115000615544-Intellij-Plugin-Load-class-object-from-source-project
    // http://tutorials.jenkov.com/java-reflection/dynamic-class-loading-reloading.html
    fun work(projectFile: VirtualFile) {
        val r = PreviewRun(module)
        r.compile { _ ->
            val extension = CompilerModuleExtension.getInstance(module)
            if (extension == null) {
                PreviewToolWindow.instance?.buildError("extension")
                return@compile
            }
            //val abb = ProjectFileIndex.getInstance(project).getClassRootForFile(projectFile)
            val klass = findKlass(projectFile, extension)

            Utils.createNotification("Compiled", "Compiled", module.project, NotificationType.INFORMATION, null)
        }
    }

    companion object {
        fun findKlass(projectFile: VirtualFile, extension: CompilerModuleExtension) {
            val loader = build()
                    .urls(URL(extension.compilerOutputUrl))
                    .get()
            val abc = loader.loadClass("com.example.myapplication.MyClass");
        }
    }
}