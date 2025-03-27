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
import io.ktor.client.request.preparePost
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.client.utils.EmptyContent.contentType
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders.ContentType
import io.ktor.http.contentType
import io.ktor.utils.io.InternalAPI

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
                client.post {
                    url(HttpRoutes.LOCATIONS)
                    contentType(io.ktor.http.ContentType.Application.Json)
                    setBody(locationItem)
                }.call.response.body<Item>()
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
}