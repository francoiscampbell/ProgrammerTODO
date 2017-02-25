package xyz.fcampbell.programmertodo.repo

import android.preference.PreferenceManager
import io.reactivex.android.schedulers.AndroidSchedulers
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import xyz.fcampbell.programmertodo.ProgrammerTodo
import xyz.fcampbell.programmertodo.api.github.GitHubApi
import xyz.fcampbell.programmertodo.api.github.GitHubClient
import xyz.fcampbell.programmertodo.api.github.model.Repo
import xyz.fcampbell.programmertodo.auth.TokenCache

/**
 * Created by francois on 2017-02-24.
 */
class RepoListPresenter {
    private lateinit var view: View

    //todo move to client
    private val gitHubTokenCache = TokenCache(
            PreferenceManager.getDefaultSharedPreferences(ProgrammerTodo.application),
            ProgrammerTodo.gson,
            TokenCache.Service.GITHUB) //todo dagger
    private val gitHubOkHttpClient = OkHttpClient.Builder()
            .addNetworkInterceptor { chain ->
                val proceedRequest = if (chain.request().headers("Authorization").isEmpty()) {
                    chain.request()
                            .newBuilder()
                            .addHeader("Authorization", "token ${gitHubTokenCache.get()?.token ?: ""}")
                            .build()
                } else {
                    chain.request()
                }
                chain.proceed(proceedRequest)
            }.build()
    private val gson = ProgrammerTodo.gson
    private val gitHubApi by lazy {
        Retrofit.Builder()
                .baseUrl(GitHubApi.URL)
                .client(gitHubOkHttpClient)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createAsync())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
                .create(GitHubApi::class.java)
    }
    private val gitHubClient by lazy { GitHubClient(gitHubApi, gitHubTokenCache) }//todo dagger

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