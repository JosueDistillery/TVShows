package com.distillery.tvshows.ui.detail

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
    fun loadDetail(tvShowSelected: FavoriteTVShow?) {
        tvShowSelected?.let {
            viewModelScope.launch {
                if(!repository.anyFavoriteById(it.id))
                    it.isFavorite = false
                _tvShowDetail.postValue(it)
            }
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
            favoriteTVShow.isFavorite = false
            repository.removeFavoriteTVShow(favoriteTVShow)
        }
    }
}