package com.index.team.jpcktor

import android.Manifest
import android.content.ContentValues.TAG
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
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
import androidx.lifecycle.lifecycleScope
import com.google.android.gms.location.LocationServices
import com.index.team.jpcktor.data.Item
import com.index.team.jpcktor.data.LocationEntity
import com.index.team.jpcktor.data.LocationItem
import com.index.team.jpcktor.data.LocationService
import com.index.team.jpcktor.ui.theme.JPCKtorTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

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
//                    value = client.getLocation().apply {
//                        for (i in 0..1000) {
//                            val location =
//                                LocationServices.getFusedLocationProviderClient(this@MainActivity)
//
//
//                            val res = client.emitLocation(
//                                LocationItem(
//                                    id = "9p9mcohshx6dxh8",
//                                    lan = "it.longitude.toString()",
//                                    lat = "it.latitude.toString()",
//                                )
//                            )
//                            Log.i(TAG, "onCreate: ${res}")
//
//                            if (ActivityCompat.checkSelfPermission(
//                                    this@MainActivity,
//                                    Manifest.permission.ACCESS_FINE_LOCATION
//                                ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
//                                    this@MainActivity,
//                                    Manifest.permission.ACCESS_COARSE_LOCATION
//                                ) != PackageManager.PERMISSION_GRANTED
//                            ) {
//                                return@apply
//                            }
////                            location.lastLocation.addOnSuccessListener { location ->
////                                location?.let {
////                                    Log.i(
////                                        TAG,
////                                        "onCreate: ${it.latitude.toString() + "  " + it.longitude}"
////                                    )
////                                    lifecycleScope.launch(Dispatchers.IO) {
////                                       val res = client.emitLocation(
////                                            Item(
////                                                "b6fndxjmcskr6hn",
////                                                "Location",
////                                                "",
////                                                updated = "",
////                                                id = "9p9mcohshx6dxh8",
////                                                lan = "it.longitude.toString()",
////                                                lat = "it.latitude.toString()",
////                                            )
////                                        )
////                                        Log.i(TAG, "onCreate: ${res}")
////                                    }
////                                }
////                            }
//                            delay(1000)
//                            value = client.getLocation()
//                        }
//                    }
                    for (i in 0..100) {
                        delay(2000)
                        val res = client.emitLocation(LocationItem("9p9mcohshx6dxh8", "123", "124"))
                        value = res
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

    fun checkPermision() {

    }

}
