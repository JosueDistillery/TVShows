package com.distillery.tvshows.ui.favorite

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.distillery.tvshows.data.entity.TVShow

/**
 * Adapter for Favorites TV shows
 */
class FavoritesAdapter(
    private val onItemClick: (TVShow) -> Unit,
    private val onLongClick: (TVShow, Int) -> Unit,
) : RecyclerView.Adapter<FavoritesViewHolder>() {

    private val items = mutableListOf<TVShow>()

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

    fun setData(favorites: List<TVShow>) {
        items.clear()
        items.addAll(favorites)
        notifyDataSetChanged()
    }

    fun setItemRemoved(position: Int){
        items.removeAt(position)
        notifyItemRemoved(position)
    }
}