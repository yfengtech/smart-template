package com.yf.smarttemplate.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.*
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

    // 文档页，不需要创建菜单
    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
//        super.onCreateOptionsMenu(menu, inflater)
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