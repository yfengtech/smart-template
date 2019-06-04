package com.yf.smarttemplate

import android.app.Activity
import android.content.Intent
import android.support.design.widget.NavigationView
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.Gravity
import android.view.ViewManager
import com.yf.smarttemplate.fragment.DocumentFragment
import com.yf.smarttemplate.fragment.MainFragment
import com.yf.smarttemplate.sample.SampleContainer
import com.yf.smarttemplate.sample.SlidingDrawer
import org.jetbrains.anko.appcompat.v7.themedToolbar
import org.jetbrains.anko.custom.ankoView
import org.jetbrains.anko.frameLayout
import org.jetbrains.anko.matchParent
import org.jetbrains.anko.support.v4.drawerLayout
import org.jetbrains.anko.verticalLayout
import org.jetbrains.anko.wrapContent

/**
 * Created by yf.
 * @date 2019-06-02
 */
object MainUI {
    /**
     * app名称
     */
    internal var appTitle = "origin"

    /**
     * 原始模板入口
     */
    internal lateinit var originTemplateContainer: SampleContainer
    /**
     * 侧滑抽屉容器
     */
    internal var slidingDrawer: SlidingDrawer? = null
}

/**
 * 替换activity内的布局，
 */
internal fun AppCompatActivity.replaceContentView() {
    drawerLayout {
        id = R.id.drawer
        verticalLayout {
            themedToolbar(R.style.SmartToolBar) {
            }.lparams(matchParent, wrapContent).apply {
                initToolbar(this, MainUI.appTitle)
            }
            // fragment 主内容
            frameLayout {
                id = R.id.fragmentContainer
            }.lparams(matchParent, matchParent)
        }
        if (MainUI.slidingDrawer != null) {
            // 初始化可拉出的抽屉控件
            frameLayout {
                navigationView {
                    initNavigationView(MainUI.slidingDrawer!!, this)
                }.lparams(wrapContent, matchParent)
            }.lparams(width = wrapContent, height = matchParent, gravity = Gravity.START)
        }
    }.apply {
        fitsSystemWindows = true
    }
}

internal fun AppCompatActivity.initToolbar(toolbar: Toolbar, title: String) {
    setSupportActionBar(toolbar)
    // 返回按钮点击事件
    toolbar.setNavigationOnClickListener {
        popFragment()
    }
    toolbar.title = title
}

/**
 * 初始化左边抽屉控件的内容
 */
internal fun AppCompatActivity.initNavigationView(slidingDrawer: SlidingDrawer, navigationView: NavigationView) {
    // 设置header
    navigationView.inflateHeaderView(R.layout.nav_header)
    // 设置显示的列表内容
    slidingDrawer.drawerList.forEach {
        navigationView.menu.add(it.groupId, it.itemId, 0, it.title)
        // 设置图标
        navigationView.menu.findItem(it.itemId).setIcon(it.iconRes ?: R.drawable.jetpack_logo_small)
    }
    // 设置点击事件
    navigationView.setNavigationItemSelectedListener { menuItem ->
        slidingDrawer.drawerList.first {
            // 找到点击的menu
            it.itemId == menuItem.itemId
        }.let { item ->
            findViewById<DrawerLayout>(R.id.drawer)?.closeDrawers()
            when {
                item.execution != null -> {
                    // 直接运行代码块
                    item.execution!!.invoke(this)
                }
                item.openClazz != null -> {
                    // 开启activity或fragment
                    when {
                        Activity::class.java.isAssignableFrom(item.openClazz!!) -> {
                            startActivity(
                                Intent(this, item.openClazz)
                            )
                        }
                        Fragment::class.java.isAssignableFrom(item.openClazz!!) -> {
                            val fragment = item.openClazz!!.newInstance() as Fragment
                            replaceFragmentAndTitle(fragment, item.title)
                        }
                    }
                }
                item.markdownAssetFileName != null -> {
                    replaceFragmentAndTitle(
                        DocumentFragment.newInstance(item.markdownAssetFileName), item.title
                    )
                }
            }
        }
        false
    }
}

/**
 * 设置toolbar中返回键的显示和隐藏
 * fragment栈中count大于0时才显示，否则认为是在首页
 */
internal fun AppCompatActivity.setToolbarBackListener() {
    supportFragmentManager.addOnBackStackChangedListener {
        val stackCount = supportFragmentManager.backStackEntryCount
        setActionBarBackShow(stackCount > 0)
        if (stackCount == 0) {
            // 当前在home fragment
            supportActionBar?.title = MainUI.appTitle
        }
    }
}

/**
 * 设置ActionBar的返回按钮是否显示
 */
internal fun AppCompatActivity.setActionBarBackShow(isShow: Boolean) {
    supportActionBar?.setDisplayHomeAsUpEnabled(isShow)
    supportActionBar?.setHomeButtonEnabled(isShow)
}

/**
 * 添加home fragment
 */
internal fun AppCompatActivity.addHomeFragment() {
    supportFragmentManager.beginTransaction()
        .add(
            R.id.fragmentContainer,
            MainFragment.newInstance(MainUI.originTemplateContainer)
        )
        .commit()
}


/**
 * 替换全局内容fragment，并且加入后退栈；修改ActionBar的title
 */
internal fun AppCompatActivity.replaceFragmentAndTitle(fragment: Fragment, title: String? = null) {
    supportFragmentManager.beginTransaction().apply {
        setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
        addToBackStack(null)
        replace(R.id.fragmentContainer, fragment)
        commit()
    }
    supportActionBar?.title = title ?: MainUI.appTitle
}

/**
 * fragment后退
 */
internal fun AppCompatActivity.popFragment() {
    if (supportFragmentManager.backStackEntryCount > 0) {
        supportFragmentManager.popBackStack()
    }
}

/**
 * 对NavigationView增加anko支持
 */
internal inline fun ViewManager.navigationView(theme: Int = 0, init: NavigationView.() -> Unit) =
    ankoView({ NavigationView(it) }, theme, init)