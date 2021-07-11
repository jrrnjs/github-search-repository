package com.kwonyj.github_search_repository.data.remote.mapper

import com.kwonyj.github_search_repository.data.remote.model.GithubRepoResponse
import com.kwonyj.github_search_repository.model.GithubRepoResult
import javax.inject.Inject

class GithubRepoResponseMapper @Inject constructor(
    private val githubRepoModelMapper: GithubRepoModelMapper
) : Mapper<GithubRepoResponse, GithubRepoResult> {
    override fun fromRemote(model: GithubRepoResponse): GithubRepoResult =
        with(model) {
            GithubRepoResult(
                totalCount,
                items.map { repoModel ->
                    githubRepoModelMapper.fromRemote(repoModel)
                }
            )
        }
}