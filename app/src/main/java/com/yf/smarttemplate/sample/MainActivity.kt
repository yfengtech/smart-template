package com.yf.smarttemplate.sample

import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import android.util.Log
import com.yf.smarttemplate.SmartTemplate

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        SmartTemplate.apply(this) {
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


val DEBUG = true
internal inline fun <reified T : Any> T.debugLog(value: String) {
    if (DEBUG) {
        Log.d("SmartTemplate", this::class.java.simpleName + value)
    }
}
