package com.swkang.hwkmaytwentythree.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.swkang.model.base.BaseViewModel
import com.swkang.model.base.exts.canHandleStateType
import com.swkang.model.base.helper.NavigationHelper
import com.swkang.model.base.redux.AppStore
import com.swkang.model.base.redux.State
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

abstract class BaseFragment<S: State>: Fragment() {
    protected val appStore: AppStore by inject()
    protected val navigationHelper: NavigationHelper<S> by inject()
    protected val vm: BaseViewModel<S> by viewModel()
    private lateinit var binder: ViewDataBinding
    private val compositeDisposable = CompositeDisposable()

    @LayoutRes
    abstract fun getLayoutResId(): Int

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        compositeDisposable.clear()
        compositeDisposable.add(
            appStore.stateListener()
                .flatMap { Observable.fromIterable(it.states.values) }
                .distinctUntilChanged()
                .canHandleStateType()
                .subscribe {
                    if (it as? S == null) throw IllegalStateException("$it is not allowed state.")
                    if (!vm.render(it)) {
                        navigationHelper.handle(it)
                    }
                }
        )
        binder = DataBindingUtil.inflate(inflater, getLayoutResId(), container, false)
        binder.setVariable(BR.vm, vm)
        binder.lifecycleOwner = viewLifecycleOwner
        return binder.root
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.clear()
        vm.dispose()
    }

}
