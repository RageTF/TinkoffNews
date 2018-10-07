package com.ragertf.tinkoffnews.utils.decorations

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.support.annotation.ColorInt
import android.support.v7.widget.RecyclerView
import android.view.View

internal class GroupItemDecoration(var titleHeight: Int,
                                   var textSize: Float,
                                   @ColorInt var textColor: Int = Color.BLACK) : RecyclerView.ItemDecoration() {

    private val textBound = Rect()
    private val paint = Paint()
    var positionTitleMap: Map<Int, String> = emptyMap()

    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDrawOver(c, parent, state)
        if (positionTitleMap.isNotEmpty()) {
            for (i in 0 until parent.childCount) {
                val view = parent.getChildAt(i)
                val position = parent.getChildAdapterPosition(view)
                if (positionTitleMap.containsKey(position)) {
                    paint.color = textColor
                    paint.textSize = textSize
                    val text = positionTitleMap[position]
                    if (text != null) {
                        paint.getTextBounds(text, 0, text.length, textBound)
                        c.drawText(text, view.x, view.y - (Math.abs(textBound.bottom) + Math.abs(textBound.top)), paint)
                    }
                }
            }
        }
    }

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        if (positionTitleMap.isEmpty() || parent.childCount <= 0) {
            super.getItemOffsets(outRect, view, parent, state)
        } else {
            val position = parent.getChildAdapterPosition(view)
            if (positionTitleMap.containsKey(position)) {
                outRect.top = titleHeight
            }
        }
    }


}