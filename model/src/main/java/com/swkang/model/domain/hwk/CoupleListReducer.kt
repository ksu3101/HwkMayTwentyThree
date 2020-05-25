package com.swkang.model.domain.hwk

import com.swkang.model.base.redux.Action
import com.swkang.model.base.redux.Reducer

class CoupleListReducer(
    override val initializeState: CoupleListState
) : Reducer<CoupleListState> {

    override fun reduce(oldState: CoupleListState, action: Action): CoupleListState {
        return when (action) {
            is RetrieveFileOneHundredLinesAction,
            is RetrieveFileTenThousandLinesAction,
            is RetrieveFileFiveHundredThousandLinesAction -> {
                OnLoadingCoupleListState
            }

            is ReceivedCoupleLinesResultAction -> {
                CoupleListReceivedState(action.couples)
            }

            is DetailCoupleListAction -> {
                CoupleDetailListState(action.detailCouples)
            }

            else -> oldState
        }
    }
}