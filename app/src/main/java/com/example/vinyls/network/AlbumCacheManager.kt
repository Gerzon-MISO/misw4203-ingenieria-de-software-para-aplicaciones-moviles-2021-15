package com.example.vinyls.network

import android.content.Context
import com.example.vinyls.models.Album

class AlbumCacheManager(context:Context) {
    companion object{
        var instance: AlbumCacheManager? = null
        fun getInstance(context: Context) =
            instance ?: synchronized(this) {
                instance ?: AlbumCacheManager(context).also {
                    instance = it
                }
            }
    }

    private var albumcache:HashMap<Int,Album> = hashMapOf()
    fun addAlbum(key:Int,albums:Album,forceRef:Boolean)
    {
        if(!albumcache.containsKey(key) || forceRef)
        {
            albumcache[key] = albums
            println(albums.tracks)
        }

    }

    fun getAlbum(key:Int): Album?
    {
        return if(albumcache[key] != null) albumcache[key] else null
    }
}