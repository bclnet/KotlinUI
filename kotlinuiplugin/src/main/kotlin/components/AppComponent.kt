package kotlinuiplugin.components

import com.intellij.ide.plugins.PluginManagerCore
import com.intellij.notification.NotificationType
import com.intellij.openapi.components.*
import com.intellij.openapi.extensions.PluginId
import com.intellij.util.xmlb.XmlSerializerUtil
import com.intellij.util.xmlb.annotations.Attribute
import kotlinuiplugin.utils.Utils

@State(name = "KUConfiguration", storages = [Storage(value = "kuConfiguration.xml")])
class AppComponent : ApplicationComponent, PersistentStateComponent<AppComponent> {

    @Attribute
    private var localVersion: String = "0.0"
    private lateinit var version: String

    override fun initComponent() {
        super.initComponent()

        version = PluginManagerCore.getPlugin(PluginId.getId("kotlinx.KotlinUIPlugin"))!!.version

        if (isANewVersion()) {
            updateLocalVersion()
            Utils.createNotification(
                Utils.message("plugin.updated"),
                Utils.message("plugin.new.message"),
                null,
                NotificationType.INFORMATION,
                null
            )
        }
    }

    override fun getState(): AppComponent = this

    override fun loadState(state: AppComponent) = XmlSerializerUtil.copyBean(state, this)

    private fun isANewVersion(): Boolean {
        val s1 = localVersion.split("-")[0].split(".")
        val s2 = version.split("-")[0].split(".")

        if (s1.size != s2.size) return false
        var i = 0

        do {
            if (s1[i] < s2[i]) return true
            i++
        } while (i < s1.size && i < s2.size)

        return false
    }

    private fun updateLocalVersion() {
        localVersion = version
    }
}