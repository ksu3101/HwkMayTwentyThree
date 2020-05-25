package com.swkang.hwkmaytwentythree.base

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import com.swkang.model.base.RxDisposer
import com.swkang.model.base.helper.MessageHelper
import com.swkang.model.base.redux.AppStore
import com.swkang.model.domain.common.HandledMessageAction
import com.swkang.model.domain.common.MessageState
import com.swkang.model.domain.common.ShowingErrorToastState
import com.swkang.model.domain.common.ShowingGeneralToastState
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import org.koin.android.ext.android.inject

abstract class BaseActivity : AppCompatActivity(), RxDisposer {
    protected val messageHelper: MessageHelper by inject()
    protected val appStore: AppStore by inject()
    private lateinit var compositeDisposable: CompositeDisposable

    @LayoutRes
    abstract fun getLayoutResId(): Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutResId())
    }

    override fun onResume() {
        super.onResume()
        subscribeMessageState()
    }

    override fun onDestroy() {
        super.onDestroy()
        dispose()
    }

    override fun addDisposer(disposable: Disposable) {
        if (!::compositeDisposable.isInitialized) {
            compositeDisposable = CompositeDisposable()
        }
        compositeDisposable.add(disposable)
    }

    override fun dispose() {
        if (::compositeDisposable.isInitialized) {
            compositeDisposable.dispose()
        }
    }

    private fun subscribeMessageState() {
        addDisposer(
            appStore.stateListener()
                .flatMap { Observable.fromArray(it.states) }
                .ofType(MessageState::class.java)
                .doOnNext{ appStore.dispatch(HandledMessageAction) }
                .subscribe { handleMessageState(it) }
        )
    }

    private fun handleMessageState(messageState: MessageState) {
        when(messageState) {
            is ShowingGeneralToastState -> {
                messageHelper.showingGeneralToast(messageState.messageResId)
            }
            is ShowingErrorToastState -> {
                messageHelper.showingErrorToast(
                    messageState.errorMessageResId,
                    messageState.errorMessageStr
                )
            }
            // 추가 공통 메시지 핸들링은 여기에 추가 한다.
        }
    }

}