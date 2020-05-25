package com.swkang.model.domain.common

import androidx.annotation.StringRes
import com.swkang.model.base.redux.State

sealed class MessageState : State

object HandledMessageState: MessageState()

data class ShowingGeneralToastState(
    @StringRes val messageResId: Int
): MessageState()

data class ShowingErrorToastState(
    @StringRes val errorMessageResId: Int,
    val errorMessageStr: String? = null
): MessageState()
