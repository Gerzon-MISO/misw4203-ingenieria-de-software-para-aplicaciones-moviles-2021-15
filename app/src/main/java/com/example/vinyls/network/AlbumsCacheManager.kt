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

    private var albumscache:List<Album> = mutableListOf()
    fun addAlbums(albums:List<Album>)
    {
        albumscache = albums
    }

    fun getAlbums(): List<Album>?
    {
        return if(albumscache.isNotEmpty()) albumscache else mutableListOf()
    }
}