package com.example.checkersgame

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.checkersgame.databinding.GridViewItemBinding
import com.example.checkersgame.positionstate.PositionState


class GridAdapter(val onClickListener: OnClickListener):
    ListAdapter<PositionState, GridAdapter.PositionViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GridAdapter.PositionViewHolder {
        return PositionViewHolder(GridViewItemBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: GridAdapter.PositionViewHolder, index: Int) {
        val position = getItem(index)
        holder.bind(position)

        holder.itemView.setOnClickListener {
            onClickListener.onClick(position)
            
            for(index in 0..63){
                notifyItemChanged(index, Unit)
            }
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<PositionState>() {
        override fun areItemsTheSame(oldItem: PositionState, newItem: PositionState): Boolean {
            return oldItem.position == newItem.position
        }

        override fun areContentsTheSame(oldItem: PositionState, newItem: PositionState): Boolean {
            val a = (oldItem.pieceColorId == newItem.pieceColorId) && (oldItem.isSelected == newItem.isSelected)
            return (a && (oldItem.isPointOn == newItem.isPointOn))
        }
    }


    class PositionViewHolder(private var binding: GridViewItemBinding):
        RecyclerView.ViewHolder(binding.root) {
        fun bind(position: PositionState) {
            binding.position = position
            binding.executePendingBindings()
        }

    }
    class OnClickListener(val clickListener: (positionState: PositionState) -> Unit) {
        fun onClick(positionState: PositionState) = clickListener(positionState)
    }


}