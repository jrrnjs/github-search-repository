package com.kwonyj.github_search_repository.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
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

    private val _githubRepoResult: MutableLiveData<GithubRepoResult> = MutableLiveData()
    val totalCount: LiveData<Int> = _githubRepoResult.map { it.totalCount }
    val githubRepoList: LiveData<List<GithubRepo>> = _githubRepoResult.map { it.items }

    private val _error: MutableLiveData<Throwable> = MutableLiveData()
    val error: LiveData<Throwable>
        get() = _error

    fun searchRepository(keyword: String, page: Int = 1) {
        searchRepository.searchRepository(keyword, page)
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { _isLoading.value = true }
            .doOnEvent { t1, t2 -> _isLoading.value = false }
            .subscribe(_githubRepoResult::setValue) {
                _githubRepoResult.value = GithubRepoResult.EMPTY
                _error.value = it
            }.addTo(compositeDisposable)
    }
}