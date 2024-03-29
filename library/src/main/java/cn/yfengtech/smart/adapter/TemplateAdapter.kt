package cn.yfengtech.smart.adapter

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import com.yf.smarttemplate.R
import cn.yfengtech.smart.constants.Params
import cn.yfengtech.smart.fragment.MainFragment
import cn.yfengtech.smart.fragment.replaceFragment
import cn.yfengtech.smart.sample.ActivitySampleItem
import cn.yfengtech.smart.sample.ExecutionSampleItem
import cn.yfengtech.smart.sample.FragmentSampleItem
import cn.yfengtech.smart.sample.SampleContainer

/**
 * 首页RecyclerView的adapter
 *
 * Created by yf on 2019-05-26.
 */
class TemplateAdapter(private val activity: AppCompatActivity, private val sampleContainer: SampleContainer) :
    androidx.recyclerview.widget.RecyclerView.Adapter<androidx.recyclerview.widget.RecyclerView.ViewHolder>() {

    private val layoutInflate = LayoutInflater.from(activity)

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): androidx.recyclerview.widget.RecyclerView.ViewHolder {
        return object :
            androidx.recyclerview.widget.RecyclerView.ViewHolder(layoutInflate.inflate(android.R.layout.simple_list_item_2, parent, false)) {}
    }

    override fun getItemCount(): Int = sampleContainer.sampleList.count()

    override fun onBindViewHolder(holder: androidx.recyclerview.widget.RecyclerView.ViewHolder, position: Int) {
        val item = sampleContainer.sampleList[position]
        // 设置涟漪效果
        holder.itemView.setBackgroundResource(R.drawable.ripple_bg)
        holder.itemView.findViewById<TextView>(android.R.id.text1).text = item.title
        holder.itemView.findViewById<TextView>(android.R.id.text2).text = item.desc
        holder.itemView.setOnClickListener {

            val context = it.context

            when (item) {
                is ActivitySampleItem<*> -> {
                    context.startActivity(Intent(context, item.clazz))
                }
                is FragmentSampleItem<*> -> {
                    val fragment = item.clazz.newInstance() as androidx.fragment.app.Fragment
                    fragment.arguments = Bundle().apply {
                        putString(Params.KEY_FRAGMENT_TITLE, item.title)
                    }
                    activity.replaceFragment(fragment)
                }
                is ExecutionSampleItem -> {
                    item.execution?.invoke(context)
                }
                is SampleContainer -> {
                    activity.replaceFragment(MainFragment.newInstance(item, item.title))
                }
            }
        }
    }
}