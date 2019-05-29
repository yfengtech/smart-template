package com.yf.smarttemplate.sample

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.*
import com.yf.smarttemplate.R
import com.yf.smarttemplate.adapter.TemplateAdapter
import com.yf.smarttemplate.fragment.SmartFragment
import kotlinx.android.synthetic.main.fragment_main.*

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * Created by songyifeng on 2019-05-24.
 */
class MainFragment : SmartFragment() {

    private var sampleContainer: SampleContainer? = null

    /**
     * `true`表示HomeFragment
     */
    private var isHome: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            sampleContainer = it.getSerializable(ARG_PARAM1) as SampleContainer
            isHome = it.getBoolean(ARG_PARAM2)
        }
        sampleContainer ?: throw IllegalArgumentException("SampleContainer is null")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val activity = activity as? AppCompatActivity ?: return
        val layoutManager = LinearLayoutManager(activity)
        recyclerView.layoutManager = layoutManager
        recyclerView.addItemDecoration(object : DividerItemDecoration(activity, layoutManager.orientation) {})
        recyclerView.adapter = TemplateAdapter(activity, sampleContainer!!)
    }

    companion object {
        @JvmStatic
        fun newInstance(sampleContainer: SampleContainer, isHome: Boolean = false) =
            MainFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(ARG_PARAM1, sampleContainer)
                    putBoolean(ARG_PARAM2, isHome)
                }
            }
    }
}