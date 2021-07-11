package com.kwonyj.github_search_repository.data.remote.model

import com.squareup.moshi.Json

data class GithubRepoModel(
    val id: String,
    @Json(name = "full_name")
    val fullName: String,
    val description: String,
    val owner: GithubRepoOwnerModel,
    @Json(name = "html_url")
    val url: String,
    @Json(name = "stargazers_count")
    val stars: Int,
    @Json(name = "forks_count")
    val forks: Int,
    val language: String?
)