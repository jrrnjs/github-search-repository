package com.kwonyj.github_search_repository.ui

import android.os.Bundle
import androidx.activity.viewModels
import com.kwonyj.github_search_repository.R
import com.kwonyj.github_search_repository.databinding.ActivityMainBinding
import com.kwonyj.github_search_repository.ext.initBinding
import com.kwonyj.github_search_repository.ext.toast
import com.kwonyj.github_search_repository.util.textChangedObservable
import com.kwonyj.github_search_repository.view.OnLoadMoreListener
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import retrofit2.HttpException
import java.net.SocketTimeoutException
import java.util.concurrent.TimeUnit

@AndroidEntryPoint
class MainActivity : BindingActivity<ActivityMainBinding>(R.layout.activity_main) {

    private val compositeDisposable: CompositeDisposable by lazy {
        CompositeDisposable()
    }
    private val viewModel by viewModels<MainViewModel>()
    private lateinit var loadMoreListener: OnLoadMoreListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.error.observe(this) { t ->
            when (t) {
                is HttpException -> {
                    when (t.code()) {
                        422 -> {
                            toast(R.string.error_unprocessable_entity)
                        }
                    }
                }
                is SocketTimeoutException -> {
                    toast(R.string.error_socket_time_exception)
                }
            }
        }

        binding.initBinding {
            lifecycleOwner = this@MainActivity
            viewModel = this@MainActivity.viewModel
        }

        binding.rvRepositories.run {
            adapter = GithubRepoAdapter()
            loadMoreListener = OnLoadMoreListener(
                onLoadMore = { page ->
                    viewModel.searchRepository(
                        binding.etSearch.text.toString(),
                        page
                    )
                }, isLoading = {
                    viewModel.isLoading.value ?: false
                }, isFinished = {
                    viewModel.isFinished
                }
            ).also { it.addTo(this) }
        }

        binding.etSearch.textChangedObservable()
            .debounce(1000, TimeUnit.MILLISECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { keyword ->
                (binding.rvRepositories.adapter as GithubRepoAdapter).setSearchKeyword(keyword)
                loadMoreListener.reset()
                viewModel.searchRepository(keyword)
            }.addTo(compositeDisposable)
    }

    override fun onDestroy() {
        if (!compositeDisposable.isDisposed) {
            compositeDisposable.dispose()
        }
        super.onDestroy()
    }
}