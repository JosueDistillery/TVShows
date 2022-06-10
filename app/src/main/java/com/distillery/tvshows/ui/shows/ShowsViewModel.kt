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

    private val _timeout = MutableLiveData(false)
    val timeout: LiveData<Boolean> = _timeout

    private val _connectivity = MutableLiveData<Boolean>(true)
    val connectivity: LiveData<Boolean> = _connectivity

    init {
        loadTVShows()
    }

    /**
     * Load TV Shows
     */
    fun loadTVShows() {
        _connectivity.value = connectivityHelper.isNetConnected()
        if(connectivity.value!!) {
            viewModelScope.launch {
                try {
                    val tvShows = tvApi.getShows()
                    _tvShows.value = tvShows
                    _timeout.value = false
                } catch (error: Throwable) {
                    _timeout.value = true
                }
            }
        }
    }

    fun getTVShow(position: Int): TVShow = tvShows.value!!.get(position)

    fun getFavoriteTVShow(position: Int): FavoriteTVShow =
        FavoriteTVShow.from(tvShows.value!!.get(position))

    fun addFavorite(position: Int) {
        viewModelScope.launch {
            val tvShow = getTVShow(position)
            repository.addFavoriteTVShow(FavoriteTVShow.from(tvShow))
        }
    }

    fun removeFavorite(position: Int) {
        viewModelScope.launch {
            val favoriteTVShow = getFavoriteTVShow(position)
            repository.removeFavoriteTVShow(favoriteTVShow)
        }
    }

    suspend fun isFavoriteTVShow(position: Int): Boolean {
        val tvShow = getTVShow(position)
        val result = repository.getFavoriteById(tvShow.id)
        return result != null
    }
}