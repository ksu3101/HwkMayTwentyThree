package com.swkang.model.domain.hwk.coupledetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.swkang.model.base.BaseViewModel
import com.swkang.model.domain.hwk.CoupleListState
import com.swkang.model.domain.hwk.dto.Couple

class CoupleDetailViewModel(
    val couples: List<Couple>
) : BaseViewModel<CoupleListState>() {

    private val _coupleDetails = MutableLiveData<List<Couple>>(listOf())
    val coupleDetails: LiveData<List<Couple>>
        get() = _coupleDetails

    override fun render(state: CoupleListState): Boolean {
        return true
    }

}