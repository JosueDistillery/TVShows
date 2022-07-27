package com.distillery.tvshows.utils

import android.graphics.Canvas
import android.view.View
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.distillery.tvshows.data.entity.TVShow
import com.distillery.tvshows.ui.shows.ShowsViewHolder

class ItemTouchHelperCallback(
    private val onSwipe: (TVShow, Int) -> Unit
) : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.START) {

    override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean =
        false

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        when(viewHolder) {
            is ShowsViewHolder -> {
                viewHolder.item?.let {
                    onSwipe(it, viewHolder.bindingAdapterPosition)
                }
            }
        }
    }

    override fun onSelectedChanged(viewHolder: RecyclerView.ViewHolder?, actionState: Int) {
        when(viewHolder) {
            is ShowsViewHolder -> getDefaultUIUtil().onSelected(viewHolder.foregroundView)
        }
    }

    override fun onChildDraw(
        c: Canvas, recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder,
        dX: Float, dY: Float, actionState: Int, isCurrentlyActive: Boolean
    ) {
        when(viewHolder) {
            is ShowsViewHolder -> {
                if (viewHolder.item?.isFavorite == true) viewHolder.deleteView.visibility = View.VISIBLE else viewHolder.addView.visibility = View.VISIBLE
                getDefaultUIUtil().onDraw(c, recyclerView, viewHolder.foregroundView, dX, dY, actionState, isCurrentlyActive )
            }
        }
    }

    override fun onChildDrawOver(
        c: Canvas, recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder?,
        dX: Float, dY: Float, actionState: Int, isCurrentlyActive: Boolean
    ) {
        when(viewHolder) {
            is ShowsViewHolder -> getDefaultUIUtil().onDrawOver(c, recyclerView,
                viewHolder.foregroundView, dX, dY, actionState, isCurrentlyActive)
        }
    }

    override fun clearView(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder) {
        when(viewHolder) {
            is ShowsViewHolder -> getDefaultUIUtil().clearView(viewHolder.foregroundView)
        }
    }
}