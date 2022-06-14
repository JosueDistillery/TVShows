package com.distillery.tvshows.ui.shows

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.distillery.tvshows.data.model.TVShow

/**
 * Adapter for TV shows
 */
class ShowsAdapter(
    private val onItemClick: (TVShow) -> Unit,
    private val onLongClick: (TVShow) -> Unit,
) : RecyclerView.Adapter<ShowsViewHolder>() {

    private var items = mutableListOf<TVShow>()

    override fun getItemCount(): Int = items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShowsViewHolder =
        ShowsViewHolder.create(parent, viewType)

    override fun onBindViewHolder(holder: ShowsViewHolder, position: Int) {
        holder.bindTo(items[position], onItemClick, onLongClick)
    }

    override fun onViewRecycled(holder: ShowsViewHolder) {
        super.onViewRecycled(holder)
        holder.unBind()
    }

    fun setData(tvShows: List<TVShow>) {
        items.clear()
        items.addAll(tvShows)
        notifyDataSetChanged()
    }
}