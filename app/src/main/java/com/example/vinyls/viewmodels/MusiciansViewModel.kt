package com.example.vinyls.viewmodels

import android.app.Application
import androidx.lifecycle.*
import com.example.vinyls.models.Musician
import com.example.vinyls.repositories.ArtistRepository


class MusiciansViewModel(application: Application) : AndroidViewModel(application) {

    private val artistRepository = ArtistRepository(application)

    private val _musicians = MutableLiveData<List<Musician>>()

    val musicians:LiveData<List<Musician>>
        get() = _musicians

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
        artistRepository.refreshMusiciansData({
            _musicians.postValue(it)
            _eventNetworkError.value = false
            _isNetworkErrorShown.value = false
        },{
            _eventNetworkError.value = true
        })
    }

    fun onNetworkErrorShown() {
        _isNetworkErrorShown.value = true
    }

    class Factory(val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(MusiciansViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return MusiciansViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}