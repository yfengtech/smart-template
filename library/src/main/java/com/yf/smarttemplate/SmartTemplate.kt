package com.yf.smarttemplate

import android.app.Activity
import android.app.Application
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.ViewGroup
import com.yf.smarttemplate.doc.Document
import com.yf.smarttemplate.sample.MainFragment
import com.yf.smarttemplate.sample.SampleContainer

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

    /**
     * 第一次启动app，移除首页内控件
     */
    private var isFirstStart = true

    @JvmStatic
    fun init(application: Application, closure: SampleContainer.() -> Unit) {
        appTitle = application.getAppName()
        // 用于拦截第一个activity，替换成模板样式
        application.registerActivityLifecycleCallbacks(lifecycle)
        // 跟节点 sample列表
        originTemplateContainer = SampleContainer().apply(closure)
        //配置文档信息
        documentPath = application::class.java.getAnnotation(Document::class.java)?.value
    }

    /**
     * 监听activity生命周期，拦截launchActivity并替换
     */
    private val lifecycle = object : Application.ActivityLifecycleCallbacks {
        override fun onActivityCreated(act: Activity?, savedInstanceState: Bundle?) {
            act?.let { activity ->
                // 必须是AppCompatActivity才能往下进行
                if (activity !is AppCompatActivity) return

                // 如果当前activity是启动页
                if (activity::class.java.name == activity.application.getLaunchActivityName()) {

                    // 控制ActionBar左边`返回按钮`的显示和隐藏
                    activity.supportFragmentManager.addOnBackStackChangedListener {
                        val stackCount = activity.supportFragmentManager.backStackEntryCount
                        activity.setActionBarBackShow(stackCount > 0)
                    }

                    // 启动home fragment
                    if (savedInstanceState == null) {
                        activity.supportFragmentManager.beginTransaction()
                            .add(
                                android.R.id.content,
                                MainFragment.newInstance(originTemplateContainer, true)
                            )
                            .commit()
                        activity.setActionBarTitle(appTitle)
                    }
                }
            }
        }

        override fun onActivityStarted(activity: Activity?) {
            activity?.let {
                // 必须是AppCompatActivity才能往下进行
                if (it !is AppCompatActivity) return

                // 如果当前activity是启动页,只有第一次启动时执行
                if (it::class.java.name == it.application.getLaunchActivityName() && isFirstStart) {
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
                    isFirstStart = false
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

