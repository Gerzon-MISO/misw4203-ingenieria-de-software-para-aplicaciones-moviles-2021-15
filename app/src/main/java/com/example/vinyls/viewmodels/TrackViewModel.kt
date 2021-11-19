package com.example.vinyls.viewmodels

import android.app.Application
import androidx.lifecycle.*
import com.example.vinyls.models.Track
import com.example.vinyls.repositories.TrackRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception
import java.lang.IllegalArgumentException


class TrackViewModel(application: Application, albumId: Int, track: Track) : AndroidViewModel(application)  {

    private val trackRepository = TrackRepository(application)

    private var _evenNetworkError = MutableLiveData(false)

    val eventNetworkError: LiveData<Boolean>
        get() = _evenNetworkError

    private var _isNetworkErrorShown = MutableLiveData(false)

    val isNetworkErrorShown: LiveData<Boolean>
        get() = _isNetworkErrorShown

    private val _albumId = albumId
    private val _track = track

    fun pushData() {
        try {
            viewModelScope.launch(Dispatchers.Default) {
                withContext(Dispatchers.IO) {
                    trackRepository.pushData(_track, _albumId)
                }
                _evenNetworkError.postValue(false)
                _isNetworkErrorShown.postValue(false)
            }
        }
        catch (e: Exception) {
            _evenNetworkError.value = true
        }
    }

    fun onNetworkErrorShown() {
        _isNetworkErrorShown.value = true
    }

    class Factory(val app: Application, private val albumId: Int, private val track: Track): ViewModelProvider.Factory {
        override fun <T: ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(TrackViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return TrackViewModel(app, albumId, track) as T
            }
            throw IllegalArgumentException("Unable to construct view model.")
        }
    }
}