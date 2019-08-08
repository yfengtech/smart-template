package com.yf.smarttemplate.sample

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}


val DEBUG = true
internal inline fun <reified T : Any> T.debugLog(value: String) {
    if (DEBUG) {
        Log.d("SmartTemplate", this::class.java.simpleName + value)
    }
}
