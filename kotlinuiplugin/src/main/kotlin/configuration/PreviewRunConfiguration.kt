package kotlinuiplugin.configuration
// https://plugins.jetbrains.com/docs/intellij/run-configurations.html#implement-configurationtype

import com.intellij.execution.*
import com.intellij.execution.configurations.*
import com.intellij.execution.executors.DefaultDebugExecutor
import com.intellij.execution.executors.DefaultRunExecutor
import com.intellij.execution.impl.ExecutionManagerImpl
import com.intellij.execution.runners.ExecutionEnvironment
import com.intellij.execution.runners.ExecutionEnvironmentBuilder
import com.intellij.execution.runners.ProgramRunner
import com.intellij.openapi.project.Project
import com.intellij.openapi.options.SettingsEditor
import com.intellij.openapi.ui.*
import com.intellij.icons.AllIcons
import com.intellij.notification.NotificationType
import com.intellij.openapi.compiler.CompileContext
import com.intellij.openapi.compiler.CompileStatusNotification
import com.intellij.openapi.compiler.CompilerManager
import com.intellij.openapi.module.Module
import com.intellij.openapi.roots.CompilerModuleExtension
import com.intellij.util.lang.UrlClassLoader
import kotlinuiplugin.utils.Utils
import kotlinuiplugin.windows.PreviewToolWindow
import org.apache.batik.svggen.font.table.Program
import org.jetbrains.jps.incremental.storage.BuildDataManager
import java.lang.Exception
import java.net.URL
import java.net.URLClassLoader
import java.util.*
import javax.swing.*

class PreviewRunConfigurationType : ConfigurationType {
    override fun getDisplayName(): String = "KotlinUI App"
    override fun getConfigurationTypeDescription(): String = "KotlinUI App Run Configuration Type"
    override fun getIcon(): Icon = AllIcons.General.Information
    override fun getId(): String = "PreviewRunConfiguration"
    override fun getConfigurationFactories(): Array<ConfigurationFactory> = arrayOf(PreviewConfigurationFactory(this))

    companion object {
        val instance = PreviewRunConfigurationType()
    }
}

class PreviewConfigurationFactory(type: ConfigurationType) : ConfigurationFactory(type) {
    override fun createTemplateConfiguration(project: Project): RunConfiguration =
            PreviewRunConfiguration(project, this, "KotlinUI App")

    override fun getName(): String = "Preview configuration factory"
    override fun getId(): String = "PreviewConfigurationFactory"
}

class PreviewRunConfiguration(project: Project, factory: ConfigurationFactory?, name: String?) :
        RunConfigurationBase<Any?>(project, factory, name) {
    override fun getConfigurationEditor(): SettingsEditor<out RunConfiguration?> = PreviewSettingsEditor()
    override fun checkConfiguration() {}
    override fun getState(executor: Executor, executionEnvironment: ExecutionEnvironment): RunProfileState? = null
}

class PreviewSettingsEditor : SettingsEditor<PreviewRunConfiguration?>() {
    private val myPanel: JPanel? = null
    private var myMainClass: LabeledComponent<ComponentWithBrowseButton<*>>? = null
    override fun resetEditorFrom(demoRunConfiguration: PreviewRunConfiguration) {}
    override fun applyEditorTo(demoRunConfiguration: PreviewRunConfiguration) {}
    override fun createEditor(): JComponent = myPanel!!
    private fun createUIComponents() {
        myMainClass = LabeledComponent()
        myMainClass!!.component = TextFieldWithBrowseButton()
    }
}

// https://www.programcreek.com/java-api-examples/?api=com.intellij.openapi.module.Module #addProjectConfiguration
class PreviewRun(var module: Module) { // Runnable
    private fun getSettings(runManager: RunManager, add: Boolean): RunnerAndConfigurationSettings {
        var settings = runManager.findConfigurationByName("Preview")
        if (settings == null) {
            settings = runManager.createConfiguration(module.name, PreviewRunConfigurationType.instance.configurationFactories[0])
            val configuration = settings.configuration as PreviewRunConfiguration
            configuration.name = "Preview"
            settings.storeInLocalWorkspace()
            if (add)
                runManager.addConfiguration(settings)
        }
        return settings
    }

    fun compile(callback: (CompileContext) -> Unit) { //  { aborted, errors, warnings, compileContext ->
//        BuildJarProjectSettings.getInstance(module.project)
        val compilerManager = CompilerManager.getInstance(module.project)
        compilerManager.make { aborted, errors, _, compileContext ->
            if (aborted || errors > 0)
                PreviewToolWindow.instance?.buildError("message") //aborted ? "aborted" : "errors")
            else
                callback(compileContext)
        }
    }

//    fun compile1() {
//        val runManager = RunManager.getInstance(module.project)
//        val settings = runManager.selectedConfiguration!!
//        val executor = DefaultRunExecutor.getRunExecutorInstance()
//        //
//        val builder: ExecutionEnvironmentBuilder
//        try {
//            builder = ExecutionEnvironmentBuilder.create(executor, settings)
//        } catch (ex: ExecutionException) {
//            Utils.createNotification(
//                "Compile Error",
//                ex.message ?: "Error",
//                module.project,
//                NotificationType.ERROR,
//                null
//            )
//            return
//        }
//        val environment = builder.contentToReuse(null).dataContext(null).activeTarget().build()
//        var name = environment.runProfile.name
//        //val manager = ExecutionManager.getInstance(environment.project) as ExecutionManagerImpl
//        val project = environment.project.projectFilePath
//    }
//
//    override fun run() {
//        val runManager = RunManager.getInstance(module.project)
////        runManager.selectedConfiguration = foundSettings
////        val lastSettings = runManager.selectedConfiguration
//        val settings = runManager.selectedConfiguration!! //getSettings(runManager, true)
//        val executor = DefaultDebugExecutor.getDebugExecutorInstance() // DefaultRunExecutor.getRunExecutorInstance()
//        try {
//            ProgramRunnerUtil.executeConfiguration(settings, executor)
//        } catch (ex: ExecutionException) {
//            Utils.createNotification(
//                "Execution Error",
//                ex.message ?: "Error",
//                module.project,
//                NotificationType.ERROR,
//                null
//            )
//        }
////        runManager.selectedConfiguration = lastSettings
//    }
}