package com.example.vinyls.viewmodels

import android.app.Application
import androidx.lifecycle.*
import com.example.vinyls.models.Band
import com.example.vinyls.repositories.ArtistRepository


class BandViewModel(application: Application, bandId: Int) : AndroidViewModel(application) {

    private val artistRepository = ArtistRepository(application)
    private val _bandId = bandId
    private val _band = MutableLiveData<Band>()

    val band:LiveData<Band>
        get() = _band

    private  var _eventNetworkError = MutableLiveData(false)

    val eventNetworkError: LiveData<Boolean>
        get() = _eventNetworkError

    private var _isNetworkErrorShown = MutableLiveData(false)

    val isNetworkErrorShown: LiveData<Boolean>
        get() = _isNetworkErrorShown

    init {
        refreshDataFromNetwork()
    }

    private fun refreshDataFromNetwork() {
        artistRepository.refreshBandData({
            _band.postValue(it)
            _eventNetworkError.value = false
            _isNetworkErrorShown.value = false
        },{
            _eventNetworkError.value = true
        },
            _bandId
        )
    }

    fun onNetworkErrorShown() {
        _isNetworkErrorShown.value = true
    }

    class Factory(val app: Application, val bandId: Int) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(BandViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return BandViewModel(app, bandId) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}