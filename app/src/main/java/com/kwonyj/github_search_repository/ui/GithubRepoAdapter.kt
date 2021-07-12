package com.kwonyj.github_search_repository.ui

import android.graphics.Color
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.kwonyj.github_search_repository.R
import com.kwonyj.github_search_repository.databinding.ViewholderRepositoryBinding
import com.kwonyj.github_search_repository.ext.binding
import com.kwonyj.github_search_repository.ext.initBinding
import com.kwonyj.github_search_repository.model.GithubRepo
import kotlin.random.Random

class GithubRepoAdapter :
    ListAdapter<GithubRepo, GithubRepoAdapter.RepositoryViewHolder>(diffUtil) {

    private var keyword: String = ""

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepositoryViewHolder {
        val binding = parent.binding<ViewholderRepositoryBinding>(R.layout.viewholder_repository)
        return RepositoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RepositoryViewHolder, position: Int) {
        holder.bind(getItem(position), keyword)
    }

    fun setSearchKeyword(keyword: String) {
        this.keyword = keyword
    }

    class RepositoryViewHolder(
        private val binding: ViewholderRepositoryBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(githubRepo: GithubRepo, searchKeyword: String) {
            binding.initBinding {
                this.githubRepo = githubRepo
                this.searchKeyword = searchKeyword
                executePendingBindings()
            }


            if (!githubRepo.language.isNullOrEmpty()) {
                val color = Color.rgb(Random.nextInt(256), Random.nextInt(256), Random.nextInt(256))
                binding.cvLang.setCardBackgroundColor(color)
            }
        }
    }

    companion object {
        private val diffUtil = object : DiffUtil.ItemCallback<GithubRepo>() {
            override fun areItemsTheSame(oldItem: GithubRepo, newItem: GithubRepo): Boolean =
                oldItem.url == newItem.url


            override fun areContentsTheSame(oldItem: GithubRepo, newItem: GithubRepo): Boolean =
                oldItem == newItem
        }
    }
}