package com.swkang.model.base.exts

import com.swkang.model.base.redux.State
import io.reactivex.Observable

inline fun <reified S: State> Observable<S>.canHandleStateType(): Observable<S> {
    return ofType(S::class.java)
}
