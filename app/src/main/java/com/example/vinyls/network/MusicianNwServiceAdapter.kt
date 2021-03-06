package com.example.vinyls.network

import android.content.Context
import com.android.volley.RequestQueue
import com.android.volley.VolleyError
import com.example.vinyls.broker.VolleyBroker
import com.example.vinyls.models.Musician
import org.json.JSONArray
import org.json.JSONObject


class MusicianNwServiceAdapter constructor(context:Context) {

    private var volleyBroker:VolleyBroker = VolleyBroker(context.applicationContext)
    private val requestQueue:RequestQueue = volleyBroker.instance

    companion object{
        var instance: MusicianNwServiceAdapter? = null
        fun getInstance(context: Context) =
            instance ?: synchronized(this) {
                instance ?: MusicianNwServiceAdapter(context).also {
                    instance = it
                }
            }
    }

    fun getMusicians(onComplete:(resp:List<Musician>)->Unit, onError: (error: VolleyError)->Unit){
        requestQueue.add(volleyBroker.getRequest("musicians",
            { response ->
                val resp = JSONArray(response)
                val list = mutableListOf<Musician>()
                var item: JSONObject?

                for (i in 0 until resp.length()) {
                    item = resp.getJSONObject(i)
                    list.add(
                        i, Musician(
                            musicianId = item.getInt("id"),
                            name = item.getString("name"),
                            image = item.getString("image"),
                            description = item.getString("description"),
                            birthdate = item.getString("birthDate"),
                            albums = item.getJSONArray("albums"),
                            performerPrizes = item.getJSONArray("performerPrizes")
                        )
                    )
                }
                onComplete(list)
            },
            {
                onError(it)
            })
        )
    }

    fun getMusician(onComplete:(resp:Musician)->Unit, onError: (error: VolleyError)->Unit, musicianId:Int){
        requestQueue.add(volleyBroker.getRequest("musicians/${musicianId}",
            { response ->
                val item = JSONObject(response)
                val targetMusician = Musician(
                            musicianId = item.getInt("id"),
                            name = item.getString("name"),
                            image = item.getString("image"),
                            description = item.getString("description"),
                            birthdate = item.getString("birthDate"),
                            albums = item.getJSONArray("albums"),
                            performerPrizes = item.getJSONArray("performerPrizes")
                )
                onComplete(targetMusician)
            },
            {
                onError(it)
            })
        )
    }
}