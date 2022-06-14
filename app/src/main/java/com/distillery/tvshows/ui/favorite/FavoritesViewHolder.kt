package com.distillery.tvshows.ui.favorite

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.distillery.tvshows.data.entity.FavoriteTVShow
import com.distillery.tvshows.databinding.ItemFavoriteBinding

class FavoritesViewHolder(private val binding: ItemFavoriteBinding) : RecyclerView.ViewHolder(binding.root) {
    /**
     * @param content [FavoriteTVShow] for this item layout
     * @param onItemClick Click listener for this item layout
     */
    fun bindTo(
        content: FavoriteTVShow,
        onItemClick: (FavoriteTVShow) -> Unit,
        onLongClick: (FavoriteTVShow, Int) -> Unit,
    ) {
        with(binding) {
            binding.favoriteName.text = content.name
            Glide.with(itemView.context).load(content.image).into(binding.favoriteImage)

            root.setOnClickListener { onItemClick(content) }
            root.setOnLongClickListener {
                onLongClick(content, bindingAdapterPosition)
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
         * Factory function to create [FavoritesViewHolder]
         */
        fun create(parent: ViewGroup, layoutId: Int): FavoritesViewHolder =
            FavoritesViewHolder(ItemFavoriteBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }
}
