package com.swkang.model.domain.common

import androidx.annotation.StringRes
import com.swkang.model.base.redux.Action

sealed class MessageAction : Action

object HandledMessageAction : MessageAction()

data class ShowingGeneralToast(
    @StringRes val messageResId: Int
) : MessageAction()

data class ShowingErrorToast(
    @StringRes val errorMessageResId: Int = 0,
    val errorMessageStr: String? = null
) : MessageAction()
