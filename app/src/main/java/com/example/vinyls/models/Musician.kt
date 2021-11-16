package com.example.vinyls.models

import org.json.JSONArray

data class Musician (
    val musicianId:Int,
    val name:String,
    val image:String,
    val description:String,
    val birthdate:String,
    val albums:JSONArray,
    val performerPrizes:JSONArray)
{
    fun getAlbumsString():String
    {
        var albums_string = ""
        for (i in 0 until albums.length())
        {
            var album = albums.getJSONObject(i)
            if (albums_string=="")
            {
                albums_string = albums_string + "${album["name"]}"
            }
            else
            {
                albums_string = albums_string + " | ${album["name"]}"
            }

        }
        return albums_string
    }
}