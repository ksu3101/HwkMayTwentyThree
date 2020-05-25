package com.swkang.hwkmaytwentythree.base.helper

import android.content.Context
import androidx.annotation.RawRes
import com.swkang.common.exts.isNumber
import com.swkang.hwkmaytwentythree.R
import com.swkang.model.base.helper.LocalFileLoadHelper
import io.reactivex.Single

class LocalFileLoadHelperImpl(
    private val context: Context
) : LocalFileLoadHelper {

    override fun loadHomeworkFileOneHundredLine(): Single<List<List<String>>> {
        return Single.just(loadHomeworkTextFile(R.raw.txtf_onehl))
    }

    override fun loadHomeworkFileTenThousandLine(): Single<List<List<String>>> {
        return Single.just(loadHomeworkTextFile(R.raw.txtf_tentl))
    }

    override fun loadHomeworkFileFiveHundredThousandLine(): Single<List<List<String>>> {
        return Single.just(loadHomeworkTextFile(R.raw.txtf_fivehuntl))
    }

    private fun loadHomeworkTextFile(@RawRes targetId: Int): List<List<String>> {
        val result = mutableListOf<List<String>>()
        val resources = context.resources
        val inputStream = resources.openRawResource(targetId)
        val reader = inputStream.bufferedReader()
        reader.forEachLine {
            if (it.isNumber()) return@forEachLine
            result.add(it.split(' '))
        }
        reader.close()
        return result
    }

}