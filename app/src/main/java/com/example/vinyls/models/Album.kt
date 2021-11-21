package com.example.vinyls.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue
import org.json.JSONArray
import org.json.JSONObject


@Parcelize
data class Album (
    val albumId:Int,
    val name:String,
    val cover:String,
    val releaseDate:String,
    val description:String,
    val genre:String,
    val recordLabel:String,
    val tracks: @RawValue JSONArray,
    val performers: @RawValue JSONArray,
    val comments: @RawValue JSONArray
) : Parcelable
{
    fun getArtistsString():String
    {
        var performerString = ""
        var performer: JSONObject?

        for (i in 0 until performers.length())
        {
            performer = performers.getJSONObject(i)
            performerString = if (performerString == "") {
                performerString + "${performer["name"]}"
            } else {
                performerString + " | ${performer["name"]}"
            }
        }
        return performerString
    }
}

