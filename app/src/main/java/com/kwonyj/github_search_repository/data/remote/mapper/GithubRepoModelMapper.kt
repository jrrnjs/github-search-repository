package com.kwonyj.github_search_repository.data.remote.mapper

import com.kwonyj.github_search_repository.data.remote.model.GithubRepoModel
import com.kwonyj.github_search_repository.model.GithubRepo
import javax.inject.Inject

class GithubRepoModelMapper @Inject constructor(
    private val githubRepoOwnerModelMapper: GithubRepoOwnerModelMapper
) : Mapper<GithubRepoModel, GithubRepo> {
    override fun fromRemote(model: GithubRepoModel): GithubRepo =
        with(model) {
            GithubRepo(
                fullName,
                description,
                githubRepoOwnerModelMapper.fromRemote(owner),
                url,
                stars,
                forks,
                language
            )
        }
}