package com.swkang.model.base.exts.livedata

import androidx.lifecycle.MutableLiveData

fun MutableLiveData<Boolean>.setTrue() {
    this.value = true
}

fun MutableLiveData<Boolean>.setFalse() {
    this.value = false
}