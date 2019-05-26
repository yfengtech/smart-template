package com.yf.smarttemplate.doc

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.yf.smarttemplate.R
import com.yf.smarttemplate.SmartTemplate
import kotlinx.android.synthetic.main.fragment_document.*

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * Created by songyifeng on 2019-05-24.
 */
class DocumentFragment : Fragment() {

    private var mTitle: String? = null
    private var mDocumentPath: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            mTitle = it.getString(ARG_PARAM1)
            mDocumentPath = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_document, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val activity = activity as? AppCompatActivity ?: return

        toolBar.title = resources.getString(R.string.menu_document)

        activity.setSupportActionBar(toolBar)
        activity.supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolBar.setNavigationOnClickListener {
            activity.supportFragmentManager.popBackStack()
        }

//        markdownView.loadMarkdownFromAssets(mDocumentPath!!)
        SmartTemplate.documentPath?.let {
            markdownView.loadMarkdownFromAssets(it)
        }

    }


    companion object {
        @JvmStatic
        fun newInstance(title: String, documentPath: String = "") =
            DocumentFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, title)
                    putString(ARG_PARAM2, documentPath)
                }
            }
    }
}