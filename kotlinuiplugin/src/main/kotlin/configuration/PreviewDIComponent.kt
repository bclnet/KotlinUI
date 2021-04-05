package kotlinuiplugin.configuration

import kotlinuiplugin.actions.PreviewBuilder
import dagger.Component

@Component(modules = [PreviewModule::class])
interface PreviewDIComponent {
    fun inject(previewBuilder: PreviewBuilder)
}