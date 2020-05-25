package com.swkang.model.domain.hwk

import com.swkang.model.base.helper.NavigationHelper

interface HwkCoupleNavigationHelper: NavigationHelper<CoupleListState> {
    fun openCoupleDetailListPage()
}