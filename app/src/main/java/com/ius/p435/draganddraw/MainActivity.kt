package com.ius.p435.draganddraw

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val animateBtn = findViewById<Button>(R.id.animate_btn)
        val drawingView = findViewById<BoxDrawingView>(R.id.drawing_view)

        animateBtn.setOnClickListener {
            drawingView.startOrStopAnimation()
        }
    }
}