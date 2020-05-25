package com.swkang.model.base.helper

import io.reactivex.Single

interface LocalFileLoadHelper {

    fun loadHomeworkFileOneHundredLine(): Single<List<List<String>>>

    fun loadHomeworkFileTenThousandLine(): Single<List<List<String>>>

    fun loadHomeworkFileFiveHundredThousandLine(): Single<List<List<String>>>

}