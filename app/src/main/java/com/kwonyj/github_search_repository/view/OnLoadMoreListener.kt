package com.kwonyj.github_search_repository.view

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class OnLoadMoreListener(
    private val onLoadMore: (Int) -> Unit,
    private val isLoading: () -> Boolean,
    private val isFinished: () -> Boolean
) : RecyclerView.OnScrollListener() {

    var threshold: Int = 5
    private var page: Int = 1

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)

        recyclerView.layoutManager?.let { layoutManager ->
            val totalCount = layoutManager.itemCount
            val lastVisiblePosition = when (layoutManager) {
                is LinearLayoutManager -> layoutManager.findLastVisibleItemPosition()
                is GridLayoutManager -> layoutManager.findLastVisibleItemPosition()
                else -> return
            }

            if (isLoading() || isFinished()) return

            if (lastVisiblePosition + threshold >= totalCount) {
                onLoadMore(++page)
            }
        }
    }

    fun reset() {
        page = 1
    }

    fun addTo(recyclerView: RecyclerView) {
        recyclerView.addOnScrollListener(this)
    }
}