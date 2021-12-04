package com.example.vinyls.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue
import org.json.JSONArray
import org.json.JSONObject


@Parcelize
data class AlbumCollector (
    val id:Int,
    val price:Int,
    val status:String,
) : Parcelable
{

}

