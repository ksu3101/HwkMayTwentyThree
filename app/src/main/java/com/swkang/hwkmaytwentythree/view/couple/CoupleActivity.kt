package com.swkang.hwkmaytwentythree.view.couple

import android.os.Bundle
import android.view.MenuItem
import com.swkang.hwkmaytwentythree.R
import com.swkang.hwkmaytwentythree.base.BaseActivity
import com.swkang.hwkmaytwentythree.base.exts.replaceFragment
import com.swkang.model.domain.hwk.RetrieveFileFiveHundredThousandLinesAction
import com.swkang.model.domain.hwk.RetrieveFileOneHundredLinesAction
import com.swkang.model.domain.hwk.RetrieveFileTenThousandLinesAction

class CoupleActivity : BaseActivity() {

    override fun getLayoutResId(): Int = R.layout.hwk_activity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportActionBar(findViewById(R.id.hwk_activity_toolbar))

        replaceFragment(
            replaceFragment = CoupleListFragment.newInstance(),
            containerResId = R.id.hwk_activity_container
        )
    }

    private enum class HomeworkFile {
        L100, L10000, L500000;

        override fun toString(): String {
            return this.name
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            R.id.hwk_menu_selectfiles -> {
                addDisposer(
                    messageHelper.createSingleChoiceDialog(
                        R.string.menu_selectfiles,
                        HomeworkFile.values().toList()
                    ).subscribe { selectedFile ->
                        appStore.dispatch(
                            when (selectedFile) {
                                HomeworkFile.L100 -> RetrieveFileOneHundredLinesAction
                                HomeworkFile.L10000 -> RetrieveFileTenThousandLinesAction
                                HomeworkFile.L500000 -> RetrieveFileFiveHundredThousandLinesAction
                            }
                        )
                    }
                )
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

}