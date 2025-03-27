package com.index.team.jpcktor.data

import kotlinx.serialization.Serializable

@Serializable
data class LocationItem(
    val id: String,
    val lan: String,
    val lat: String,
)