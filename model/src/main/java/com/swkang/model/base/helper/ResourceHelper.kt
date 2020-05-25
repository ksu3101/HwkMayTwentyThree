package com.swkang.model.base.helper

import androidx.annotation.StringRes


interface ResourceHelper {

    fun getString(@StringRes stringResId: Int): String

}