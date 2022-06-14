package com.distillery.tvshows.ui.detail

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.distillery.tvshows.data.entity.FavoriteTVShow
import com.distillery.tvshows.repository.FavoritesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val repository: FavoritesRepository
) : ViewModel() {

    private val _tvShowDetail = MutableLiveData<FavoriteTVShow>()
    val tvShowDetail: LiveData<FavoriteTVShow> = _tvShowDetail

    /**
     * Load TV Show Detail
     */
    @SuppressLint("NullSafeMutableLiveData")
    fun loadDetail(tvShowSelected: FavoriteTVShow) {
        viewModelScope.launch {
            val tvShowDetail = repository.getFavoriteById(tvShowSelected.id) ?: tvShowSelected
            _tvShowDetail.value = tvShowDetail
        }
    }


    fun addFavorite(favoriteTVShow: FavoriteTVShow) {
        viewModelScope.launch {
            favoriteTVShow.isFavorite = true
            repository.addFavoriteTVShow(favoriteTVShow)
        }
    }

    fun removeFavorite(favoriteTVShow: FavoriteTVShow) {
        viewModelScope.launch {
            repository.removeFavoriteTVShow(favoriteTVShow)
        }
    }
}