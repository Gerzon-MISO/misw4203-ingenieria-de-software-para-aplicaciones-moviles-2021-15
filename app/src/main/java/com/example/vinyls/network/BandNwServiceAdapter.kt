package com.example.vinyls.network

import android.content.Context
import com.android.volley.RequestQueue
import com.android.volley.VolleyError
import com.example.vinyls.broker.VolleyBroker
import com.example.vinyls.models.Band
import org.json.JSONArray
import org.json.JSONObject

class BandNwServiceAdapter constructor(context:Context) {

    private var volleyBroker:VolleyBroker = VolleyBroker(context.applicationContext)
    private val requestQueue:RequestQueue = volleyBroker.instance

    companion object{
        var instance: BandNwServiceAdapter? = null
        fun getInstance(context: Context) =
            instance ?: synchronized(this) {
                instance ?: BandNwServiceAdapter(context).also {
                    instance = it
                }
            }
    }

    fun getBands(onComplete:(resp:List<Band>)->Unit, onError: (error: VolleyError)->Unit){
        requestQueue.add(volleyBroker.getRequest("bands",
            { response ->
                val resp = JSONArray(response)
                val list = mutableListOf<Band>()
                var item: JSONObject?

                for (i in 0 until resp.length()) {
                    item = resp.getJSONObject(i)
                    list.add(
                        i, Band(
                            bandId = item.getInt("id"),
                            name = item.getString("name"),
                            image = item.getString("image"),
                            description = item.getString("description"),
                            creationDate = item.getString("creationDate"),
                            albums = item.getJSONArray("albums"),
                            musicians = item.getJSONArray("musicians"),
                            performerPrizes = item.getJSONArray("performerPrizes")
                        )
                    )
                }
                onComplete(list)
            },
            {
                onError(it)
            }))
    }
}