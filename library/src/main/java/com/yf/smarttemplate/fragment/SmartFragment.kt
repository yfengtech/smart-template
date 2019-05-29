package com.yf.smarttemplate.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import com.yf.smarttemplate.R
import com.yf.smarttemplate.SmartTemplate
import com.yf.smarttemplate.doc.DocumentFragment
import com.yf.smarttemplate.replaceFragmentAndTitle
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
     * 菜单创建回调
     */
    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)
        (activity as AppCompatActivity).menuInflater.inflate(R.menu.main_document_menu, menu)

        // 打开文档，只在首页出现
        menu?.findItem(R.id.action_document)?.setOnMenuItemClickListener {
            activity?.replaceFragmentAndTitle(
                DocumentFragment.newInstance(SmartTemplate.documentPath),
                it.title.toString()
            )
            true
        }
    }

    /**
     * 菜单点击回调
     */
    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            // 监听到首页的菜单返回键
            android.R.id.home -> {
                activity?.supportFragmentManager?.popBackStack()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}