package com.yf.smarttemplate

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.Toolbar
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_main.*

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
private const val ARG_PARAM3 = "param3"

/**
 * Created by songyifeng on 2019-05-24.
 */
class MainFragment : Fragment(), Toolbar.OnMenuItemClickListener {

    private var title: String? = null
    private var sampleContainer: SampleContainer? = null

    /**
     * `true`表示HomeFragment
     */
    private var isHome: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            title = it.getString(ARG_PARAM1)
            sampleContainer = it.getSerializable(ARG_PARAM2) as SampleContainer
            isHome = it.getBoolean(ARG_PARAM3)
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

        if (isHome) {
            toolBar.inflateMenu(R.menu.main_document_menu)
            toolBar.setOnMenuItemClickListener(this)
        } else {
            activity.setSupportActionBar(toolBar)
            activity.supportActionBar?.setDisplayHomeAsUpEnabled(true)
            toolBar.setNavigationOnClickListener {
                activity.supportFragmentManager.popBackStack()
            }
        }

        val layoutManager = LinearLayoutManager(activity)
        recyclerView.layoutManager = layoutManager
        recyclerView.addItemDecoration(object : DividerItemDecoration(activity, layoutManager.orientation) {})
        recyclerView.adapter = TemplateAdapter(activity, sampleContainer!!)
    }

    override fun onMenuItemClick(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            R.id.action_document -> {
                activity!!.supportFragmentManager.beginTransaction()
                    .replace(
                        android.R.id.content,
                        DocumentFragment.newInstance(
                            title!!, "readMe.md"
                        )
                    )
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    .addToBackStack(null)
                    .commit()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(title: String = "default title", sampleContainer: SampleContainer, isHome: Boolean = false) =
            MainFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, title)
                    putSerializable(ARG_PARAM2, sampleContainer)
                    putBoolean(ARG_PARAM3, isHome)
                }
            }
    }
}