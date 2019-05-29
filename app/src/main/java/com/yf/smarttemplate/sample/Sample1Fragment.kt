package com.yf.smarttemplate.sample


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.yf.smarttemplate.R
import com.yf.smarttemplate.fragment.SmartFragment

class Sample1Fragment : SmartFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_sample1, container, false)
    }
}
