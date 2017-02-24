package xyz.fcampbell.programmertodo.repo

import io.reactivex.android.schedulers.AndroidSchedulers
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import xyz.fcampbell.programmertodo.ProgrammerTodo
import xyz.fcampbell.programmertodo.api.github.GitHubApi
import xyz.fcampbell.programmertodo.api.github.GitHubClient
import xyz.fcampbell.programmertodo.api.github.model.Repo

/**
 * Created by francois on 2017-02-24.
 */
class RepoListPresenter {
    private lateinit var view: View

    private val gson = ProgrammerTodo.gson
    private val gitHubApi by lazy {
        Retrofit.Builder()
                .baseUrl(GitHubApi.URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createAsync())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
                .create(GitHubApi::class.java)
    }
    private val gitHubClient by lazy { GitHubClient(gitHubApi) }//todo dagger

    fun attach(view: View) {
        this.view = view
    }

    fun getRepos() {
        view.onRepoLoadStart()
        gitHubClient.getMyRepos()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    view.onRepoLoadComplete(it)
                }, {
                    view.onRepoLoadError(it)
                })
    }

    interface View {
        fun onRepoLoadStart()
        fun onRepoLoadError(throwable: Throwable)
        fun onRepoLoadComplete(repos: List<Repo>)
    }
}