package cn.yfengtech.smart.data

import android.R
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView


/**
 * 用于快速生成RecyclerView列表
 */
internal class SampleAdapter(
    private val context: Context,
    private val itemCount: Int? = null,
    private val useEnglishText: Boolean = true
) :
    androidx.recyclerview.widget.RecyclerView.Adapter<SampleHolder>() {

    private val data by lazy {
        if (useEnglishText)
            DataProvider.getCountryEnNameList(itemCount)
        else
            DataProvider.getCountryNameList(itemCount)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SampleHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.simple_list_item_1, parent, false)
        return SampleHolder(view)
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: SampleHolder, position: Int) {
        holder.textView.text = data[position]
    }

}

internal class SampleHolder(itemView: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView) {
    val textView = itemView.findViewById<TextView>(R.id.text1)
}