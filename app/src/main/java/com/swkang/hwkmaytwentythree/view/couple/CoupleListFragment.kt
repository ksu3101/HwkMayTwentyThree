package com.swkang.hwkmaytwentythree.view.couple

import com.swkang.hwkmaytwentythree.R
import com.swkang.hwkmaytwentythree.base.BaseFragment
import com.swkang.model.domain.hwk.CoupleListState

class CoupleListFragment : BaseFragment<CoupleListState>() {

    companion object {
        fun newInstance(): CoupleListFragment {
            return CoupleListFragment()
        }
    }

    override fun getLayoutResId(): Int = R.layout.hwk_list_frag

}