package com.example.vinyls.repositories

import android.app.Application
import com.android.volley.VolleyError
import com.example.vinyls.models.Album
import com.example.vinyls.network.AlbumCacheManager
import com.example.vinyls.network.AlbumNwServiceAdapter
import com.example.vinyls.network.AlbumsCacheManager


class AlbumRepository(val application: Application) {
    fun refreshAlbumsData(callback:(List<Album>)->Unit, onError:(VolleyError)->Unit,forcerefresh:Boolean)
    {
        val albumsCacheInstance = AlbumsCacheManager.getInstance(application.applicationContext)
        val potentialResp = albumsCacheInstance.getAlbums()

        if (potentialResp.isEmpty() || forcerefresh)
        {
            AlbumNwServiceAdapter.getInstance(application).getAlbums(
                {
                    albumsCacheInstance.addAlbums(it)
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

    fun refreshAlbumData(callback:(Album)->Unit, onError:(VolleyError)->Unit,albumId:Int, forcerefresh:Boolean)
    {
        val albumCacheInstance = AlbumCacheManager.getInstance(application.applicationContext)
        val potentialResp = albumCacheInstance.getAlbum(albumId)

        if(potentialResp == null || forcerefresh)
        {
            AlbumNwServiceAdapter.getInstance(application).getAlbum(
                {
                    albumCacheInstance.addAlbum(albumId,it,forcerefresh)
                    callback(it)
                },
                onError,
                albumId
            )
        }
        else
        {
            callback(potentialResp)
        }
    }
}