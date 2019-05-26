package com.yf.smarttemplate

import android.app.Activity
import android.content.Intent
import android.support.v4.app.FragmentActivity
import android.support.v4.app.FragmentTransaction
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView

/**
 * 首页RecyclerView的adapter
 *
 * Created by yf on 2019-05-26.
 */
class TemplateAdapter(private val activity: FragmentActivity, private val sampleContainer: SampleContainer) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val layoutInflate = LayoutInflater.from(activity)

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): RecyclerView.ViewHolder {
        return object :
            RecyclerView.ViewHolder(layoutInflate.inflate(android.R.layout.simple_list_item_2, parent, false)) {}
    }

    override fun getItemCount(): Int = sampleContainer.sampleList.count()

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = sampleContainer.sampleList[position]
        // 设置涟漪效果
        holder.itemView.setBackgroundResource(R.drawable.ripple_bg)
        holder.itemView.findViewById<TextView>(android.R.id.text1).text = item.title
        holder.itemView.findViewById<TextView>(android.R.id.text2).text = item.desc
        holder.itemView.setOnClickListener {

            val context = it.context

            when (item) {
                is ActivitySampleItem -> {
                    if (Activity::class.java.isAssignableFrom(item.clazz))
                        context.startActivity(Intent(context, item.clazz))
                }
                is ExecutionSampleItem -> {
                    item.execution?.invoke(context)
                }
                is SampleContainer -> {
                    activity.supportFragmentManager.beginTransaction()
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .addToBackStack(null)
                        .replace(android.R.id.content, MainFragment.newInstance(item.title, item))
                        .commit()

                }
            }
        }
    }

}