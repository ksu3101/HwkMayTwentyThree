package com.swkang.model.domain.hwk.couples.rv

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CoupleListItemViewModel(
    intersectionStr: String
) : ViewModel() {

    private val _intersection = MutableLiveData(intersectionStr)
    val intersection: LiveData<String>
        get() = _intersection

}
