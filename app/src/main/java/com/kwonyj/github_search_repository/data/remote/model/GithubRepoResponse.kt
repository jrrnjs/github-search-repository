package com.kwonyj.github_search_repository.data.remote.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class GithubRepoResponse(
    @Json(name = "total_count")
    val totalCount: Int,
    val items: List<GithubRepoModel>
)