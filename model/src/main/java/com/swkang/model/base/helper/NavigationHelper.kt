package com.swkang.model.base.helper

import com.swkang.model.base.redux.State


interface NavigationHelper<S: State> {

    fun handle(state: S)

}