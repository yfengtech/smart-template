package com.yf.smarttemplate

import android.app.Activity
import android.app.Application
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.view.ViewGroup

object SmartTemplate {

    internal val sampleList = mutableListOf<SampleItem>()

    @JvmStatic
    fun init(application: Application, closure: TemplateItem.() -> Unit) {
        application.registerActivityLifecycleCallbacks(lifecycle)
        TemplateItem().apply(closure)
        Log.d("SmartTemplate", sampleList.toString())
    }

    private fun getLaunchActivityName(application: Application): String? {
        // 获取app的启动intent
        val launchIntent: Intent? = application.packageManager.getLaunchIntentForPackage(application.packageName)
        return launchIntent?.component?.className
    }

    val lifecycle = object : Application.ActivityLifecycleCallbacks {
        override fun onActivityCreated(activity: Activity?, savedInstanceState: Bundle?) {
            activity?.let {
                // 如果当前activity是启动页
                if (it::class.java.name == getLaunchActivityName(it.application)) {
                    if (it is AppCompatActivity && savedInstanceState == null) {
                        it.supportFragmentManager.beginTransaction().add(android.R.id.content, MainFragment()).commit()
                    }
                }
            }
        }

        override fun onActivityStarted(activity: Activity?) {
            activity?.let {
                // 如果当前activity是启动页
                if (it::class.java.name == getLaunchActivityName(it.application)) {
                    val contentLayout = it.findViewById<ViewGroup>(android.R.id.content)
                    val sampleFragmentContainer = contentLayout.findViewById<View>(R.id.sampleFragmentContainer)
                    if (sampleFragmentContainer == null) {
                        contentLayout.removeAllViews()
                    } else {
                        // 移除除了sampleFragmentContainer的其他控件
                        (0 until contentLayout.childCount).map { index ->
                            contentLayout.getChildAt(index)
                        }.filter { view ->
                            view != sampleFragmentContainer
                        }.forEach { view ->
                            contentLayout.removeView(view)
                        }
                    }
                }
            }
        }

        override fun onActivityPaused(activity: Activity?) {}
        override fun onActivityResumed(activity: Activity?) {}
        override fun onActivityDestroyed(activity: Activity?) {}
        override fun onActivitySaveInstanceState(activity: Activity?, outState: Bundle?) {}
        override fun onActivityStopped(activity: Activity?) {}
    }
}

class TemplateItem {

    fun item(closure: SampleItem.() -> Unit) {
        SmartTemplate.sampleList.add(SampleItem().apply(closure))
    }
}

class SampleItem(var id: Int = 0, var title: String = "default title", var desc: String = "default desc") {
    override fun toString(): String {
        return "SampleItem(id=$id, title='$title', desc='$desc')"
    }
}