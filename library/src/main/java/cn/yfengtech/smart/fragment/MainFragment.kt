package cn.yfengtech.smart.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.yf.smarttemplate.R
import cn.yfengtech.smart.adapter.TemplateAdapter
import cn.yfengtech.smart.constants.Params
import cn.yfengtech.smart.debugLog
import cn.yfengtech.smart.sample.SampleContainer

private const val ARG_PARAM1 = "param1"

/**
 * Created by yf on 2019-05-24.
 */
class MainFragment : Fragment() {

    private var sampleContainer: SampleContainer? = null

    /**
     * `true`表示HomeFragment
     */

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            sampleContainer = it.getSerializable(ARG_PARAM1) as SampleContainer
        }
        sampleContainer ?: throw IllegalArgumentException("SampleContainer is null")
        arguments?.remove(ARG_PARAM1)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        debugLog("MainFragment onViewCreated")

        val activity = activity as? AppCompatActivity ?: return
        val layoutManager = LinearLayoutManager(activity)
        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = layoutManager
        recyclerView.addItemDecoration(object :
            androidx.recyclerview.widget.DividerItemDecoration(
                activity,
                layoutManager.orientation
            ) {})
        recyclerView.adapter = TemplateAdapter(activity, sampleContainer!!)
    }

    companion object {
        @JvmStatic
        fun newInstance(sampleContainer: SampleContainer, title: String) =
            MainFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(ARG_PARAM1, sampleContainer)
                    putString(Params.KEY_FRAGMENT_TITLE, title)
                }
            }
    }
}