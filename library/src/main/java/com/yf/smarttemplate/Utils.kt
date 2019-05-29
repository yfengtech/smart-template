package com.yf.smarttemplate

import android.app.Activity
import android.app.Application
import android.content.Intent
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction
import android.support.v7.app.AppCompatActivity

/**
 * 获取app的名称，即为AndroidManifest中application中的label
 */
internal fun Application.getAppName(): String {
    val packageManager = packageManager
    return packageManager.getPackageInfo(packageName, 0)
        .applicationInfo.loadLabel(packageManager) as String
}

/**
 * 获取应用启动时，第一个启动的activity的名称
 */
internal fun Application.getLaunchActivityName(): String? {
    // 获取app的启动intent
    val launchIntent: Intent? = packageManager.getLaunchIntentForPackage(packageName)
    return launchIntent?.component?.className
}

/**
 * 替换全局内容fragment，并且加入后退栈；修改ActionBar的title
 */
internal fun Activity.replaceFragmentAndTitle(fragment: Fragment, titleTag: String? = null) {

    if (this !is AppCompatActivity) throw IllegalArgumentException("Activity must be AppCompatActivity")

    supportFragmentManager.beginTransaction().apply {
        setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
        addToBackStack(null)
        replace(android.R.id.content, fragment, titleTag)
        commit()
    }
}

internal fun Activity.setActionBarTitle(title: String?) {
    if (!title.isNullOrBlank() && this is AppCompatActivity) {
        supportActionBar?.title = title
    }
}

internal fun Activity.setActionBarBackShow(isShow: Boolean) {
    if (this is AppCompatActivity) {
        supportActionBar?.setDisplayHomeAsUpEnabled(isShow)
        supportActionBar?.setHomeButtonEnabled(isShow)
    }
}