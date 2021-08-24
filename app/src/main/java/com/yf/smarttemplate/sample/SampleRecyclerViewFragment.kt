package com.yf.smarttemplate.sample


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.yf.smarttemplate.SmartTemplate
import com.yf.smarttemplate.sample.databinding.FragmentSampleRecyclerviewBinding

class SampleRecyclerViewFragment : Fragment() {

    private lateinit var viewBinding : FragmentSampleRecyclerviewBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        viewBinding = FragmentSampleRecyclerviewBinding.inflate(inflater)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        SmartTemplate.generateSampleData(requireContext(), viewBinding.recyclerView)
    }
}
