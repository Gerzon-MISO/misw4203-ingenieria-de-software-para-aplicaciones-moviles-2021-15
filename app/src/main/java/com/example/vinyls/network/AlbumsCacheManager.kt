package com.example.vinyls.network

import android.content.Context
import com.example.vinyls.models.Album

class AlbumsCacheManager (context: Context) {
    companion object{
        var instance: AlbumsCacheManager? = null
        fun getInstance(context: Context) =
            instance ?: synchronized(this) {
                instance ?: AlbumsCacheManager(context).also {
                    instance = it
                }
            }
    }

    private var albumscache:HashMap<Int,List<Album>> = hashMapOf()
    fun addAlbums(key:Int,albums:List<Album>)
    {
        if(!albumscache.containsKey(key))
        {
            albumscache[key] = albums
        }
    }

    fun getAlbums(key:Int): List<Album>?
    {
        var listToReturn = listOf<Album>()
        if (albumscache.containsKey(key))
        {
            return albumscache[key]
        }
        else
        {
            return listToReturn
        }
    }
}