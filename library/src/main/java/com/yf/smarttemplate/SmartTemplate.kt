package com.yf.smarttemplate

import android.app.Application
import android.util.Log

object SmartTemplate {

    internal val sampleList = mutableListOf<SampleItem>()

    @JvmStatic
    fun init(application: Application, closure: TemplateItem.() -> Unit) {
        TemplateItem().apply(closure)
        Log.d("SmartTemplate", sampleList.toString())
    }
}

class TemplateItem {

    fun item(closure: SampleItem.() -> Unit) {
        SmartTemplate.sampleList.add(SampleItem().apply(closure))
    }
}

class SampleItem(var id: Int = 0, var title: String = "default title", var desc: String = "default desc") {
    override fun toString(): String {
        return "SampleItem(id=$id, title='$title', desc='$desc')"
    }
}