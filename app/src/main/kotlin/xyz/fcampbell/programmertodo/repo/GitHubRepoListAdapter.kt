package xyz.fcampbell.programmertodo.repo

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.item_view_github_repo.view.*
import xyz.fcampbell.programmertodo.R
import xyz.fcampbell.programmertodo.api.github.model.Repo

/**
 * Created by francois on 2017-02-24.
 */
class GitHubRepoListAdapter(
        var repos: List<Repo>
) : RecyclerView.Adapter<GitHubRepoListAdapter.ViewHolder>() {
    override fun getItemCount() = repos.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bindRepo(repos[position])

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_view_github_repo, parent, false))
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindRepo(repo: Repo) {
            itemView.name.text = repo.name
        }
    }
}