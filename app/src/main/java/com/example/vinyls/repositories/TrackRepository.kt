package com.example.vinyls.repositories

import android.app.Application
import com.example.vinyls.models.Track
import com.example.vinyls.network.NetworkServiceAdapter
import com.google.gson.Gson
import org.json.JSONObject


class TrackRepository(val application: Application) {

    suspend fun pushData(track: Track, albumId: Int): Boolean {
        val jsonString = Gson().toJson(track)
        val request = JSONObject(jsonString)
        return NetworkServiceAdapter.getInstance(application).postTrack(request, albumId)
    }
}