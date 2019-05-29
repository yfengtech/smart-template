package com.yf.smarttemplate.sample

import android.support.v4.app.Fragment
import com.yf.smarttemplate.fragment.SmartFragment

/**
 * demo的包装容器，内存有sample列表和嵌套列表
 *
 * Created by yf.
 * @date 2019-05-26
 */
class SampleContainer : SampleItem() {

    /**
     * demo item list
     */
    internal val sampleList: ArrayList<SampleItem> = ArrayList()

    /**
     * 将一个item加入列表
     */

    fun activityItem(clazz: Class<*>, closure: ActivitySampleItem.() -> Unit) {
        sampleList.add(ActivitySampleItem(clazz).apply(closure))
    }

    /**
     * 使用fragment需注意，默认将fragment加入后退栈
     * 所以在fragment中想后退，不要直接finish，请调用`activity.supportFragmentManager.popBackStack()`
     */
    fun <T:SmartFragment> fragmentItem(clazz: Class<T>, closure: FragmentSampleItem.() -> Unit) {
        sampleList.add(FragmentSampleItem(clazz).apply(closure))
    }

    fun executionItem(closure: ExecutionSampleItem.() -> Unit) {
        sampleList.add(ExecutionSampleItem().apply(closure))
    }

    /**
     * 嵌套列表
     */
    fun itemList(closure: SampleContainer.() -> Unit) {
        sampleList.add(SampleContainer().apply(closure))
    }
}