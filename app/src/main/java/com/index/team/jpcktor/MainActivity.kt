package com.index.team.jpcktor

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.produceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.LocationServices
import com.index.team.jpcktor.data.Item
import com.index.team.jpcktor.data.LocationItem
import com.index.team.jpcktor.data.LocationService
import com.index.team.jpcktor.ui.theme.JPCKtorTheme
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {
    private val client = LocationService.create()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContent {
            RequestLocationPermissionUsingRememberLauncherForActivityResult(
                onPermissionDenied = {},
                onPermissionGranted = {},
            )
            val location = produceState<Item?>(
                initialValue = null,
                producer = {
                    for (i in 0..10000) {
                        delay(1000)
                        value = getLocation()?.let { client.emitLocation(it) }
                    }
                }
            )
            JPCKtorTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                    ) {
                        Text(
                            ((location.value?.lat
                                ?: "") + "  " + location.value?.lan)
                        )
                    }
                }
            }
        }
    }


    @Composable
    fun RequestLocationPermissionUsingRememberLauncherForActivityResult(
        onPermissionGranted: () -> Unit,
        onPermissionDenied: () -> Unit
    ) {
        // 1. Create a stateful launcher using rememberLauncherForActivityResult
        val locationPermissionLauncher = rememberLauncherForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { permissionsMap ->
            // 2. Check if all requested permissions are granted
            val arePermissionsGranted = permissionsMap.values.reduce { acc, next ->
                acc && next
            }

            // 3. Invoke the appropriate callback based on the permission result
            if (arePermissionsGranted) {
                onPermissionGranted.invoke()
            } else {
                onPermissionDenied.invoke()
            }
        }

        // 4. Launch the permission request on composition
        LaunchedEffect(Unit) {
            locationPermissionLauncher.launch(
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
            )
        }
    }

    suspend fun getLocation(): LocationItem? {

        var locationItem: LocationItem? = LocationItem("1", "2")

        val location =
            LocationServices.getFusedLocationProviderClient(this@MainActivity)

        if (ActivityCompat.checkSelfPermission(
                this@MainActivity,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this@MainActivity,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return@getLocation null
        }
        location.lastLocation.addOnSuccessListener { location ->
            location?.let {
                locationItem = LocationItem(
                    location.longitude.toString(),
                    location.latitude.toString()
                )
            }
        }
        return locationItem
    }

}
