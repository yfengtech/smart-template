package com.yf.smarttemplate.sample

import android.app.Application
import android.support.v7.app.AlertDialog
import android.util.Log
import com.yf.smarttemplate.R
import com.yf.smarttemplate.SmartTemplate

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        val drawer = SlidingDrawer().apply {
            item {
                title = "execute"
                execute {
                    val dialog = AlertDialog.Builder(it).create()
                    dialog.setTitle("我是title")
                    dialog.setMessage("我是message")
                    dialog.show()
                }
            }
            item {
                title = "document"
                markdownAssetFileName = "README.md"
            }
            item {
                title = "replace fragment"
                openClazz = Sample1Fragment::class.java
            }
            item {
                groupId = 1
                iconRes = R.drawable.jetpack_logo_small
                title = "start activity"
                openClazz = Sample1Activity::class.java
            }
            item {
                groupId = 2
                iconRes = R.drawable.jetpack_logo_small
                title = "nothing"
            }
        }


        SmartTemplate.init(this, drawer) {

            activityItem(Sample1Activity::class.java) {
                title = "activity title 1"
                desc = "activity desc_1"
            }

            executionItem {
                title = "dialog title"
                desc = "dialog desc"
                execute {
                    val dialog = AlertDialog.Builder(it).create()
                    dialog.setTitle("我是title")
                    dialog.setMessage("我是message")
                    dialog.show()
                }
            }

            fragmentItem(Sample1Fragment::class.java) {
                title = "fragment title"
                desc = "fragment desc"
            }

            itemList {
                title = "list title"
                desc = "list desc"

                activityItem(Sample1Activity::class.java) {
                    title = "activity title 1"
                    desc = "activity desc_1"
                }

                fragmentItem(Sample1Fragment::class.java) {
                    title = "fragment title"
                    desc = "fragment desc"
                }
            }
        }
    }
}