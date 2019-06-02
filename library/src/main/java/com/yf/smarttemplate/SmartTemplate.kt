package com.yf.smarttemplate

import android.app.Activity
import android.app.Application
import android.graphics.Color
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v7.app.AppCompatActivity
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import com.yf.smarttemplate.doc.Document
import com.yf.smarttemplate.doc.DocumentFragment
import com.yf.smarttemplate.sample.MainFragment
import com.yf.smarttemplate.sample.SampleContainer
import org.jetbrains.anko.*
import org.jetbrains.anko.support.v4.drawerLayout

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
        override fun onActivityStarted(activity: Activity?) {
            activity?.let {
                // 必须是AppCompatActivity才能往下进行
                if (it !is AppCompatActivity) return

                // 如果当前activity是启动页,只有第一次启动时执行
                if (it::class.java.name == it.application.getLaunchActivityName() && isFirstStart) {

                    // TODO 可以增加toolbar

                    // 替换原布局，重新setContentView
                    it.drawerLayout {
                        // fragment 主内容
                        frameLayout {
                            id = android.R.id.custom
                        }
                        frameLayout {
                            navigationView {
                                inflateMenu(R.menu.main_drawer_menu)
                                setNavigationItemSelectedListener { menuItem ->
                                    when (menuItem.itemId) {
                                        R.id.drawer_document -> {
                                            // TODO 点击文档，禁止重复点
                                            it.replaceFragmentAndTitle(
                                                DocumentFragment.newInstance(SmartTemplate.documentPath),
                                                menuItem.title.toString()
                                            )
                                            true
                                        }
                                    }
                                    false
                                }
                            }.lparams(width = wrapContent, height = matchParent)
                        }.lparams(width = wrapContent, height = matchParent, gravity = Gravity.START)
                            .fitsSystemWindows = true
                    }.fitsSystemWindows = true


                    // 控制ActionBar左边`返回按钮`的显示和隐藏
                    it.supportFragmentManager.addOnBackStackChangedListener {
                        val stackCount = it.supportFragmentManager.backStackEntryCount
                        it.setActionBarBackShow(stackCount > 0)
                    }

                    // 启动home fragment
                    it.supportFragmentManager.beginTransaction()
                        .add(
                            android.R.id.custom,
                            MainFragment.newInstance(originTemplateContainer, true),
                            appTitle
                        )
                        .commit()
                    isFirstStart = false
                }
            }
        }

        override fun onActivityCreated(act: Activity?, savedInstanceState: Bundle?) {}
        override fun onActivityPaused(activity: Activity?) {}
        override fun onActivityResumed(activity: Activity?) {}
        override fun onActivityDestroyed(activity: Activity?) {}
        override fun onActivitySaveInstanceState(activity: Activity?, outState: Bundle?) {}
        override fun onActivityStopped(activity: Activity?) {}
    }
}
