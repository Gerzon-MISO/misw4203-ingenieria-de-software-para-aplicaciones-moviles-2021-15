package com.example.vinyls.repositories

import android.app.Application
import com.android.volley.VolleyError
import com.example.vinyls.models.Album
import com.example.vinyls.network.AlbumNwServiceAdapter
import com.example.vinyls.network.AlbumsCacheManager

class AlbumRepository(val application: Application) {
    fun refreshAlbumsData(callback:(List<Album>)->Unit, onError:(VolleyError)->Unit)
    {

        var albumsCache = AlbumsCacheManager.getInstance(application.applicationContext)
        var potentialResp = albumsCache.getAlbums(intAlbumListIds)
        AlbumNwServiceAdapter.getInstance(application).getAlbums(
            {
                if(potentialResp != null && potentialResp.isEmpty())
                {
                    albumsCache.addAlbums(albumListIds = intAlbumListIds,albums=it)
                }
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