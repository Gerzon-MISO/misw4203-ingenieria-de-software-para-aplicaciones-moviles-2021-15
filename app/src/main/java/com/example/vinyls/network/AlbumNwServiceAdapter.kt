package com.example.vinyls.network

import android.content.Context
import com.android.volley.RequestQueue
import com.android.volley.VolleyError
import com.example.vinyls.broker.VolleyBroker
import com.example.vinyls.models.Album
import org.json.JSONArray
import org.json.JSONObject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine


class AlbumNwServiceAdapter constructor(context: Context) {

    private var volleyBroker:VolleyBroker = VolleyBroker(context.applicationContext)
    companion object{
        var instance: AlbumNwServiceAdapter? = null
        fun getInstance(context: Context) =
            instance ?: synchronized(this) {
                instance ?: AlbumNwServiceAdapter(context).also {
                    instance = it
                }
            }
    }

    private val requestQueue:RequestQueue = volleyBroker.instance
    fun getAlbums(onComplete:(resp:List<Album>)->Unit, onError: (error: VolleyError)->Unit){
        requestQueue.add(volleyBroker.getRequest("albums",
            { response ->
                val resp = JSONArray(response)
                val list = mutableListOf<Album>()
                var item: JSONObject?
                for (i in 0 until resp.length()) {
                    item = resp.getJSONObject(i)
                    list.add(i, Album(
                        albumId = item.getInt("id"),
                        name = item.getString("name"),
                        cover = item.getString("cover"),
                        recordLabel = item.getString("recordLabel"),
                        releaseDate = item.getString("releaseDate"),
                        genre = item.getString("genre"),
                        description = item.getString("description"),
                        tracks = item.getJSONArray("tracks"),
                        performers = item.getJSONArray("performers"),
                        comments = item.getJSONArray("comments")
                    ))
                }
                onComplete(list)
            },
            {
                onError(it)
            }))
    }

    suspend fun postAlbum(body: JSONObject) = suspendCoroutine<Boolean>  {
            cont -> requestQueue.add(volleyBroker.postRequest(
        "albums",
        body,
        {
            cont.resume(true)
        },
        {
            cont.resumeWithException(it)
        }
    ))
    }

    fun getAlbum(onComplete:(resp:Album)->Unit, onError: (error: VolleyError)->Unit,albumId:Int){
        requestQueue.add(volleyBroker.getRequest("albums/${albumId}",
            { response ->
                val item = JSONObject(response)
                val targetAlbum = Album(
                    albumId = item.getInt("id"),
                    name = item.getString("name"),
                    cover = item.getString("cover"),
                    recordLabel = item.getString("recordLabel"),
                    releaseDate = item.getString("releaseDate"),
                    genre = item.getString("genre"),
                    description = item.getString("description"),
                    tracks = item.getJSONArray("tracks"),
                    performers = item.getJSONArray("performers"),
                    comments = item.getJSONArray("comments")
                )

                onComplete(targetAlbum)
            },
            {
                onError(it)
            }))
    }

}