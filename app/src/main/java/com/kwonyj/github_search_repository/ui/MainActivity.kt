package com.kwonyj.github_search_repository.ui

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import com.kwonyj.github_search_repository.R
import com.kwonyj.github_search_repository.databinding.ActivityMainBinding
import com.kwonyj.github_search_repository.ext.initBinding
import com.kwonyj.github_search_repository.ext.toast
import com.kwonyj.github_search_repository.util.textChangedObservable
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
        }

        binding.etSearch.textChangedObservable()
            .debounce(1000, TimeUnit.MILLISECONDS)
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