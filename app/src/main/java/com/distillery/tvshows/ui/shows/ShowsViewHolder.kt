package com.distillery.tvshows.ui.shows

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.distillery.tvshows.data.entity.Favorite
import com.distillery.tvshows.data.entity.TVShow
import com.distillery.tvshows.databinding.ItemShowBinding

class ShowsViewHolder(private val binding: ItemShowBinding) : RecyclerView.ViewHolder(binding.root) {

    var item: TVShow? = null

    val addView: View by lazy { binding.addView }
    val deleteView: View by lazy { binding.deleteView }
    val foregroundView: View by lazy { binding.foregroundView }

    /**
     * @param content [TVShow] for this item layout
     * @param favorite [Favorite]
     * @param onItemClick Click listener for this item layout
     */
    fun bindTo(
        content: TVShow,
        onItemClick: (TVShow) -> Unit,
    ) {
        item = content

        with(binding) {
            binding.showName.text = content.name
            Glide.with(itemView.context).load(content.image).into(binding.showImage)

            root.setOnClickListener { onItemClick(content) }
        }
    }

    /**
     * Clear up
     * Remove listeners
     */
    fun unBind() {
        with(binding) {
            root.setOnClickListener(null)
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