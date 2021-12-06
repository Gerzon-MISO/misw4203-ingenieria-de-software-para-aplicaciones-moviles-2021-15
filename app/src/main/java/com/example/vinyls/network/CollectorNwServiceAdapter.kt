package com.example.vinyls.network

import android.content.Context
import com.android.volley.RequestQueue
import com.example.vinyls.broker.VolleyBroker
import com.example.vinyls.models.Collector
import org.json.JSONArray
import org.json.JSONObject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine


class CollectorNwServiceAdapter constructor(context:Context) {

    private var volleyBroker: VolleyBroker = VolleyBroker(context.applicationContext)
    private val requestQueue:RequestQueue = volleyBroker.instance

    companion object{
        var instance: CollectorNwServiceAdapter? = null
        fun getInstance(context: Context) =
            instance ?: synchronized(this) {
                instance ?: CollectorNwServiceAdapter(context).also {
                    instance = it
                }
            }
    }

    suspend fun getCollectors() = suspendCoroutine<List<Collector>>{ cont->
        requestQueue.add(volleyBroker.getRequest("collectors",
            { response ->
                val resp = JSONArray(response)
                val list = mutableListOf<Collector>()
                var item: JSONObject?

                for (i in 0 until resp.length()) {
                    item = resp.getJSONObject(i)
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
            )
        )
    }
}