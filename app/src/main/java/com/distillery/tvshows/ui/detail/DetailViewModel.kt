package com.distillery.tvshows.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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

    /**
     * Load TV Show Detail
     */
    fun loadDetail(tvShowSelected: TVShow?) {
        tvShowSelected?.let {
            viewModelScope.launch {
                if(!repository.anyFavoriteById(it.id))
                    it.isFavorite = false
                _tvShowDetail.postValue(it)
            }
        }
    }


    fun addFavorite(favorite: TVShow) {
        viewModelScope.launch {
            favorite.isFavorite = true
            repository.addFavorite(favorite)
        }
    }

    fun removeFavorite(favorite: TVShow) {
        viewModelScope.launch {
            favorite.isFavorite = false
            repository.removeFavorite(favorite)
        }
    }
}