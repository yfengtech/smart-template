package com.yf.smarttemplate.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.MenuItem
import android.view.View
import com.yf.smarttemplate.popFragment
import com.yf.smarttemplate.setActionBarTitle

abstract class SmartFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 开启菜单功能
        setHasOptionsMenu(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // 设置菜单显示情况
        setMenuVisibility(true)
        activity?.setActionBarTitle(tag)
    }

    /**
     * 菜单点击回调
     */
    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            // 监听到首页的菜单返回键
            android.R.id.home -> {
                activity?.popFragment()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}