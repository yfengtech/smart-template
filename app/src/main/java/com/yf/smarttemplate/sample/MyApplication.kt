package com.yf.smarttemplate.sample

import android.app.Application
import com.yf.smarttemplate.SmartTemplate

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        SmartTemplate.init(this) {
            item {
                id = 3
                title = "title_1"
                desc = "desc_1"
            }
            item {
                id = 4
                title = "title_2"
                desc = "desc_2"
            }
        }
    }
}