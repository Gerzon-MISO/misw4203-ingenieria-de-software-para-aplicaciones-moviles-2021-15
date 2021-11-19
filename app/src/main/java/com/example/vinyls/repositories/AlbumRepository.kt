package com.example.vinyls.repositories

import android.app.Application
import com.android.volley.VolleyError
import com.example.vinyls.models.Album
import com.example.vinyls.network.AlbumNwServiceAdapter
import com.example.vinyls.network.AlbumsCacheManager

class AlbumRepository(val application: Application) {
    fun refreshAlbumsData(callback:(List<Album>)->Unit, onError:(VolleyError)->Unit,forcerefresh:Boolean)
    {
        var albumCacheInstance = AlbumsCacheManager.getInstance(application.applicationContext)
        var potentialResp = albumCacheInstance.getAlbums()
        if (potentialResp!!.isEmpty() || forcerefresh)
        {
            AlbumNwServiceAdapter.getInstance(application).getAlbums(
                {
                    albumCacheInstance.addAlbums(it)
                    callback(it)
                },
                onError
            )
        }
        else
        {
            callback(potentialResp)
        }

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