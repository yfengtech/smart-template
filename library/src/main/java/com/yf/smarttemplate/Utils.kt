package com.yf.smarttemplate

import android.app.Activity
import android.app.Application
import android.content.Intent
import android.support.design.widget.NavigationView
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.ViewManager
import org.jetbrains.anko.custom.ankoView

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

val DEBUG = true
internal inline fun <reified T : Any> T.debugLog(value: String) {
    if (DEBUG) {
        Log.d("SmartTemplate", this::class.java.simpleName + value)
    }
}
