package com.ragertf.tinkoffnews.utils.decorations

import android.graphics.Rect
import android.support.v7.widget.RecyclerView
import android.view.View

class MarginItemDecoration(private val space: Int) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        with(outRect) {
            if (parent.getChildAdapterPosition(view) == 0) {
                top = space
            }
            left = space
            right = space
            bottom = space

        }
    }

}