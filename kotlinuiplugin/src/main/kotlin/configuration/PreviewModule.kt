package kotlinuiplugin.configuration

import kotlinuiplugin.actions.PreviewBuilder
import com.intellij.openapi.project.Project
import dagger.Module
import dagger.Provides
import kotlinuiplugin.components.ProjectComponent

@Module
class PreviewModule(
    private val builder: PreviewBuilder,
    private val project: Project
) {
    @Provides
    fun provideBuilder(): PreviewBuilder = builder

    @Provides
    fun provideProject(): Project = project

    @Provides
    fun provideComponent(): ProjectComponent =
        ProjectComponent.getInstance(project)

//    @Provides
//    fun providesJiraService(component: JiraComponent): JiraService {
//        val jiraURL = component.url
//        val retrofit = Retrofit.Builder()
//            .addConverterFactory(GsonConverterFactory.create())
//            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
//            .baseUrl(jiraURL)
//            .build()
//
//        return retrofit.create(JiraService::class.java)
//    }
}