package kotlinuiplugin.actions.jiraAction

import kotlinuiplugin.actions.jiraAction.network.JiraService
import kotlinuiplugin.actions.jiraAction.network.Transition
import kotlinuiplugin.actions.jiraAction.network.TransitionData
import com.intellij.ide.IdeEventQueue
import com.intellij.openapi.project.Project
import com.intellij.util.Base64
import kotlinuiplugin.components.JiraComponent
import git4idea.repo.GitRepositoryManager
import hu.akarnokd.rxjava2.swing.SwingSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class JiraMoveDialogPresenter @Inject constructor(
    private val view: JiraMoveDialog,
    private val project: Project,
    private val component: JiraComponent,
    private val jiraService: JiraService
) {

    private var disposable: Disposable? = null
    private var issue: String = ""

    fun load() {
        getBranch()
        getTransitions()
    }

    private fun getBranch() {
        val repositoryManager = GitRepositoryManager.getInstance(project)

        try {
            val repository = repositoryManager.repositories.first()
            issue = repository.currentBranch!!.name

            val match = Regex(component.regex).find(issue)
            match?.let {
                issue = match.value
                view.setIssue(issue)
            }
        } catch (t: Throwable) {
            view.setIssue("Unknown")
        }
    }

    private fun getTransitions() {
        val auth = getAuthCode()
        disposable = jiraService.getTransitions(auth, issue)
            .subscribeOn(Schedulers.io())
            .observeOn(SwingSchedulers.edt())
            .subscribe(
                { response ->
                    view.setTransitions(response.transitions)
                },
                { error ->
                    view.error(error)
                }
            )
    }

    fun doTransition(selectedItem: Transition, issue: String) {
        val auth = getAuthCode()
        val transition = TransitionData(selectedItem)

        disposable = jiraService.doTransition(auth, issue, transition)
            .subscribeOn(Schedulers.io())
            .observeOn(SwingSchedulers.edt())
            .subscribe(
                {
                    view.success()
                },
                { error ->
                    view.error(error)
                }
            )
    }

    private fun getAuthCode(): String {
        val username = component.username
        val token = component.token
        val data: ByteArray = "$username:$token".toByteArray(Charsets.UTF_8)
        return "Basic ${Base64.encode(data)}"
    }
}