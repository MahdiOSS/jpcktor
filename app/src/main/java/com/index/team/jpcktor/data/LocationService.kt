package com.index.team.jpcktor.data

import android.system.Os.accept
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.request.accept
import io.ktor.http.ContentType
import io.ktor.http.contentType
import kotlinx.serialization.json.Json

interface LocationService {

    suspend fun getLocation(): LocationEntity?

    suspend fun emitLocation(locationItem: LocationItem): Item?

    companion object {
        fun create(): LocationService {
            return LocationServiceImpl(
                client = HttpClient(Android){
                    defaultRequest {
                        contentType(ContentType.Application.Json)
                        accept(ContentType.Application.Json)
                    }
                    install(ContentNegotiation){
                        json(Json { ignoreUnknownKeys = true })
                    }
                    install(Logging) {
                        level = LogLevel.ALL
                    }
                }
            )
        }

    }
}