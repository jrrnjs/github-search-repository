package com.kwonyj.github_search_repository.data.remote.mapper

interface Mapper<in FROM, out TO> {

    fun fromRemote(model: FROM): TO
}