package com.example.vinyls.network

import android.content.Context
import com.example.vinyls.models.Collector


class CacheManager(context: Context) {

    private var collectors: List<Collector> = mutableListOf()

    companion object{
        var instance: CacheManager? = null
        fun getInstance(context: Context) =
            instance ?: synchronized(this) {
                instance ?: CacheManager(context).also {
                    instance = it
                }
            }
    }

    fun addCollectors(newCollectors: List<Collector>){
            collectors = newCollectors
    }

    fun getCollectors() : List<Collector>{
        return if (collectors.isNotEmpty()) collectors else mutableListOf()
    }
}