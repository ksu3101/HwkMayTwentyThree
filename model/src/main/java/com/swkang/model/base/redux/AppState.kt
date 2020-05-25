package com.swkang.model.base.redux

import android.util.Log
import com.swkang.common.exts.getSuperClassNames
import com.swkang.common.LOG_TAG

class AppState(
    val states: Map<String, State>
) : State {

    inline fun <reified S : State> getCurrentState(): S? {
        for (state in states.values) {
            if (state is S) return state
        }
        return null
    }

    inline fun <reified S : State> getStateBy(key: String): S? {
        val currentState = states.get(key) ?: return null
        return currentState as S
    }

    fun printStateLogs() {
        Log.d(LOG_TAG, "AppState (\n")
        for (state in states) {
            Log.d(LOG_TAG, String.format("\t[%-24s]\t%s \n", state.getSuperClassNames(), state))
        }
        Log.d(LOG_TAG, ")")
    }
}