package com.yf.smarttemplate

import android.app.Activity
import android.app.Application
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.yf.smarttemplate.sample.SampleContainer
import com.yf.smarttemplate.sample.SlidingDrawer

object SmartTemplate {

    /**
     * 第一次启动app，移除首页内控件
     */
    private var isFirstLaunch = true

    @JvmStatic
    fun init(application: Application, slidingDrawer: SlidingDrawer? = null, closure: SampleContainer.() -> Unit) {
        // 获取应用的名称
        MainUI.appTitle = application.getAppName()
        // 用于拦截第一个activity，替换成模板样式
        application.registerActivityLifecycleCallbacks(lifecycle)
        // 跟节点 sample列表
        MainUI.originTemplateContainer = SampleContainer().apply(closure)
        // 用来填充抽屉控件
        MainUI.slidingDrawer = slidingDrawer
    }

    /**
     * 监听activity生命周期，拦截launchActivity并替换
     */
    private val lifecycle = object : Application.ActivityLifecycleCallbacks {
        override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
            if (activity::class.java.name == activity.application.getLaunchActivityName()) {
                isFirstLaunch = true
            }
        }

        override fun onActivityStarted(activity: Activity) {
            // 必须是AppCompatActivity才能往下进行
            if (activity !is AppCompatActivity) return

            // 如果当前activity是启动页,只有第一次启动时执行
            if (activity::class.java.name == activity.application.getLaunchActivityName() && isFirstLaunch) {
                // 替换原布局，重新setContentView
                activity.replaceContentView()
                // 控制ActionBar左边`返回按钮`的显示和隐藏
                activity.setToolbarBackListener()
                // 启动home fragment
                activity.addHomeFragment()
                isFirstLaunch = false
            }
        }

        override fun onActivityPaused(activity: Activity?) {}
        override fun onActivityResumed(activity: Activity?) {}
        override fun onActivityDestroyed(activity: Activity?) {}
        override fun onActivitySaveInstanceState(activity: Activity?, outState: Bundle?) {}
        override fun onActivityStopped(activity: Activity?) {}
    }
}
