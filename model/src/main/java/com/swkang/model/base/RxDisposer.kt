package com.swkang.model.base

import io.reactivex.disposables.Disposable


interface RxDisposer {
    fun addDisposer(disposable: Disposable)

    fun dispose()
}