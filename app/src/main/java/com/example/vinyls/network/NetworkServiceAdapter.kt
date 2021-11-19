package com.example.vinyls.network

import android.content.Context
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine


class NetworkServiceAdapter constructor(context: Context) {

    companion object {
        const val BASE_URL = "https://vynilsmovil.herokuapp.com/"
        var instance: NetworkServiceAdapter? = null
        fun getInstance(context: Context) =
            instance?: synchronized(this) {
                instance?: NetworkServiceAdapter(context).also {
                   instance = it
                }
            }
    }

    private val requestQueue: RequestQueue by lazy {
        Volley.newRequestQueue(context.applicationContext)
    }

    suspend fun postTrack(body: JSONObject, albumId: Int) = suspendCoroutine<Boolean>  {
        cont -> requestQueue.add(postRequest(
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

    private fun postRequest(path: String, body: JSONObject, responseListener: Response.Listener<JSONObject>, errorListener: Response.ErrorListener): JsonObjectRequest {
        return JsonObjectRequest(Request.Method.POST, BASE_URL + path, body, responseListener, errorListener)
    }
}