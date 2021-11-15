package com.example.vinyls.repositories

import android.app.Application
import com.android.volley.VolleyError
import com.example.vinyls.models.Collector
import com.example.vinyls.network.CollectorNwServiceAdapter

class CollectorRepository (val application: Application){
    fun refreshCollectorsData(callback:(List<Collector>)->Unit, onError:(VolleyError)->Unit)
    {
        CollectorNwServiceAdapter.getInstance(application).getCollectors(
            {
                callback(it)
            },
            onError
        )
    }

    fun refreshCollectorData(callback:(Collector)->Unit, onError:(VolleyError)->Unit, collectorId:Int)
    {
        CollectorNwServiceAdapter.getInstance(application).getCollector(
            {
                callback(it)
            },
            onError,
            collectorId
        )
    }
}