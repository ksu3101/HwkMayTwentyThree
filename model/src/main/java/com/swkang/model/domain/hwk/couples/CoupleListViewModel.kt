package com.swkang.model.domain.hwk.couples

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.swkang.model.base.BaseViewModel
import com.swkang.model.base.exts.livedata.setFalse
import com.swkang.model.base.exts.livedata.setTrue
import com.swkang.model.base.redux.AppStore
import com.swkang.model.domain.hwk.*
import com.swkang.model.domain.hwk.dto.Couple

class CoupleListViewModel(
    private val appStore: AppStore
) : BaseViewModel<CoupleListState>() {

    private lateinit var processedCouples: Map<String, List<Couple>>

    private val _couples = MutableLiveData<List<String>>(listOf())
    val couples: LiveData<List<String>>
        get() = _couples

    private val _onLoading = MutableLiveData(false)
    val onLoading: LiveData<Boolean>
        get() = _onLoading

    val onItemClicked = { clickedItem: String ->
        if (::processedCouples.isInitialized) {
            val couplesOfClickedItem = processedCouples[clickedItem]
            appStore.dispatch(DetailCoupleListAction(couplesOfClickedItem!!))
        }
    }

    init {
        appStore.dispatch(RetrieveFileOneHundredLinesAction)
    }

    override fun render(state: CoupleListState): Boolean {
        return when (state) {
            is OnLoadingCoupleListState -> {
                _couples.value = listOf()
                _onLoading.setTrue()
                true
            }
            is CoupleListReceivedState -> {
                processedCouples = state.couples
                _couples.value = state.couples.keys.toList()
                _onLoading.setFalse()
                true
            }
            else -> false
        }
    }

}