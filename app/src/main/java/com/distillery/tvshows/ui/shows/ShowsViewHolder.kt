package com.distillery.tvshows.ui.shows

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.distillery.tvshows.data.model.TVShow
import com.distillery.tvshows.databinding.ItemShowBinding

class ShowsViewHolder(private val binding: ItemShowBinding) : RecyclerView.ViewHolder(binding.root) {
    /**
     * @param content [TVShow] for this item layout
     * @param onItemClick Click listener for this item layout
     */
    fun bindTo(
        content: TVShow,
        onItemClick: (TVShow) -> Unit,
        onLongClick: (TVShow) -> Unit,
    ) {
        with(binding) {
            binding.showName.text = content.name
            Glide.with(itemView.context).load(content.image?.medium).into(binding.showImage)

            root.setOnClickListener { onItemClick(content) }
            root.setOnLongClickListener {
                onLongClick(content)
                true
            }
        }
    }

    /**
     * Clear up
     * Remove listeners
     */
    fun unBind() {
        with(binding) {
            root.setOnClickListener(null)
            root.setOnLongClickListener(null)
        }
    }

    companion object {
        /**
         * Factory function to create [ShowsViewHolder]
         */
        fun create(parent: ViewGroup, layoutId: Int): ShowsViewHolder =
            ShowsViewHolder(ItemShowBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }
}