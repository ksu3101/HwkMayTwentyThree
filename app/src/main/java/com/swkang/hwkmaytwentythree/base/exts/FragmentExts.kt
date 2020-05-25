package com.swkang.hwkmaytwentythree.base.exts

import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity

fun FragmentActivity?.addFragment(
    addFragment: Fragment,
    @IdRes containerResId: Int,
    tag: String? = null,
    isAddToBackStack: Boolean = false) {
    this?.let {
        if (it.isFinishing) {
            return@let
        }
        val transaction = it.supportFragmentManager.beginTransaction()
        transaction.add(containerResId, addFragment, tag)
        if (isAddToBackStack) {
            transaction.addToBackStack(addFragment.javaClass.simpleName)
        }
        transaction.commitAllowingStateLoss()
    }
}

fun FragmentActivity?.replaceFragment(
    replaceFragment: Fragment,
    @IdRes containerResId: Int,
    tagOfFragment: String? = null,
    isAddToBackStack: Boolean = false
) {
    this?.let {
        if (it.isFinishing) {
            return@let
        }
        val transaction = it.supportFragmentManager.beginTransaction()
        transaction.replace(containerResId, replaceFragment)
        if (isAddToBackStack) {
            transaction.addToBackStack(tagOfFragment)
        }
        transaction.commitAllowingStateLoss()
    }
}
