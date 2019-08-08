package com.yf.smarttemplate.sample


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.yf.smarttemplate.SmartTemplate
import kotlinx.android.synthetic.main.fragment_sample_recyclerview.*

class SampleRecyclerViewFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_sample_recyclerview, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        SmartTemplate.generateSampleData(context!!, recyclerView)
    }
}
