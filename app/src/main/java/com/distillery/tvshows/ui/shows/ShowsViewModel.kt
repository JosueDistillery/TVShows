package com.distillery.tvshows.ui.shows

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.distillery.tvshows.data.entity.FavoriteTVShow
import com.distillery.tvshows.data.model.TVShow
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

    private val _tvShows = MutableLiveData(emptyList<TVShow>())
    val tvShows: LiveData<List<TVShow>> = _tvShows

    private val _timeoutOcurred = MutableLiveData(false)
    val timeoutOcurred: LiveData<Boolean> = _timeoutOcurred

    private val _hasConnectivity = MutableLiveData<Boolean>(true)
    val hasConnectivity: LiveData<Boolean> = _hasConnectivity

    init {
        loadTVShows()
    }

    /**
     * Load TV Shows
     */
    fun loadTVShows() {
        _hasConnectivity.value = connectivityHelper.isNetConnected()
        if(hasConnectivity.value!!) {
            viewModelScope.launch {
                try {
                    val tvShows = tvApi.getShows()
                    _tvShows.value = tvShows
                    _timeoutOcurred.value = false
                } catch (error: Throwable) {
                    _timeoutOcurred.value = true
                }
            }
        }
    }

    fun addFavorite(tvShow: TVShow) {
        viewModelScope.launch {
            repository.addFavoriteTVShow(FavoriteTVShow.from(tvShow))
        }
    }

    fun removeFavorite(tvShow: TVShow) {
        viewModelScope.launch {
            repository.removeFavoriteTVShow(FavoriteTVShow.from(tvShow))
        }
    }

    suspend fun isFavoriteTVShow(id: Int): Boolean = repository.anyFavoriteById(id)
}