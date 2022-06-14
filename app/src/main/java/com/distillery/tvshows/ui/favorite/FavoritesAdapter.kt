package com.distillery.tvshows.ui.favorite

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.distillery.tvshows.data.entity.FavoriteTVShow
import com.distillery.tvshows.data.model.TVShow
import com.distillery.tvshows.databinding.ItemFavoriteBinding
import com.distillery.tvshows.ui.shows.ShowsViewHolder

/**
 * Adapter for Favorites TV shows
 */
class FavoritesAdapter(
    private val onItemClick: (FavoriteTVShow) -> Unit,
    private val onLongClick: (FavoriteTVShow) -> Unit,
) : RecyclerView.Adapter<FavoritesViewHolder>() {

    private val items = mutableListOf<FavoriteTVShow>()

    override fun getItemCount(): Int = items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoritesViewHolder =
        FavoritesViewHolder.create(parent, viewType)

    override fun onBindViewHolder(holder: FavoritesViewHolder, position: Int) {
        holder.bindTo(items[position], onItemClick, onLongClick)
    }

    override fun onViewRecycled(holder: FavoritesViewHolder) {
        super.onViewRecycled(holder)
        holder.unBind()
    }

    fun setData(favorites: List<FavoriteTVShow>) {
        items.clear()
        items.addAll(favorites)
        notifyDataSetChanged()
    }
}