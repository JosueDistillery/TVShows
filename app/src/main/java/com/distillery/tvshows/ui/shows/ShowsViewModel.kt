package com.distillery.tvshows.ui.shows

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.distillery.tvshows.data.entity.TVShow
import com.distillery.tvshows.data.enums.NetError
import com.distillery.tvshows.repository.FavoriteRepository
import com.distillery.tvshows.repository.TVShowRepository
import com.distillery.tvshows.utils.helpers.ConnectivityHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ShowsViewModel @Inject constructor(
    private val showsRepository: TVShowRepository,
    private val favoriteRepository: FavoriteRepository,
    private val connectivityHelper: ConnectivityHelper,
) : ViewModel() {

    private val _tvShows = MutableLiveData(emptyList<TVShow>())
    val tvShows: LiveData<List<TVShow>> = _tvShows

    private val _errorOcurred = MutableLiveData(NetError.NONE)
    val errorOcurred: LiveData<NetError> = _errorOcurred

    /**
     * Load TV Shows
     */
    fun loadTVShows() {
        val hasConnectivity = connectivityHelper.isNetConnected()
        if(hasConnectivity) {
            viewModelScope.launch {
                try {
                    val tvShows = showsRepository.getTVShows()
                    _tvShows.postValue(tvShows)
                    _errorOcurred.postValue(NetError.NONE)
                } catch (error: Throwable) {
                    _errorOcurred.postValue(NetError.TIMEOUT)
                }
            }
        } else {
            _errorOcurred.postValue(NetError.CONNECTIVITY)
        }
    }

    fun doFavoriteAction(tvShow: TVShow) {
        viewModelScope.launch {
            if (tvShow.isFavorite) {
                tvShow.isFavorite = false
                favoriteRepository.removeFavorite(tvShow)
            } else {
                tvShow.isFavorite = true
                favoriteRepository.addFavorite(tvShow)
            }
        }
    }
}