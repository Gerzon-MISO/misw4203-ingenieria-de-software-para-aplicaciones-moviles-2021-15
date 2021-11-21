package com.example.vinyls.repositories

import android.app.Application
import android.util.Log
import com.example.vinyls.models.Collector
import com.example.vinyls.network.CacheManager
import com.example.vinyls.network.CollectorNwServiceAdapter


class CollectorRepository (val application: Application) {

    suspend fun refreshCollectorsData(forceInternet: Boolean): List<Collector> {
        val potentialResp = CacheManager.getInstance(application.applicationContext).getCollectors()

        return if (potentialResp.isEmpty() || forceInternet) {
            Log.d("Cache decision", "get from network")
            val collectors = CollectorNwServiceAdapter.getInstance(application).getCollectors()
            CacheManager.getInstance(application.applicationContext).addCollectors(collectors)
            collectors
        } else {
            Log.d("Cache decision", "return ${potentialResp.size} elements from cache")
            potentialResp
        }
    }
}