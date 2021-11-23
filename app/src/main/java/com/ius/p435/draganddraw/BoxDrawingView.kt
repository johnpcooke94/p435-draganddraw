package com.ius.p435.draganddraw

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.PointF
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View

const val TAG = "BOX_DRAWING_VIEW"

class BoxDrawingView constructor(
    context: Context,
    attrs: AttributeSet?
): View(context, attrs){

    private var backgroundPaint = Paint()
    private var currentBoxPaint = Paint()
    private var boxesPaint = Paint()

    var currentBox: Box? = null
    var boxes = mutableListOf<Box>()
    var animate = false

    override fun onTouchEvent(event: MotionEvent?): Boolean {

        event ?: return true

        val current = PointF(event.x, event.y)

        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                Log.d(TAG, "ACTION_DOWN $current")
                if (!maybeRemoveBox(current)) {
                    currentBox = Box(current, current)
                }

            }
            MotionEvent.ACTION_UP -> {
                Log.d(TAG, "ACTION_UP $current")
                addBox()
            }
            MotionEvent.ACTION_MOVE -> {
                Log.d(TAG, "ACTION_MOVE $current")
                currentBox ?: return true
                currentBox!!.end = current
            }
            MotionEvent.ACTION_CANCEL -> {
                Log.d(TAG, "ACTION_CANCEL $current")
            }
        }

        invalidate()
        return true
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        canvas ?: return

        if (animate) {
            moveBoxes()
        }

        backgroundPaint.color = Color.GRAY
        backgroundPaint.alpha = 100

        currentBoxPaint.color = Color.RED
        currentBoxPaint.alpha = 100

        boxesPaint.color = Color.GREEN
        boxesPaint.alpha = 100

        canvas.drawPaint(backgroundPaint)

        for (box in boxes) {
            canvas!!.drawRect(box.start.x, box.start.y,
                box.end.x, box.end.y,
                boxesPaint)
        }

        canvas!!.drawRect(currentBox!!.start.x, currentBox!!.start.y,
            currentBox!!.end.x, currentBox!!.end.y,
            currentBoxPaint)

    }

    fun moveBoxes() {

        for (box in boxes) {

            if (box.end.x >= this.width || box.start.x <= 0) {
                box.deltaX = -box.deltaX
            }

            if (box.end.y >= this.height || box.start.y <= 0) {
                box.deltaY = -box.deltaY
            }

            box.start.x += box.deltaX
            box.start.y += box.deltaY
            box.end.x += box.deltaX
            box.end.y += box.deltaY
        }

        invalidate()
    }

    fun addBox() {
        currentBox ?: return

        val cb = currentBox!!

        if (cb.start.x > cb.end.x) {
            val oldStart = cb.start.x
            cb.start.x = cb.end.x
            cb.end.x = oldStart
        }

        if (cb.start.y > cb.end.y) {
            val oldStart = cb.start.y
            cb.start.y = cb.end.y
            cb.end.y = oldStart
        }

        boxes.add(currentBox!!)
        currentBox == null
    }

    fun maybeRemoveBox(current: PointF): Boolean {
        for (box in boxes) {
            if (current.x > box.start.x && current.y > box.start.y &&
                    current.x <= box.end.x && current.y <= box.end.y) {
                boxes.remove(box)
                return true
            }
        }
        return false
    }

    fun startOrStopAnimation() {
        animate = !animate
        invalidate()
    }

}