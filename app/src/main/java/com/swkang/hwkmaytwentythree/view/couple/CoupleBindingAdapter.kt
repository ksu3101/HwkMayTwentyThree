package com.swkang.hwkmaytwentythree.view.couple

import androidx.databinding.BindingAdapter
import androidx.databinding.ObservableArrayList
import androidx.recyclerview.widget.RecyclerView
import com.swkang.hwkmaytwentythree.R
import com.swkang.hwkmaytwentythree.base.databinding.recyclerview.bindItems
import com.swkang.hwkmaytwentythree.base.databinding.recyclerview.setVerticalLayoutManager
import com.swkang.model.domain.hwk.couples.rv.CoupleListItemViewModel

@BindingAdapter(value = ["coupleItems", "onCoupleItemClicked"])
fun bindCoupleItems(rv: RecyclerView, items: List<String>, onItemClicked: (String) -> Unit) {
    bindItems<CoupleListItemViewModel>(
        rv,
        ObservableArrayList<CoupleListItemViewModel>().apply {
            addAll(items.map {
                CoupleListItemViewModel(
                    it
                )
            })
        },
        R.layout.hwk_list_rvitemv
    ) {
        onItemClicked(it.intersection.value!!)
    }
    rv.setHasFixedSize(true)
    setVerticalLayoutManager(rv, true)
}