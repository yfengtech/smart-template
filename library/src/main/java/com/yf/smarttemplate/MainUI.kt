package com.yf.smarttemplate

import android.app.Activity
import android.support.design.widget.NavigationView
import android.support.v4.widget.DrawerLayout
import com.yf.smarttemplate.fragment.DocumentFragment

/**
 * Created by yf.
 * @date 2019-06-02
 */
internal fun Activity.initNavigationView(view: NavigationView) {
    view.apply {
        inflateHeaderView(R.layout.nav_header)
        inflateMenu(R.menu.main_drawer_menu)
        setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.drawer_document -> {
                    // TODO 点击文档，禁止重复点
                    replaceFragmentAndTitle(
                        DocumentFragment.newInstance(SmartTemplate.documentPath),
                        menuItem.title.toString()
                    )
                    //
                    this@initNavigationView.findViewById<DrawerLayout>(R.id.drawer).closeDrawers()
                    true
                }
            }
            false
        }
    }
}