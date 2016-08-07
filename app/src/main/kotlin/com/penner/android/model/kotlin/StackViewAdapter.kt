package com.penner.android.model.kotlin

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.LinearLayout
import com.penner.android.util.PennerUtils

/**
 * Created by PennerYu on 15/10/9.
 */
class StackViewAdapter(var context: Context, var colors: IntArray) : BaseAdapter() {

    override fun getCount(): Int {
        return colors.size
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View? {
        var colorLayoutParams = LinearLayout.LayoutParams(PennerUtils.dpToPx(100), PennerUtils.dpToPx(100))
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