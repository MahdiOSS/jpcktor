package com.index.team.jpcktor.data

import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import kotlinx.serialization.json.Json

interface LocationService {

    suspend fun getLocation(): LocationEntity?

    suspend fun emitLocation(locationItem: LocationItem): Item?

    companion object {
        fun create(): LocationService {
            return LocationServiceImpl(
                client = HttpClient(Android){
                    install(ContentNegotiation){
                        json(
                            Json {
                                ignoreUnknownKeys = true
                                isLenient = true
                            }
                        )
                    }
                    install(Logging) {
                        level = LogLevel.ALL
                    }
                }
            )
        }

    }
}