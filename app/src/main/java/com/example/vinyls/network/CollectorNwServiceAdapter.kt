package com.example.vinyls.network

import android.content.Context
import com.android.volley.RequestQueue
import com.android.volley.VolleyError
import com.example.vinyls.broker.VolleyBroker
import com.example.vinyls.models.Album
import com.example.vinyls.models.Collector
import org.json.JSONArray
import org.json.JSONObject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

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

    suspend fun getCollectors() = suspendCoroutine<List<Collector>>{ cont->
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
                cont.resume(list)
            },
            {
                cont.resumeWithException(it)
            }
            ))
    }

    suspend fun getCollector(collectorId:Int) = suspendCoroutine<Collector>{ cont->
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
                cont.resume(targetAlbum)
            },
            {
                cont.resumeWithException(it)
            }
        ))
    }


}