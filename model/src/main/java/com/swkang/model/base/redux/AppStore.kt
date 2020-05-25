package com.swkang.model.base.redux

import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.subjects.BehaviorSubject
import org.koin.core.KoinComponent

class AppStore(
    initializeAppState: AppState,
    reducer: Reducer<AppState>
) : Store<AppState>, KoinComponent {
    private val stateEmitter: BehaviorSubject<AppState> = BehaviorSubject.create()
    private val middleWares: Array<Middleware<AppState>> = getKoin().get()
    private var appState: AppState = initializeAppState
    private var dispatcher: Dispatcher

    init {
        dispatcher = middleWares.foldRight({ dispatchedAction ->
            appState = reducer.reduce(appState, dispatchedAction)
            stateEmitter.onNext(appState)
        }) { middleWare, next ->
            middleWare.create(this, next)
        }
    }

    override fun dispatch(action: Action) {
        dispatcher(action)
    }

    override fun stateListener(): Observable<AppState> =
        stateEmitter.hide().observeOn(AndroidSchedulers.mainThread())

    override fun getState(): AppState = appState
}