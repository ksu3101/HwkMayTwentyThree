package com.swkang.model.base.exts

import com.swkang.model.base.redux.State
import io.reactivex.Observable

/**
 * @author kangsungwoo
 * @since 2020-03-02
 */

inline fun <reified S: State> Observable<S>.canHandleStateType(): Observable<S> =
    ofType<S>(S::class.java)