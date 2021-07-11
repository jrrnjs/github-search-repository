package com.kwonyj.github_search_repository.data.remote.service

import com.kwonyj.github_search_repository.data.remote.model.GithubRepoResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchService {

    companion object {
        const val PER_PAGE = 30
    }

    @GET("search/repositories")
    fun searchRepository(
        @Query("q") query: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int = PER_PAGE
    ): Single<GithubRepoResponse>
}