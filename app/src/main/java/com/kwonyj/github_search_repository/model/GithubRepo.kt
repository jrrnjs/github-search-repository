package com.kwonyj.github_search_repository.model

import android.os.Parcelable
import com.kwonyj.github_search_repository.ext.prettyCount
import kotlinx.parcelize.Parcelize

@Parcelize
data class GithubRepo(
    val fullName: String,
    val description: String?,
    val owner: GithubRepoOwner,
    val url: String,
    val stars: Int,
    val forks: Int,
    val language: String?
) : Parcelable {

    fun starsPrettyPrint(): String = stars.prettyCount()
    fun forksPrettyPrint(): String = forks.prettyCount()
}