package com.example.vinyls.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue
import org.json.JSONArray

@Parcelize
data class Collector (
    val collectorId:Int? = null,
    val name:String? = null,
    val telephone:String? = null,
    val email:String? = null,
    val comments: @RawValue JSONArray? = null,
    val favoritePerformers: @RawValue JSONArray? = null,
    val collectorAlbums:  @RawValue JSONArray? = null,
): Parcelable



