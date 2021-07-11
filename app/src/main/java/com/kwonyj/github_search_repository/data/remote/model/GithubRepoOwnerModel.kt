package com.kwonyj.github_search_repository.data.remote.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class GithubRepoOwnerModel(
    val id: String,
    val login: String,
    @Json(name = "avatar_url")
    val avatarUrl: String,
)