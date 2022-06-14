package com.distillery.tvshows.ui.favorite

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
class FavoritesViewModel @Inject constructor(
    private val repository: FavoritesRepository
) : ViewModel() {

    private val _favorites = MutableLiveData(emptyList<FavoriteTVShow>())
    val favorites: LiveData<List<FavoriteTVShow>> = _favorites

    /**
     * Load Favorites
     */
    fun loadFavorites() {
        viewModelScope.launch {
            _favorites.value = repository.getFavorites()
        }
    }

    fun removeFavorite(favoriteTVShow: FavoriteTVShow) {
        viewModelScope.launch {
            repository.removeFavoriteTVShow(favoriteTVShow)
        }
    }
}