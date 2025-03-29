package com.index.team.jpcktor.data

import kotlinx.serialization.Serializable

@Serializable
data class LocationItem(
    val lan: String,
    val lat: String,
)