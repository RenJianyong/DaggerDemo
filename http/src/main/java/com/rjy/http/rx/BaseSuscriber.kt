package com.rjy.http.rx

import rx.Subscriber

open class BaseSuscriber<T> : Subscriber<T>() {
    override fun onNext(t: T) {

    }

    override fun onCompleted() {
    }

    override fun onError(e: Throwable?) {
    }
}