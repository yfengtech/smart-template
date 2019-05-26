package com.yf.smarttemplate

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_main.*

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * Created by songyifeng on 2019-05-24.
 */
class MainFragment : Fragment() {

    private var title: String? = null
    private var sampleContainer: SampleContainer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            title = it.getString(ARG_PARAM1)
            sampleContainer = it.getSerializable(ARG_PARAM2) as SampleContainer
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val activity = activity as? AppCompatActivity ?: return
        sampleContainer ?: throw IllegalArgumentException("SampleContainer is null")

        toolBar.title = title

        val layoutManager = LinearLayoutManager(activity)
        recyclerView.layoutManager = layoutManager
        recyclerView.addItemDecoration(object : DividerItemDecoration(activity, layoutManager.orientation) {})
        recyclerView.adapter = TemplateAdapter(activity, sampleContainer!!)
    }


    companion object {
        @JvmStatic
        fun newInstance(title: String = "default title", sampleContainer: SampleContainer) =
            MainFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, title)
                    putSerializable(ARG_PARAM2, sampleContainer)
                }
            }
    }
}