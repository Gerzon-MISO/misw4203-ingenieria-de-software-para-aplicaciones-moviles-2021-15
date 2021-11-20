package com.example.vinyls.repositories

import android.app.Application
import android.util.Log
import com.android.volley.VolleyError
import com.example.vinyls.models.Collector
import com.example.vinyls.network.CacheManager
import com.example.vinyls.network.CollectorNwServiceAdapter

class CollectorRepository (val application: Application){
    suspend fun refreshCollectorsData(forceInternet: Boolean): List<Collector>{
        var potentialResp = CacheManager.getInstance(application.applicationContext).getCollectors()
        if(potentialResp.isEmpty() || forceInternet){
            Log.d("Cache decision", "get from network")
            var collectors = CollectorNwServiceAdapter.getInstance(application).getCollectors()
            CacheManager.getInstance(application.applicationContext).addCollectors(collectors)
            return collectors
        }else{
            Log.d("Cache decision", "return ${potentialResp.size} elements from cache")
            return potentialResp
        }
    }

    suspend fun refreshCollectorData(collectorId:Int): Collector{
        return CollectorNwServiceAdapter.getInstance(application).getCollector(collectorId)
    }

}