package com.swkang.hwkmaytwentythree.view.couple

import com.swkang.hwkmaytwentythree.R
import com.swkang.hwkmaytwentythree.base.BaseFragment
import com.swkang.model.domain.hwk.CoupleListState

class CoupleDetailListFragment : BaseFragment<CoupleListState>() {

    companion object {
        fun newInstance(): CoupleDetailListFragment {
            return CoupleDetailListFragment()
        }
    }

    override fun getLayoutResId(): Int = R.layout.hwk_detail_frag

}