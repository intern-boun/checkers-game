package com.example.checkersgame

import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.checkersgame.constants.Constants.Companion.NO_PIECE
import com.example.checkersgame.positionstate.PositionState

@BindingAdapter("listData")
fun bindRecyclerView(recyclerView: RecyclerView, data: List<PositionState>?) {
    val adapter = recyclerView.adapter as GridAdapter
    adapter.submitList(data)
}

@BindingAdapter("setVisibility")
fun setVisibility(imageView: ImageView, selected: Boolean) {
    if(selected) {
        imageView.visibility = View.VISIBLE
    }else{
        imageView.visibility = View.INVISIBLE
    }
}

@BindingAdapter("setPiece")
fun setPiece(imageView: ImageView, colorId: Int){
    if(colorId != NO_PIECE){
        imageView.setImageResource(colorId)
        if(!imageView.isVisible){
            imageView.visibility = View.VISIBLE
        }
    }else{
        imageView.visibility = View.INVISIBLE
    }
}