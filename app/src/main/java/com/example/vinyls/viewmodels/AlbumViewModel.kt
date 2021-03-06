package com.example.vinyls.viewmodels

import android.app.Application
import androidx.lifecycle.*
import com.example.vinyls.models.Album
import com.example.vinyls.repositories.AlbumRepository


class AlbumViewModel(application: Application,albumId: Int, forcerefresh:Boolean) :  AndroidViewModel(application) {

    private val albumsRepository = AlbumRepository(application)
    private val _albumId = albumId
    private val forceRef = forcerefresh
    private val _album = MutableLiveData<Album>()

    val album: LiveData<Album>
        get() = _album

    private var _eventNetworkError = MutableLiveData(false)

    val eventNetworkError: LiveData<Boolean>
        get() = _eventNetworkError

    private var _isNetworkErrorShown = MutableLiveData(false)

    val isNetworkErrorShown: LiveData<Boolean>
        get() = _isNetworkErrorShown

    init {
        refreshDataFromNetwork()
    }

    private fun refreshDataFromNetwork() {
        if(forceRef)
        {
            Thread.sleep(500)
        }
        albumsRepository.refreshAlbumData({
            _album.postValue(it)
            _eventNetworkError.value = false
            _isNetworkErrorShown.value = false
        },{
            _eventNetworkError.value = true
        },
            _albumId
        ,   forceRef
        )
    }

    fun onNetworkErrorShown() {
        _isNetworkErrorShown.value = true
    }

    class Factory(val app: Application, val albumId:Int, private val forceRefresh: Boolean) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(AlbumViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return AlbumViewModel(app, albumId, forceRefresh) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}