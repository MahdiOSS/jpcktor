package com.index.team.jpcktor.data

import kotlinx.serialization.Serializable

@Serializable
data class LocationEntity(
    val items: List<Item>,
    val page: Int,
    val perPage: Int,
    val totalItems: Int,
    val totalPages: Int
)