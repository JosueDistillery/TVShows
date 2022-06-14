package com.distillery.tvshows.ui.shows

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.distillery.tvshows.data.entity.FavoriteTVShow
import com.distillery.tvshows.databinding.ItemShowBinding

class ShowsViewHolder(private val binding: ItemShowBinding) : RecyclerView.ViewHolder(binding.root) {

    var item: FavoriteTVShow? = null

    val addView: View by lazy { binding.addView }
    val deleteView: View by lazy { binding.deleteView }
    val foregroundView: View by lazy { binding.foregroundView }

    /**
     * @param content [FavoriteTVShow] for this item layout
     * @param onItemClick Click listener for this item layout
     */
    fun bindTo(
        content: FavoriteTVShow,
        onItemClick: (FavoriteTVShow) -> Unit,
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