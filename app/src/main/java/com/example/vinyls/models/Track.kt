package com.example.vinyls.models


data class Track (
    @Transient val trackId: Int,
    val name: String,
    val duration: String,
)