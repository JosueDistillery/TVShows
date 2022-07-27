package com.distillery.tvshows.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.distillery.tvshows.data.entity.Favorite
import com.distillery.tvshows.data.entity.TVShow
import com.distillery.tvshows.repository.FavoriteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val repository: FavoriteRepository
) : ViewModel() {

    private val _tvShowDetail = MutableLiveData<TVShow>()
    val tvShowDetail: LiveData<TVShow> = _tvShowDetail

    private val _isFavorite = MutableLiveData(false)
    val isFavorite: LiveData<Boolean> = _isFavorite

    /**
     * Load TV Show Detail
     */
    fun loadDetail(tvShowSelected: TVShow?) {
        tvShowSelected?.let {
            viewModelScope.launch {
                val favorite = repository.getFavoriteById(it.id)
                favorite?.let {  _isFavorite.postValue(true) }
                _tvShowDetail.postValue(it)
            }
        }
    }

    fun addFavorite(tvShow: TVShow) {
        viewModelScope.launch {
            repository.AddFavorite(Favorite.from(tvShow))
            _isFavorite.postValue(true)
        }
    }

    fun removeFavorite(tvShow: TVShow) {
        viewModelScope.launch {
            val favorite = repository.getFavoriteById(tvShow.id)
            favorite?.let { repository.RemoveFavorite(it) }
            _isFavorite.postValue(false)
        }
    }
}