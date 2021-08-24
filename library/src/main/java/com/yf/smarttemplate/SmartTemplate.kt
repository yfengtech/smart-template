package com.yf.smarttemplate

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.yf.smarttemplate.data.SampleAdapter
import com.yf.smarttemplate.fragment.addHomeFragment
import com.yf.smarttemplate.fragment.registerFragmentLifecycle
import com.yf.smarttemplate.sample.SampleContainer
import com.yf.smarttemplate.ui.MainUI
import com.yf.smarttemplate.ui.replaceContentView
import com.yf.smarttemplate.ui.setToolbarBackListener

/**
 * 智能Demo模板，常用与写一些demo，避免
 *
 * @created songyifeng
 * @date 2020-01-20 22:27
 */
object SmartTemplate {

    @JvmStatic
    fun apply(activity: AppCompatActivity, closure: SampleContainer.() -> Unit) {
        val sample = SampleContainer().apply(closure)
        apply(activity, sample)
    }

    /**
     * 替换activity布局，应用模板
     */
    @JvmStatic
    fun apply(activity: AppCompatActivity, sampleContainer: SampleContainer) {
        // 获取应用的名称
        MainUI.appTitle = activity.application.getAppName()
        // 跟节点 sample列表
        MainUI.originTemplateContainer = sampleContainer

        // 使用AppCompat主题
        activity.setTheme(R.style.SampleAppTheme)
        // 替换原布局，重新setContentView
        activity.replaceContentView()
        // 控制ActionBar左边`返回按钮`的显示和隐藏
        activity.setToolbarBackListener()
        // 注册fragment lifecycle
        activity.registerFragmentLifecycle()
        // 启动home fragment
        activity.addHomeFragment()
    }


    /**
     * 为recyclerView 提供默认数据，填充
     *
     * orientation:列表方向
     * useEnglishText:英文数据还是中文数据
     */
    fun generateSampleData(
        context: Context,
        recyclerView: androidx.recyclerview.widget.RecyclerView,
        orientation: Int = androidx.recyclerview.widget.LinearLayoutManager.VERTICAL,
        itemCount: Int? = null,
        useEnglishText: Boolean = true
    ) {
        recyclerView.layoutManager =
            androidx.recyclerview.widget.LinearLayoutManager(
                context,
                orientation,
                false
            )
        recyclerView.addItemDecoration(
            androidx.recyclerview.widget.DividerItemDecoration(
                context,
                orientation
            )
        )
        recyclerView.adapter = SampleAdapter(context, itemCount, useEnglishText)
    }
}
