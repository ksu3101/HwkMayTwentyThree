package com.swkang.model.base.redux

import io.reactivex.Observable


interface ActionProcessor<S : State> {
    fun run(action: Observable<Action>, store: Store<S>): Observable<out Action>
}

/**
 * wrapper class of multiple action processors.
 */
class CombinedActionProcessors<S: State>(
    private val actionProcessrs: Iterable<ActionProcessor<S>>
): ActionProcessor<S> {
    override fun run(action: Observable<Action>, store: Store<S>): Observable<out Action> {
        return Observable.fromIterable(actionProcessrs)
            .flatMap { it.run(action, store) }
    }
}