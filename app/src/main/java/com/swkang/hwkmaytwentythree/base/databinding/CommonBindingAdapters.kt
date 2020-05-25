package com.swkang.hwkmaytwentythree.base.databinding

import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

@BindingAdapter("android:visibility")
fun setVisibility(view: View, visibility: Boolean) {
    view.visibility = if (visibility) View.VISIBLE else View.GONE
}

@BindingAdapter("imageUrl")
fun loadImageUrl(iv: ImageView, url: String?) {
    if (url.isNullOrEmpty()) {
        iv.setImageDrawable(null)
    } else {
        Glide.with(iv.context)
            .load(url)
            .into(iv)
    }
}

@BindingAdapter("onClicked")
fun onClickedEventHandler(view: View, handler: () -> Unit) {
    view.setOnClickListener { handler() }
}
