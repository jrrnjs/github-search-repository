package com.kwonyj.github_search_repository.repository

import com.kwonyj.github_search_repository.model.GithubRepoResult
import io.reactivex.Single

interface SearchRepository {

    fun searchRepository(keyword: String, page: Int): Single<GithubRepoResult>
}