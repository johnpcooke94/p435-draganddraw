package com.ius.p435.draganddraw

import android.graphics.PointF

const val defaultDelta = 10

data class Box(
    var start: PointF,
    var end: PointF,
    var deltaX: Int = defaultDelta,
    var deltaY: Int = defaultDelta
)
