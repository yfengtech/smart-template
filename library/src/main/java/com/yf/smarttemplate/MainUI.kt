package com.yf.smarttemplate

import android.app.Activity
import android.content.Intent
import android.support.design.widget.NavigationView
import android.support.v4.app.Fragment
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import com.yf.smarttemplate.fragment.DocumentFragment
import com.yf.smarttemplate.sample.SlidingDrawer

/**
 * Created by yf.
 * @date 2019-06-02
 */
internal fun Activity.initToolbar(toolbar: Toolbar, title: String) {
    (this as? AppCompatActivity)?.apply {
        setSupportActionBar(toolbar)
        // 返回按钮点击事件
        toolbar.setNavigationOnClickListener {
            popFragment()
        }
        toolbar.title = title
    }
}

internal fun Activity.initNavigationView(slidingDrawer: SlidingDrawer, navigationView: NavigationView) {
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