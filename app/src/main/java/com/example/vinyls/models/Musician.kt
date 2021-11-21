package com.example.vinyls.models

import org.json.JSONArray


data class Musician (
    val musicianId:Int,
    val name:String,
    val image:String,
    val description:String,
    val birthdate:String,
    val albums:JSONArray,
    val performerPrizes:JSONArray
)