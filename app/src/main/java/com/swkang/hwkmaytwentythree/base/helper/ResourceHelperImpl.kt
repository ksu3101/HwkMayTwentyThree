package com.swkang.hwkmaytwentythree.base.helper

import android.app.Application
import com.swkang.model.base.helper.ResourceHelper

class ResourceHelperImpl(
    val context: Application
): ResourceHelper {

    override fun getString(stringResId: Int): String =
        context.getString(stringResId)

}