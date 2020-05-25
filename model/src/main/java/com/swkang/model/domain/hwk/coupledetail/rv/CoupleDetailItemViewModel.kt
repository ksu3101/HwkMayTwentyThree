package com.swkang.model.domain.hwk.coupledetail.rv

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.swkang.model.domain.hwk.dto.Couple

class CoupleDetailItemViewModel(
    couple: Couple
) : ViewModel() {

    private val _firstIndex = MutableLiveData(couple.i1.toString())
    val firstIndex: LiveData<String>
        get() = _firstIndex

    private val _secondIndex = MutableLiveData(couple.i2.toString())
    val secondIndex: LiveData<String>
        get() = _secondIndex

    private val _firstItems = MutableLiveData(couple.items1)
    val firstItems: LiveData<List<String>>
        get() = _firstItems

    private val _secondItems = MutableLiveData(couple.items2)
    val secondItems: LiveData<List<String>>
        get() = _secondItems

}
