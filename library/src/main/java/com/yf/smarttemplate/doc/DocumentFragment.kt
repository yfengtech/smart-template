package com.yf.smarttemplate.doc

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.yf.smarttemplate.R
import kotlinx.android.synthetic.main.fragment_document.*

private const val ARG_PARAM1 = "param1"

/**
 * Created by songyifeng on 2019-05-24.
 */
internal class DocumentFragment : Fragment() {

    private var mDocumentPath: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            mDocumentPath = it.getString(ARG_PARAM1)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_document, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mDocumentPath?.let {
            markdownView.loadMarkdownFromAssets(it)
        }
    }


    companion object {
        @JvmStatic
        fun newInstance(documentPath: String? = "") =
            DocumentFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, documentPath)
                }
            }
    }
}