package com.index.team.jpcktor.data

import android.util.Log
import com.index.team.jpcktor.constant.HttpRoutes
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.RedirectResponseException
import io.ktor.client.plugins.ServerResponseException
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.utils.io.InternalAPI
import kotlinx.serialization.json.Json


class LocationServiceImpl(
    private val client: HttpClient
) : LocationService {
    override suspend fun getLocation(): LocationEntity? {
        try {
            val res = client.get { url(HttpRoutes.LOCATIONS) }
            Log.i("Body -> ", res.body<LocationEntity>().toString())
            val json = res.body<LocationEntity>()
            Log.i("Json -> ", json.toString())
            return json
        } catch (e: RedirectResponseException) {
            // 3.x.x
            Log.i("Error -> ", e.response.status.description)
            return null
        } catch (e: ClientRequestException) {
            // 4.x.x
            Log.i("Error -> ", e.response.status.description)
            return null
        } catch (e: ServerResponseException) {
            // 5.x.x
            Log.i("Error -> ", e.response.status.description)
            return null
        } catch (e: Exception) {
            Log.i("Error -> ", e.toString())
            return null
        }
    }

    override suspend fun emitLocation(locationItem: LocationItem): Item? {
        return try {
            val res = client.post {
                url(HttpRoutes.LOCATIONS)
                setBody(locationItem)
            }
            Log.i("Body -> ", res.status.value.toString())
            Log.i("Body -> ", res.body())
            return res.body()
        } catch (e: RedirectResponseException) {
            // 3.x.x
            Log.i("Error 3 -> ", e.response.status.description)
            return null
        } catch (e: ClientRequestException) {
            // 4.x.x
            Log.i("Error 4 -> ", e.response.status.description)
            return null
        } catch (e: ServerResponseException) {
            // 5.x.x
            Log.i("Error 5 -> ", e.response.status.description)
            return null
        } catch (e: Exception) {
            Log.i("Error -> ", e.toString())
            return null
        }
    }
}