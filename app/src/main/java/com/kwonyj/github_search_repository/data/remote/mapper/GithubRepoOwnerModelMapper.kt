package com.kwonyj.github_search_repository.data.remote.mapper

import com.kwonyj.github_search_repository.data.remote.model.GithubRepoOwnerModel
import com.kwonyj.github_search_repository.model.GithubRepoOwner
import javax.inject.Inject

class GithubRepoOwnerModelMapper @Inject constructor(
) : Mapper<GithubRepoOwnerModel, GithubRepoOwner> {
    override fun fromRemote(model: GithubRepoOwnerModel): GithubRepoOwner =
        with(model) {
            GithubRepoOwner(avatarUrl)
        }
}