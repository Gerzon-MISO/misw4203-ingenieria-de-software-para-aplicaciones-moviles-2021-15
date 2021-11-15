package com.example.vinyls.network

import android.content.Context
import com.android.volley.RequestQueue
import com.android.volley.VolleyError
import com.example.vinyls.broker.VolleyBroker
import com.example.vinyls.models.Album
import com.example.vinyls.models.Collector
import org.json.JSONArray
import org.json.JSONObject

class CollectorNwServiceAdapter constructor(context:Context) {
    var volleyBroker:VolleyBroker = VolleyBroker(context.applicationContext)
    companion object{
        var instance: CollectorNwServiceAdapter? = null
        fun getInstance(context: Context) =
            instance ?: synchronized(this) {
                instance ?: CollectorNwServiceAdapter(context).also {
                    instance = it
                }
            }
    }


    private val requestQueue:RequestQueue = volleyBroker.instance
    fun getCollectors(onComplete:(resp:List<Collector>)->Unit, onError: (error: VolleyError)->Unit){
        requestQueue.add(volleyBroker.getRequest("collectors",
            { response ->
                val resp = JSONArray(response)
                val list = mutableListOf<Collector>()
                for (i in 0 until resp.length()) {
                    val item = resp.getJSONObject(i)
                    list.add(i, Collector(
                        collectorId = item.getInt("id"),
                        name = item.getString("name"),
                        telephone = item.getString("telephone"),
                        email = item.getString("email"),
                        comments = item.getJSONArray("comments"),
                        favoritePerformers = item.getJSONArray("favoritePerformers"),
                        collectorAlbums = item.getJSONArray("collectorAlbums"),
                    ))
                }
                onComplete(list)
            },
            {
                onError(it)
            }))
    }

    fun getCollector(onComplete:(resp:Collector)->Unit, onError: (error: VolleyError)->Unit,collectorId:Int){
        requestQueue.add(volleyBroker.getRequest("collectors/${collectorId}",
            { response ->
                val item = JSONObject(response)
                val targetAlbum = Collector(
                    collectorId = item.getInt("id"),
                    name = item.getString("name"),
                    telephone = item.getString("telephone"),
                    email = item.getString("email"),
                    comments = item.getJSONArray("comments"),
                    favoritePerformers = item.getJSONArray("favoritePerformers"),
                    collectorAlbums = item.getJSONArray("collectorAlbums"),
                )

                onComplete(targetAlbum)
            },
            {
                onError(it)
            }))
    }

}