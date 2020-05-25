package com.swkang.model.domain.hwk

import com.swkang.model.base.redux.State
import com.swkang.model.domain.hwk.dto.Couple

sealed class CoupleListState : State

object InitializedState: CoupleListState()

object OnLoadingCoupleListState : CoupleListState()

data class CoupleListReceivedState(
    val couples: Map<String, List<Couple>>
) : CoupleListState()

data class CoupleDetailListState(
    val detailCouples: List<Couple>
): CoupleListState()
