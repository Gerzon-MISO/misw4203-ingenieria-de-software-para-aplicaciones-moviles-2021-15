package com.example.vinyls.network

import android.content.Context
import com.android.volley.RequestQueue
import com.example.vinyls.broker.VolleyBroker
import org.json.JSONObject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine


class NetworkServiceAdapter constructor(context: Context) {

    var volleyBroker: VolleyBroker = VolleyBroker(context.applicationContext)

    companion object {
        var instance: NetworkServiceAdapter? = null
        fun getInstance(context: Context) =
            instance?: synchronized(this) {
                instance?: NetworkServiceAdapter(context).also {
                   instance = it
                }
            }
    }

    private val requestQueue: RequestQueue by lazy {
        volleyBroker.instance
    }

    suspend fun postTrack(body: JSONObject, albumId: Int) = suspendCoroutine<Boolean>  {
        cont -> requestQueue.add(volleyBroker.postRequest(
            "albums/$albumId/tracks",
            body,
            {
                cont.resume(true)
            },
            {
                cont.resumeWithException(it)
            }
        ))
    }
}