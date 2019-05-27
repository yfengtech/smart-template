package com.yf.smarttemplate.sample

import android.app.Application
import android.support.v7.app.AlertDialog
import com.yf.smarttemplate.SmartTemplate
import com.yf.smarttemplate.doc.Document

@Document("README.md")
class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        SmartTemplate.init(this) {

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
            }


        }
    }
}