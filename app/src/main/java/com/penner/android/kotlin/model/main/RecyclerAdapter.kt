package com.penner.android.kotlin.model.main

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.penner.android.FrescoActivity
import com.penner.android.R
import com.penner.android.kotlin.KotlinActivity

/**
 * Created by PennerYu on 15/10/10.
 */
class RecyclerAdapter(var context: Context, var list: List<String>) : RecyclerView.Adapter<RecyclerAdapter.CellViewHodler>() {

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): CellViewHodler? {
        var view = LayoutInflater.from(context).inflate(R.layout.main_recycler_item, parent, false);
        var viewHolder = CellViewHodler(view)
        return viewHolder
    }

    override fun onBindViewHolder(holder: CellViewHodler?, position: Int) {
        var value = list.get(position)
        holder?.title?.text = value;
        holder?.itemView?.setOnClickListener({
            if (position == 0) {
                context.startActivity(Intent(context, FrescoActivity::class.java))
            } else if (position == 1) {
                context.startActivity(Intent(context, KotlinActivity::class.java))
            }
        })
    }

    override fun getItemCount(): Int {
        return list.size()
    }

    class CellViewHodler : RecyclerView.ViewHolder {

        var title: TextView? = null

        constructor(itemView: View) : super(itemView) {
            title = itemView.findViewById(R.id.main_recycler_txt_tile) as TextView;
        }
    }
}