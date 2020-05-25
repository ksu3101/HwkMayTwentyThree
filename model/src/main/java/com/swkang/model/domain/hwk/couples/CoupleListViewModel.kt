package com.swkang.model.domain.hwk.couples

import android.util.Log
import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableList
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.swkang.common.LOG_TAG
import com.swkang.model.base.BaseViewModel
import com.swkang.model.base.exts.livedata.setFalse
import com.swkang.model.base.exts.livedata.setTrue
import com.swkang.model.base.redux.AppStore
import com.swkang.model.domain.hwk.*
import com.swkang.model.domain.hwk.couples.rv.CoupleListItemViewModel
import com.swkang.model.domain.hwk.dto.Couple

class CoupleListViewModel(
    private val appStore: AppStore
) : BaseViewModel<CoupleListState>() {

    private lateinit var processedCouples: Map<String, List<Couple>>

    private val _couples = MutableLiveData<ObservableList<CoupleListItemViewModel>>(ObservableArrayList())
    val couples: LiveData<ObservableList<CoupleListItemViewModel>>
        get() = _couples

    private val _onLoading = MutableLiveData(false)
    val onLoading: LiveData<Boolean>
        get() = _onLoading

    val onItemClicked = { clickedItem: CoupleListItemViewModel ->
        if (::processedCouples.isInitialized) {
            val couplesOfClickedItem = processedCouples[clickedItem.intersection.value]
            appStore.dispatch(DetailCoupleListAction(couplesOfClickedItem!!))
            Log.e(LOG_TAG, "////////// couplesOfClickedItem = ${couplesOfClickedItem ?: "ERROR"}")
        }
    }

    init {
        appStore.dispatch(RetrieveFileOneHundredLinesAction)
    }

    override fun render(state: CoupleListState): Boolean {
        return when (state) {
            is OnLoadingCoupleListState -> {
                _couples.value?.clear()
                _onLoading.setTrue()
                true
            }
            is CoupleListReceivedState -> {
                processedCouples = state.couples
                _couples.value?.addAll(state.couples.keys.map { CoupleListItemViewModel(it) })
                _onLoading.setFalse()
                true
            }
            else -> false
        }
    }

}