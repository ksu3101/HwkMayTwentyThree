package com.swkang.hwkmaytwentythree.base.helper

import android.app.AlertDialog
import android.content.Context
import android.widget.Toast
import com.swkang.common.exts.isNotNullOrEmpty
import com.swkang.model.base.helper.MessageHelper
import io.reactivex.Maybe
import io.reactivex.disposables.Disposables

class MessageHelperImpl(
    private val context: Context
) : MessageHelper {

    override fun showingGeneralToast(messageResId: Int) {
        Toast.makeText(context, messageResId, Toast.LENGTH_SHORT).show()
    }

    override fun showingErrorToast(errorMessageResId: Int, errorMessageStr: String?) {
        val message = if (errorMessageResId == 0 && errorMessageStr.isNullOrEmpty()) {
            throw IllegalArgumentException("Message parameters has not avaialble.")
        } else {
            if (errorMessageStr.isNotNullOrEmpty()) errorMessageStr
            else context.getString(errorMessageResId)
        }
        // todo : 에러 토스트의 경우 배경 컬러 변경?
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    override fun <T> createSingleChoiceDialog(title: Int, selectItems: List<T>): Maybe<T> {
        return Maybe.create { emitter ->
            val builder = AlertDialog.Builder(context)
            val dialog = builder.setTitle(title)
                .setItems(
                    selectItems.map { it.toString() }.toTypedArray()
                ) { _, which ->
                    emitter.onSuccess(selectItems[which])
                }
                .setOnCancelListener { emitter.onComplete() }
                .create()
            emitter.setDisposable(
                Disposables.fromRunnable {
                    dialog.dismiss()
                }
            )
            dialog.setCancelable(true)
            dialog.show()
        }
    }
}