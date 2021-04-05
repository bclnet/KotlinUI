package kotlinuiplugin.actions.jiraAction.di

import kotlinuiplugin.actions.jiraAction.JiraMoveDialog
import dagger.Component

@Component(modules = [JiraModule::class])
interface JiraDIComponent {
    fun inject(jiraMoveDialog: JiraMoveDialog)
}