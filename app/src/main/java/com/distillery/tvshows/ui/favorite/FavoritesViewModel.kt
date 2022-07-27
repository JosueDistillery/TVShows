package com.distillery.tvshows.ui.favorite

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
class FavoritesViewModel @Inject constructor(
    private val repository: FavoriteRepository
) : ViewModel() {

    private val _favorites = MutableLiveData(emptyList<TVShow>())
    val favorites: LiveData<List<TVShow>> = _favorites

    /**
     * Load Favorites
     */
    fun loadFavorites() {
        viewModelScope.launch {
            _favorites.postValue(repository.getFavorites())
        }
    }

    fun removeFavorite(favorite: TVShow) {
        viewModelScope.launch {
            repository.removeFavorite(favorite)
        }
    }
}