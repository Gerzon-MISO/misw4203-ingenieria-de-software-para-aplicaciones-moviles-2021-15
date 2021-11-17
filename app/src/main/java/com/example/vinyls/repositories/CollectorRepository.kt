package com.example.vinyls.repositories

import android.app.Application
import com.android.volley.VolleyError
import com.example.vinyls.models.Collector
import com.example.vinyls.network.CollectorNwServiceAdapter

class CollectorRepository (val application: Application){
    suspend fun refreshCollectorsData(): List<Collector>{
        return CollectorNwServiceAdapter.getInstance(application).getCollectors()
    }

    suspend fun refreshCollectorData(collectorId:Int): Collector{
        return CollectorNwServiceAdapter.getInstance(application).getCollector(collectorId)
    }

}