package com.example.vinyls.repositories

import android.app.Application
import com.android.volley.VolleyError
import com.example.vinyls.models.Band
import com.example.vinyls.models.Musician
import com.example.vinyls.network.BandNwServiceAdapter
import com.example.vinyls.network.MusicianNwServiceAdapter


class ArtistRepository(val application: Application) {

    fun refreshBandsData(callback:(List<Band>)->Unit, onError:(VolleyError)->Unit)
    {
        BandNwServiceAdapter.getInstance(application).getBands(
            {
                callback(it)
            },
            onError
        )
    }

    fun refreshBandData(callback:(Band)->Unit, onError:(VolleyError)->Unit, bandId:Int)
    {
        BandNwServiceAdapter.getInstance(application).getBand(
            {
                callback(it)
            },
            onError,
            bandId
        )
    }

    fun refreshMusiciansData(callback:(List<Musician>)->Unit, onError:(VolleyError)->Unit)
    {
        MusicianNwServiceAdapter.getInstance(application).getMusicians(
            {
                callback(it)
            },
            onError
        )
    }

    fun refreshMusicianData(callback:(Musician)->Unit, onError:(VolleyError)->Unit, musicianId:Int)
    {
        MusicianNwServiceAdapter.getInstance(application).getMusician(
            {
                callback(it)
            },
            onError,
            musicianId
        )
    }
}