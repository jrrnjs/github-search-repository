package com.kwonyj.github_search_repository.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import com.kwonyj.github_search_repository.data.remote.service.SearchService
import com.kwonyj.github_search_repository.model.GithubRepo
import com.kwonyj.github_search_repository.model.GithubRepoResult
import com.kwonyj.github_search_repository.repository.SearchRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.addTo
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val searchRepository: SearchRepository
) : BaseViewModel() {

    // page to result
    private val _githubRepoResult: MutableLiveData<Pair<Int, GithubRepoResult>> = MutableLiveData()

    val totalCount: LiveData<Int> = _githubRepoResult.map { it.second.totalCount }
    val githubRepoList: LiveData<List<GithubRepo>> = MediatorLiveData<List<GithubRepo>>().apply {
        addSource(_githubRepoResult) {
            val page = it.first
            value = when (page) {
                1 -> it.second.items
                else -> ArrayList(value ?: listOf()).apply { addAll(it.second.items) }
            }
        }
    }

    private val _error: MutableLiveData<Throwable> = MutableLiveData()
    val error: LiveData<Throwable>
        get() = _error

    var isFinished: Boolean = false
        private set

    fun searchRepository(keyword: String, page: Int = 1) {
        searchRepository.searchRepository(keyword, page)
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { _isLoading.value = true }
            .doOnEvent { t1, t2 -> _isLoading.value = false }
            .subscribe({
                _githubRepoResult.value = page to it
                isFinished = it.items.size < SearchService.PER_PAGE
            }, {
                _githubRepoResult.value = 1 to GithubRepoResult.EMPTY
                _error.value = it
            }).addTo(compositeDisposable)
    }
}
