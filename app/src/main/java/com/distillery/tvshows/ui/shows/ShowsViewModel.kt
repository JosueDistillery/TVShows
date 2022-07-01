package com.distillery.tvshows.ui.shows

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.distillery.tvshows.data.entity.FavoriteTVShow
import com.distillery.tvshows.data.enums.NetError
import com.distillery.tvshows.network.TVApi
import com.distillery.tvshows.repository.FavoritesRepository
import com.distillery.tvshows.utils.helpers.ConnectivityHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ShowsViewModel @Inject constructor(
    private val tvApi: TVApi,
    private val repository: FavoritesRepository,
    private val connectivityHelper: ConnectivityHelper,
) : ViewModel() {

    private val _tvShows = MutableLiveData(emptyList<FavoriteTVShow>())
    val tvShows: LiveData<List<FavoriteTVShow>> = _tvShows

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
                    val tvShows = tvApi.getShows().map { FavoriteTVShow.from(it) }
                    repository.getFavorites().forEach {
                        tvShows.firstOrNull { item -> item.id == it.id }?.isFavorite = true
                    }
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

    fun doFavoriteAction(tvShow: FavoriteTVShow) {
        viewModelScope.launch {
            if (tvShow.isFavorite) {
                tvShow.isFavorite = false
                repository.removeFavoriteTVShow(tvShow)
            } else {
                tvShow.isFavorite = true
                repository.addFavoriteTVShow(tvShow)
            }
        }
    }
}