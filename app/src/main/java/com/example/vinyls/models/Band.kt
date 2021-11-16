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
    val performerPrizes: JSONArray)
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