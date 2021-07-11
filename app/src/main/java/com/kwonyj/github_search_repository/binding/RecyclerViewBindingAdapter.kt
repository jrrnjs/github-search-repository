package com.kwonyj.github_search_repository.binding

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

object RecyclerViewBindingAdapter {

    @JvmStatic
    @BindingAdapter("bind:submitList")
    fun bindSubmitList(view: RecyclerView, items: List<Any>?) {
        (view.adapter as? ListAdapter<Any, *>)?.submitList(items)
    }
}