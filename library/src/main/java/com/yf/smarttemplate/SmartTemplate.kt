package com.yf.smarttemplate

import android.app.Activity
import android.app.Application
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Gravity
import com.yf.smarttemplate.fragment.MainFragment
import com.yf.smarttemplate.sample.SampleContainer
import com.yf.smarttemplate.sample.SlidingDrawer
import org.jetbrains.anko.appcompat.v7.themedToolbar
import org.jetbrains.anko.frameLayout
import org.jetbrains.anko.matchParent
import org.jetbrains.anko.support.v4.drawerLayout
import org.jetbrains.anko.verticalLayout
import org.jetbrains.anko.wrapContent

object SmartTemplate {

    /**
     * 原始模板入口
     */
    private lateinit var originTemplateContainer: SampleContainer
    /**
     * 侧滑抽屉容器
     */
    private var mSlidingDrawer: SlidingDrawer? = null

    /**
     * app名称
     */
    private var appTitle = "origin"

    /**
     * 第一次启动app，移除首页内控件
     */
    private var isFirstLaunch = true

    @JvmStatic
    fun init(application: Application, slidingDrawer: SlidingDrawer? = null, closure: SampleContainer.() -> Unit) {
        appTitle = application.getAppName()
        // 用于拦截第一个activity，替换成模板样式
        application.registerActivityLifecycleCallbacks(lifecycle)
        // 跟节点 sample列表
        originTemplateContainer = SampleContainer().apply(closure)
        // 用来填充抽屉控件
        mSlidingDrawer = slidingDrawer
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
                activity.drawerLayout {
                    id = R.id.drawer
                    verticalLayout {
                        themedToolbar(R.style.SmartToolBar) {
                        }.lparams(matchParent, wrapContent).apply {
                            activity.initToolbar(this,appTitle)

                        }
                        // fragment 主内容
                        frameLayout {
                            id = android.R.id.custom
                        }.lparams(matchParent, matchParent)
                    }
                    if (mSlidingDrawer != null) {
                        // 初始化可拉出的抽屉控件
                        frameLayout {
                            navigationView {
                                activity.initNavigationView(mSlidingDrawer!!, this)
                            }.lparams(width = wrapContent, height = matchParent)
                        }.lparams(width = wrapContent, height = matchParent, gravity = Gravity.START)
                    }
                }.apply {
                    fitsSystemWindows = true
                }


                // 控制ActionBar左边`返回按钮`的显示和隐藏
                activity.supportFragmentManager.addOnBackStackChangedListener {
                    val stackCount = activity.supportFragmentManager.backStackEntryCount
                    activity.setActionBarBackShow(stackCount > 0)
                    if (stackCount == 0) {
                        // 当前在home fragment
                        activity.supportActionBar?.title = appTitle
                    }
                }

                // 启动home fragment
                activity.supportFragmentManager.beginTransaction()
                    .add(
                        android.R.id.custom,
                        MainFragment.newInstance(originTemplateContainer)
                    )
                    .commit()
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
