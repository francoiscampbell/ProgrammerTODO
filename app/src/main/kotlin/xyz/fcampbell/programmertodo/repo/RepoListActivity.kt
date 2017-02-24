package xyz.fcampbell.programmertodo.repo

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_repo_list.*
import xyz.fcampbell.programmertodo.R
import xyz.fcampbell.programmertodo.api.github.model.Repo

class RepoListActivity : AppCompatActivity(), RepoListPresenter.View {
    companion object {
        const val TAG = "RepoListActivity"
        fun start(context: Context) = context.startActivity(Intent(context, RepoListActivity::class.java))
        fun transitionTo(from: Activity) {
            start(from)
            from.finish()
        }
    }

    private val presenter = RepoListPresenter().apply { attach(this@RepoListActivity) } //todo dagger

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_repo_list)

        setupList()
        presenter.getRepos()
    }

    fun setupList() {
        repoList.apply {
            layoutManager = LinearLayoutManager(this@RepoListActivity, LinearLayoutManager.VERTICAL, false)
            adapter = GitHubRepoListAdapter(listOf())
        }
    }

    override fun onRepoLoadStart() {
    }

    override fun onRepoLoadError(throwable: Throwable) {
    }

    override fun onRepoLoadComplete(repos: List<Repo>) {
        (repoList.adapter as GitHubRepoListAdapter).apply {
            this.repos = repos
            notifyDataSetChanged()
        }
    }
}
