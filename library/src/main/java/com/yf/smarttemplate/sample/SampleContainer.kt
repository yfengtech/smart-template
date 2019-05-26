package com.yf.smarttemplate.sample

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