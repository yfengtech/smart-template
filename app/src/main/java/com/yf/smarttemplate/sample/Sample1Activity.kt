package com.yf.smarttemplate.sample

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.yf.smarttemplate.sample.databinding.ActivitySample1Binding

class Sample1Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivitySample1Binding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}
