package com.yf.smarttemplate.sample

import android.app.Application
import android.support.v7.app.AlertDialog
import com.yf.smarttemplate.SmartTemplate

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        SmartTemplate.init(this) {
            fragmentItem(Sample1Fragment::class.java) {
                title = "fragment title"
                desc = "fragment desc"
            }
            activityItem(Sample1Activity::class.java) {
                title = "Sample Activity"
                desc = "我是个activity"
            }
            executionItem {
                title = "弹出dialog"
                desc = "直接执行代码块，弹出dialog"
                execute { context ->
                    AlertDialog.Builder(context)
                        .setTitle("我是title")
                        .setMessage("我是message")
                        .create()
                        .show()
                }
            }
            fragmentItem(SampleRecyclerViewFragment::class.java) {
                title = "列表数据"
                desc = "一键创建填充列表数据"
            }
            itemList {
                title = "我是一个分组"
                fragmentItem(Sample1Fragment::class.java) {
                    title = "fragment title"
                    desc = "fragment desc"
                }
                activityItem(Sample1Activity::class.java) {
                    title = "Sample Activity"
                    desc = "我是个activity"
                }
            }
        }
    }
}