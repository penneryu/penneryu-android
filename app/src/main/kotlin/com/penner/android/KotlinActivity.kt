package com.penner.android

import android.graphics.Color
import android.os.Bundle
import android.widget.StackView
import com.penner.android.base.BaseActivity
import com.penner.android.model.kotlin.StackViewAdapter

/**
 * Created by PennerYu on 15/10/9.
 */
class KotlinActivity : BaseActivity() {

    lateinit var stackview: StackView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kotlin)

        stackview = findViewById(R.id.kotlin_stackview) as StackView

        var colors = IntArray(5)
        colors.set(0, Color.BLUE)
        colors.set(1, Color.CYAN)
        colors.set(2, Color.GRAY)
        colors.set(3, Color.GREEN)
        colors.set(4, Color.RED)

        stackview.adapter = StackViewAdapter(this, colors);
    }
}