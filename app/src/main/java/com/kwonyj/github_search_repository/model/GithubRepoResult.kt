package com.kwonyj.github_search_repository.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class GithubRepoResult(
    val totalCount: Int,
    val items: List<GithubRepo>
) : Parcelable