package com.index.team.jpcktor.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Item(
    @SerialName("collectionId")
    val collectionId: String,
    @SerialName("collectionName")
    val collectionName: String,
    @SerialName("created")
    val created: String,
    @SerialName("id")
    val id: String,
    @SerialName("updated")
    val updated: String,
    @SerialName("lan")
    val lan: String,
    @SerialName("lat")
    val lat: String,
)