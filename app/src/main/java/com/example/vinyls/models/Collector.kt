package com.example.vinyls.models

import org.json.JSONArray


data class Collector (
    val collectorId:Int,
    val name:String,
    val telephone:String,
    val email:String,
    val comments:JSONArray,
    val favoritePerformers:JSONArray,
    val collectorAlbums:JSONArray,
)


