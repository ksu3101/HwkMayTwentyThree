package com.swkang.hwkmaytwentythree.view.couple

import com.swkang.model.domain.hwk.CoupleDetailListState
import com.swkang.model.domain.hwk.CoupleListState
import com.swkang.model.domain.hwk.HwkCoupleNavigationHelper

class HwkCoupleNavigationHelperImpl(

): HwkCoupleNavigationHelper {

    override fun handle(state: CoupleListState) {
        when (state) {
            is CoupleDetailListState -> openCoupleDetailListPage()
        }
    }

    override fun openCoupleDetailListPage() {
    }

}