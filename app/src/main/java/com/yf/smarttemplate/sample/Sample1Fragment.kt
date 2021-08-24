package com.yf.smarttemplate.sample


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.yf.smarttemplate.sample.databinding.FragmentSample1Binding

class Sample1Fragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentSample1Binding.inflate(inflater)
        return binding.root
    }
}
