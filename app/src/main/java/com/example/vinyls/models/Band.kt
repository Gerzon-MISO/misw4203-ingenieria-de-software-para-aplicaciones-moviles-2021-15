package com.example.vinyls.models

import org.json.JSONArray


data class Band (
    val bandId:Int,
    val name:String,
    val image:String,
    val description:String,
    val creationDate:String,
    val albums: JSONArray,
    val musicians: JSONArray,
    val performerPrizes: JSONArray
)