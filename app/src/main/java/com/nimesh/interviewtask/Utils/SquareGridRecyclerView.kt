package com.nimesh.interviewtask.Utils

import android.content.Context
import android.util.AttributeSet
import androidx.recyclerview.widget.RecyclerView

/**
 * Created by Nimesh Patel on 4/16/2024.
 * Purpose: Extends RecyclerView to create a grid layout where each item's width matches its height.
 * This ensures a square aspect ratio for each item, facilitating a uniform and
 * visually appealing grid display in UI layouts.
 */
class SquareGridRecyclerView : RecyclerView {

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet?, defStyle: Int) : super(context, attrs, defStyle)

    override fun onMeasure(widthSpec: Int, heightSpec: Int) {
        super.onMeasure(widthSpec, widthSpec)
    }
}