package com.kwonyj.github_search_repository.repository

import com.kwonyj.github_search_repository.data.remote.mapper.GithubRepoResponseMapper
import com.kwonyj.github_search_repository.data.remote.service.SearchService
import com.kwonyj.github_search_repository.model.GithubRepoResult
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class SearchRepositoryImpl @Inject constructor(
    private val searchService: SearchService,
    private val githubRepoResponseMapper: GithubRepoResponseMapper
) : SearchRepository {

    override fun searchRepository(keyword: String, page: Int): Single<GithubRepoResult> =
        searchService.searchRepository(keyword, page)
            .map { response ->
                githubRepoResponseMapper.fromRemote(response)
            }.subscribeOn(Schedulers.io())
}