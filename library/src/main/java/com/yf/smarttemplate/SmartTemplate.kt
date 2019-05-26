package com.yf.smarttemplate

import android.app.Activity
import android.app.Application
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.ViewGroup

object SmartTemplate {

    /**
     * 原始模板入口
     */
    private lateinit var originTemplateContainer: SampleContainer

    /**
     * app名称
     */
    internal var appTitle = "origin"
    /**
     * 文档路径
     */
    internal var documentPath: String? = null

    @JvmStatic
    fun init(application: Application, closure: SampleContainer.() -> Unit) {
        appTitle = getAppName(application)

        application.registerActivityLifecycleCallbacks(lifecycle)

        // 跟节点 sample列表
        originTemplateContainer = SampleContainer().apply(closure)
        //配置文档信息
        documentPath = application::class.java.getAnnotation(Document::class.java)?.value
    }

    private fun getAppName(application: Application): String {
        val packageManager = application.packageManager
        return packageManager.getPackageInfo(
            application.packageName,
            0
        ).applicationInfo.loadLabel(packageManager) as String
    }

    private fun getLaunchActivityName(application: Application): String? {
        // 获取app的启动intent
        val launchIntent: Intent? = application.packageManager.getLaunchIntentForPackage(application.packageName)
        return launchIntent?.component?.className
    }

    /**
     * 监听activity生命周期，拦截launchActivity并替换
     */
    private val lifecycle = object : Application.ActivityLifecycleCallbacks {
        override fun onActivityCreated(activity: Activity?, savedInstanceState: Bundle?) {
            activity?.let {
                // 如果当前activity是启动页
                if (it::class.java.name == getLaunchActivityName(it.application)) {
                    if (it is AppCompatActivity && savedInstanceState == null) {
                        it.supportFragmentManager.beginTransaction()
                            .add(
                                android.R.id.content,
                                MainFragment.newInstance(appTitle, originTemplateContainer, true)
                            )
                            .commit()
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