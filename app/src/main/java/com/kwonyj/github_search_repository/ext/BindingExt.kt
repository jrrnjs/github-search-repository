package com.kwonyj.github_search_repository.ext

import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

/**
 * activity binding
 */
fun <T : ViewDataBinding> Activity.binding(
    @LayoutRes layoutResId: Int
): T = DataBindingUtil.setContentView(this, layoutResId)

/**
 * viewGroup binding (viewHolder)
 */
fun <T : ViewDataBinding> ViewGroup.binding(
    @LayoutRes layoutResId: Int
): T = DataBindingUtil.inflate(LayoutInflater.from(context), layoutResId, this, false)

inline fun <T : ViewDataBinding> T.initBinding(initBlock: T.() -> Unit) =
    run(initBlock)