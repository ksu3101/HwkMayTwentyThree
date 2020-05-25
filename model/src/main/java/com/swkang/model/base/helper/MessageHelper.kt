package com.swkang.model.base.helper

import androidx.annotation.StringRes
import io.reactivex.Maybe


interface MessageHelper {

    fun showingGeneralToast(
        @StringRes messageResId: Int
    )

    fun showingErrorToast(
        @StringRes errorMessageResId: Int,
        errorMessageStr: String? = null
    )

    /**
     * 목록에서 하나의 아이템선택이 가능한 팝업을 보여준다.
     *
     * @param title 팝업의 타이틀 String resource id.
     * @param selectItems 보여줄 아이템 목록. 해당 항목의 `toString()` 을 이용 하여 항목을 출력 한다.
     * @return `onSuccess` 의 경우 선택한 아이템 인스턴스. `onComplete` 의 경우 다이얼로그 캔슬.
     */
    fun <T> createSingleChoiceDialog(
        @StringRes title: Int,
        selectItems: List<T>
    ): Maybe<T>

}