package com.swkang.model.domain.common

import com.swkang.model.base.redux.Action
import com.swkang.model.base.redux.Reducer

class MessageReducer(
    override val initializeState: MessageState = HandledMessageState
): Reducer<MessageState> {

    override fun reduce(oldState: MessageState, action: Action): MessageState {
        return when(action) {
            is HandledMessageAction -> HandledMessageState
            is ShowingGeneralToast -> ShowingGeneralToastState(action.messageResId)
            is ShowingErrorToast -> ShowingErrorToastState(
                action.errorMessageResId,
                action.errorMessageStr
            )
            else -> oldState
        }
    }

}