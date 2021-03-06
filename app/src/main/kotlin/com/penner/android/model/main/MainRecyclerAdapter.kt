package com.penner.android.model.main

import android.app.Activity
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.penner.android.*

/**
 * Created by PennerYu on 15/10/10.
 */
class MainRecyclerAdapter(var context: Activity, var list: List<String>) : RecyclerView.Adapter<MainRecyclerAdapter.CellViewHodler>() {

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): CellViewHodler? {
        val view = LayoutInflater.from(context).inflate(R.layout.penner_recycler_item, parent, false);
        val viewHolder = CellViewHodler(view)
        return viewHolder
    }

    override fun onBindViewHolder(holder: CellViewHodler?, position: Int) {
        val value = list.get(position)
        holder?.title?.text = value
        holder?.itemView?.setOnClickListener({
            if (position == 0) {
                context.startActivity(Intent(context, FrescoActivity::class.java))
            } else if (position == 1) {
                context.startActivity(Intent(context, KotlinActivity::class.java))
            } else if (position == 2) {
                context.startActivity(Intent(context, LoginActivity::class.java))
            } else if (position == 3) {
                context.startActivity(Intent(context, BottomTabActivity::class.java))
            } else if (position == 4) {
                context.startActivity(Intent(context, ServiceActivity::class.java))
            } else if (position == 5) {
                context.startActivity(Intent(context, MaterialActivity::class.java))
            } else if (position == 6) {
                context.startActivity(Intent(context, DatabindingActivity::class.java))
            } else if (position == 7) {
                context.startActivity(Intent(context, LargeImageActivity::class.java))
            } else if (position == 8) {
                context.startActivity(Intent(context, ScaleGestureActivity::class.java))
            } else if (position == 9) {
                context.startActivity(Intent(context, RxJavaActivity::class.java))
            } else if (position == 10) {
                context.startActivity(Intent(context, NdkActivity::class.java))
            } else if (position == 11) {
                context.startActivity(Intent(context, AshmenActivity::class.java))
            } else if (position == 12) {
                context.startActivity(Intent(context, WebViewActivity::class.java))
            } else if (position == 13) {
                context.startActivity(Intent(context, EmojiActivity::class.java))
            } else if (position == 14) {
                context.startActivity(Intent(context, PdfActivity::class.java))
            } else if (position == 15) {
                context.startActivity(Intent(context, MyNativeActivity::class.java))
            } else if (position == 16) {
                context.startActivity(Intent(context, TestActivity::class.java))
            }
        })
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class CellViewHodler : RecyclerView.ViewHolder {

        var title: TextView? = null

        constructor(itemView: View) : super(itemView) {
            title = itemView.findViewById(R.id.penner_recycler_txt_tile) as TextView
        }
    }
}