package com.kwonyj.github_search_repository.binding

import android.graphics.Typeface
import android.text.Spanned
import android.text.style.StyleSpan
import android.widget.TextView
import androidx.core.text.toSpannable
import androidx.databinding.BindingAdapter

object TextViewBinding {

    @JvmStatic
    @BindingAdapter(value = ["bold_span_text", "bold_span_target"], requireAll = true)
    fun bindBoldSpan(view: TextView, text: String, target: String) {
        val start = text.indexOf(target, ignoreCase = true)

        view.text = if (start == -1) {
            text
        } else {
            text.toSpannable().apply {
                setSpan(
                    StyleSpan(Typeface.BOLD),
                    start,
                    start + target.length,
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                )
            }
        }
    }
}