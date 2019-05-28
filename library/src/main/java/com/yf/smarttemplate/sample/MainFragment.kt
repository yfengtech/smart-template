package com.yf.smarttemplate.sample

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.*
import com.yf.smarttemplate.R
import com.yf.smarttemplate.SmartTemplate
import com.yf.smarttemplate.adapter.TemplateAdapter
import com.yf.smarttemplate.doc.DocumentFragment
import com.yf.smarttemplate.replaceFragmentAndTitle
import kotlinx.android.synthetic.main.fragment_main.*

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * Created by songyifeng on 2019-05-24.
 */
class MainFragment : Fragment() {

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
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    /**
     * 菜单创建回调
     */
    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)
        (activity as AppCompatActivity).menuInflater.inflate(R.menu.main_document_menu, menu)

        if (isHome) {
            // 打开文档，只在首页出现
            menu?.findItem(R.id.action_document)?.setOnMenuItemClickListener {
                activity?.replaceFragmentAndTitle(
                    DocumentFragment.newInstance(SmartTemplate.documentPath),
                    it.title.toString()
                )
                true
            }
        }
    }

    /**
     * 菜单点击回调
     */
    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            // 监听到首页的菜单返回键
            android.R.id.home -> {
                activity?.supportFragmentManager?.popBackStack()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
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