package com.yf.smarttemplate.sample.container

import com.yf.smarttemplate.sample.Sample1Activity
import com.yf.smarttemplate.sample.Sample1Fragment
import cn.yfengtech.smart.sample.SampleContainer

class SampleContainer1 : SampleContainer() {
    init {
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