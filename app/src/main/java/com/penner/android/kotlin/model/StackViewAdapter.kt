package com.penner.android.kotlin.model

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.LinearLayout

/**
 * Created by PennerYu on 15/10/9.
 */
class StackViewAdapter(var context: Context, var colors: IntArray) : BaseAdapter() {

    //    lateinit var context: Context
    //    lateinit var colors: IntArray
    //
    //    constructor(context: Context, colors:IntArray) {
    //        this.context = context;
    //        this.colors = colors;
    //    }

    override fun getCount(): Int {
        return colors.size()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View? {
        var colorLayoutParams = LinearLayout.LayoutParams(100, 100)
        var colorLayout = LinearLayout(context)
        colorLayout.setBackgroundColor(colors.get(position))
        colorLayout.layoutParams = colorLayoutParams

        return colorLayout;
    }

    override fun getItem(position: Int): Any? {
        return colors.get(position)
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }
}