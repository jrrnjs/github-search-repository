package com.kwonyj.github_search_repository.ui

import android.os.Bundle
import androidx.activity.viewModels
import com.kwonyj.github_search_repository.R
import com.kwonyj.github_search_repository.databinding.ActivityMainBinding
import com.kwonyj.github_search_repository.ext.initBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BindingActivity<ActivityMainBinding>(R.layout.activity_main) {

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
    }
}