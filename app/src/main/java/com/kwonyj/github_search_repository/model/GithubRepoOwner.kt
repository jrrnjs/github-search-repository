package com.kwonyj.github_search_repository.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class GithubRepoOwner(
    val avatarUrl: String
) : Parcelable