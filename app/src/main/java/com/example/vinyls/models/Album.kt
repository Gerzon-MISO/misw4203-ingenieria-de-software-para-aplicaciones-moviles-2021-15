package com.example.vinyls.models

import org.json.JSONArray


data class Album (
    val albumId:Int,
    val name:String,
    val cover:String,
    val releaseDate:String,
    val description:String,
    val genre:String,
    val recordLabel:String,
    val tracks:JSONArray,
    val performers:JSONArray,
    val comments:JSONArray
)
{
    fun getArtistsString():String
    {
        var performer_string = ""
        for (i in 0 until performers.length())
        {
            var performer = performers.getJSONObject(i)
            if (performer_string=="")
            {
                performer_string = performer_string + "${performer["name"]}"
            }
            else
            {
                performer_string = performer_string + " | ${performer["name"]}"
            }

        }
        return performer_string
    }

    fun getArtistCover(): String
    {
        if (performers.length() > 0)
        {
            return "${performers.getJSONObject(0)["image"]}"
        }
        return ""
    }
}

