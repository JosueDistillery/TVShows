package com.distillery.tvshows.ui.shows

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.distillery.tvshows.data.entity.FavoriteTVShow

/**
 * Adapter for TV shows
 */
class ShowsAdapter(
    private val onItemClick: (FavoriteTVShow) -> Unit,
) : RecyclerView.Adapter<ShowsViewHolder>() {

    private var items = mutableListOf<FavoriteTVShow>()

    override fun getItemCount(): Int = items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShowsViewHolder =
        ShowsViewHolder.create(parent, viewType)

    override fun onBindViewHolder(holder: ShowsViewHolder, position: Int) {
        holder.bindTo(items[position], onItemClick)
    }

    override fun onViewRecycled(holder: ShowsViewHolder) {
        super.onViewRecycled(holder)
        holder.unBind()
    }

    fun setData(tvShows: List<FavoriteTVShow>) {
        items.clear()
        items.addAll(tvShows)
        notifyDataSetChanged()
    }

    fun setItemChanged(tvShow: FavoriteTVShow, position: Int) {
        items[position] = tvShow
        notifyItemChanged(position)
    }
}