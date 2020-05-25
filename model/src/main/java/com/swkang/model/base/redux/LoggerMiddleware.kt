package com.swkang.model.base.redux

import android.util.Log
import androidx.databinding.library.BuildConfig
import com.swkang.common.exts.getSuperClassNames
import com.swkang.common.LOG_TAG

class LoggerMiddleware<S : State> : Middleware<S> {

    override fun create(store: Store<S>, next: Dispatcher): Dispatcher {
        return { action: Action ->
            if (BuildConfig.DEBUG) {
                Log.d(LOG_TAG, "action dispatch : [${action.getSuperClassNames()}] $action")
            }
            val prevState = store.getState()
            next(action)

            if (BuildConfig.DEBUG) {
                val currentState = store.getState()
                if (prevState != currentState) {
                    (currentState as AppState).printStateLogs()
                }
            }
        }
    }

}