package com.kwonyj.github_search_repository.util

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import java.util.concurrent.atomic.AtomicBoolean

class TextChangedObservable(
    private val editText: EditText
) : Observable<String>() {

    override fun subscribeActual(observer: Observer<in String>) {
        val listener = Listener(editText, observer)
        observer.onSubscribe(listener)
        editText.addTextChangedListener(listener)
    }

    private class Listener(
        private val editText: EditText,
        private val observer: Observer<in String>
    ) : Disposable, TextWatcher {

        private val unsubscribed = AtomicBoolean()

        override fun dispose() {
            if (unsubscribed.compareAndSet(false, true)) {
                editText.removeTextChangedListener(this)
            }
        }

        override fun isDisposed(): Boolean = unsubscribed.get()

        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            if (!isDisposed) {
                observer.onNext(p0.toString())
            }
        }

        override fun afterTextChanged(p0: Editable?) {
        }
    }
}

fun EditText.textChangedObservable(): Observable<String> = TextChangedObservable(this)