package kotlinuiplugin.components

import com.intellij.openapi.components.*
import com.intellij.openapi.project.Project
import com.intellij.util.xmlb.XmlSerializerUtil
import java.io.Serializable

@State(name = "KUPConfiguration", storages = [Storage(value = "kupConfiguration.xml")])
class ProjectComponent(project: Project? = null) :
    AbstractProjectComponent(project),
    Serializable,
    PersistentStateComponent<ProjectComponent> {

    var username: String = ""
    var token: String = ""
    var url: String = ""
    var regex: String = ""

    override fun getState(): ProjectComponent = this

    override fun loadState(state: ProjectComponent) =
        XmlSerializerUtil.copyBean(state, this)

    companion object {
        fun getInstance(project: Project): ProjectComponent =
            project.getComponent(ProjectComponent::class.java)
    }
}
