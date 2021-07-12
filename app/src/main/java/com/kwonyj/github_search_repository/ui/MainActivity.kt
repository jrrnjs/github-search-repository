package com.kwonyj.github_search_repository.ui

import android.os.Bundle
import androidx.activity.viewModels
import com.kwonyj.github_search_repository.R
import com.kwonyj.github_search_repository.databinding.ActivityMainBinding
import com.kwonyj.github_search_repository.ext.initBinding
import com.kwonyj.github_search_repository.util.textChangedObservable
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import java.util.concurrent.TimeUnit

@AndroidEntryPoint
class MainActivity : BindingActivity<ActivityMainBinding>(R.layout.activity_main) {

    private val compositeDisposable: CompositeDisposable by lazy {
        CompositeDisposable()
    }
    private val viewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.initBinding {
            lifecycleOwner = this@MainActivity
            viewModel = this@MainActivity.viewModel
        }

        binding.rvRepositories.run {
            adapter = GithubRepoAdapter()
        }

        binding.etSearch.textChangedObservable()
            .debounce(500, TimeUnit.MILLISECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { keyword ->
                (binding.rvRepositories.adapter as GithubRepoAdapter).setSearchKeyword(keyword)
                viewModel.searchRepository(keyword, 1)
            }.addTo(compositeDisposable)
    }

    override fun onDestroy() {
        if (!compositeDisposable.isDisposed) {
            compositeDisposable.dispose()
        }
        super.onDestroy()
    }
}