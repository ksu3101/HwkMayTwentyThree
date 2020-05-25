package com.swkang.model.domain.hwk

import android.util.Log
import com.swkang.common.LOG_TAG
import com.swkang.common.exts.add
import com.swkang.model.base.exts.actionTransformer
import com.swkang.model.base.exts.createActionProcessor
import com.swkang.model.base.helper.LocalFileLoadHelper
import com.swkang.model.base.redux.Action
import com.swkang.model.base.redux.ActionProcessor
import com.swkang.model.base.redux.AppState
import com.swkang.model.base.redux.Store
import com.swkang.model.domain.common.MessageAction
import com.swkang.model.domain.common.ShowingErrorToast
import com.swkang.model.domain.hwk.dto.Couple
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class HwkCoupleActionProcessor(
    private val localFileLoader: LocalFileLoadHelper
) : ActionProcessor<AppState> {

    override fun run(
        action: Observable<Action>,
        store: Store<AppState>
    ): Observable<out Action> {
        return action.compose(actionProcessor)
    }

    private val actionProcessor = createActionProcessor { shared ->
        arrayOf(
            shared.ofType(RetrieveFileOneHundredLinesAction::class.java).compose(loadFileL100),
            shared.ofType(RetrieveFileTenThousandLinesAction::class.java).compose(loadFileL10000),
            shared.ofType(RetrieveFileFiveHundredThousandLinesAction::class.java)
                .compose(loadFileL500000)
        )
    }

    private val loadFileL100 = actionTransformer<RetrieveFileOneHundredLinesAction> { action ->
        localFileLoader.loadHomeworkFileOneHundredLine()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .map<Action> {
                ReceivedCoupleLinesResultAction(handleReceivedItems(it))
            }
            .onErrorReturn { commonHandleError(it) }
            .toObservable()
    }

    private val loadFileL10000 = actionTransformer<RetrieveFileTenThousandLinesAction> { action ->
        localFileLoader.loadHomeworkFileTenThousandLine()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .map<Action> {
                ReceivedCoupleLinesResultAction(handleReceivedItems(it))
            }
            .onErrorReturn { commonHandleError(it) }
            .toObservable()
    }

    private val loadFileL500000 =
        actionTransformer<RetrieveFileFiveHundredThousandLinesAction> { action ->
            localFileLoader.loadHomeworkFileFiveHundredThousandLine()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map<Action> {
                    ReceivedCoupleLinesResultAction(handleReceivedItems(it))
                }.onErrorReturn { commonHandleError(it) }
                .toObservable()
        }

    private fun handleReceivedItems(items: List<List<String>>): Map<String, List<Couple>> {
        val result = mutableMapOf<String, List<Couple>>()
        for (i in items.indices) {
            for (j in i + 1 until items.size) {
                val intersectItems = items[i].intersect(items[j]).toList()

                // 최소 중복원소 5개 이상
                if (intersectItems.size < 5) continue

                val intersectItemStr = intersectItems.toString()
                result.putToList(
                    intersectItemStr,
                    Couple(intersectItemStr, i, j, items[i], items[j])
                )
            }
        }
        return result.toSortedMap()
    }

    private fun <K, V, L : List<V>> MutableMap<K, L>.putToList(key: K, item: V) {
        val container = get(key)
        put(key,
            if (container != null) {
                container.add(item)
            } else {
                listOf(item)
            } as L
        )
    }

    private fun commonHandleError(throwable: Throwable): MessageAction {
        return ShowingErrorToast(errorMessageStr = throwable.message)
    }

}
