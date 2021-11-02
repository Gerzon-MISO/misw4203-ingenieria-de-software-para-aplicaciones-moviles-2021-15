package com.example.vinyls.repositories

import android.app.Application
import com.android.volley.VolleyError
import com.example.vinyls.models.Album
import com.example.vinyls.network.AlbumNwServiceAdapter

class AlbumRepository(val application: Application) {
    fun refreshAlbumsData(callback:(List<Album>)->Unit, onError:(VolleyError)->Unit)
    {
        AlbumNwServiceAdapter.getInstance(application).getAlbums(
            {
                callback(it)
            },
            onError
        )
    }

    fun refreshAlbumData(callback:(Album)->Unit, onError:(VolleyError)->Unit,albumId:Int)
    {
        AlbumNwServiceAdapter.getInstance(application).getAlbum(
            {
                callback(it)
            },
            onError,
            albumId
        )
    }
}