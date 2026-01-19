package ro.pub.cs.systems.pdsd.practicaltest02v3.views

import android.location.Geocoder
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import ro.pub.cs.systems.pdsd.practicaltest02v3.R
import ro.pub.cs.systems.pdsd.practicaltest02v3.general.Constants
import java.io.IOException
import java.util.Locale

class GoogleMapsActivity : AppCompatActivity(), GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private var googleMap: GoogleMap? = null
    private var googleApiClient: GoogleApiClient? = null


    private fun navigateToLocation() {
        val geocoder = Geocoder(this, Locale.getDefault())
        var lat: Double = 0.0
        var long: Double = 0.0

        try {
            // Search for the address (1 result)
            // This is a network operation, so in a real app use a background thread/coroutine.
            // For a simple lab, this might run on the main thread but could stutter the UI.
            val addresses = geocoder.getFromLocationName("Chochirleanca", 1)

            if (addresses != null && addresses.isNotEmpty()) {
                val address = addresses[0]
                lat = address.latitude
                long = address.longitude
                Log.i(Constants.TAG, "SADASDASDASDASDASD")
                Log.i(Constants.TAG, "Found: $lat, $long")
                val cameraPosition = CameraPosition.Builder()
                    .target(LatLng(lat, long))
                    .zoom(Constants.CAMERA_ZOOM.toFloat())
                    .build()
                googleMap?.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))

                // Reuse your existing navigation method

                // Optional: Update the EditTexts so the user sees the coordinates
            } else {
                Log.d(Constants.TAG, "sadasdasdasd")
                Toast.makeText(this, "Location not found", Toast.LENGTH_SHORT).show()
            }
        } catch (ioException: IOException) {
            Log.e(Constants.TAG, "ddddd", ioException)
        } catch (illegalArgumentException: IllegalArgumentException) {
            Log.e(Constants.TAG, "ASddsdasd", illegalArgumentException)
        }
        Log.i(Constants.TAG, "skip")


    }

    private val navigateToLocationButtonListener = object : View.OnClickListener {
        override fun onClick(view: View) {
//            if (latitudeContent.isEmpty() || longitudeContent.isEmpty()) {
//                Toast.makeText(applicationContext, "GPS Coordinates should be filled!", Toast.LENGTH_SHORT).show()
//                return
//            }
//
//            val latitudeValue = latitudeContent.toDouble()
//            val longitudeValue = longitudeContent.toDouble()
//            navigateToLocation(latitudeValue, longitudeValue)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_google_maps)

        Log.i(Constants.TAG, "onCreate() callback method was invoked")


        googleApiClient = GoogleApiClient.Builder(this)
            .addConnectionCallbacks(this)
            .addOnConnectionFailedListener(this)
            .addApi(LocationServices.API)
            .build()
        navigateToLocation()
    }

    override fun onStart() {
        super.onStart()
        Log.i(Constants.TAG, "onStart() callback method was invoked")
        if (googleApiClient != null && !googleApiClient!!.isConnected) {
            googleApiClient!!.connect()
        }
        if (googleMap == null) {
            (supportFragmentManager.findFragmentById(R.id.google_map) as? SupportMapFragment)?.getMapAsync(object : OnMapReadyCallback {
                override fun onMapReady(readyGoogleMap: GoogleMap) {
                    googleMap = readyGoogleMap
                    navigateToLocation()
                }
            })
        }
    }

    override fun onStop() {
        Log.i(Constants.TAG, "onStop() callback method was invoked")
        if (googleApiClient != null && googleApiClient!!.isConnected) {
            googleApiClient!!.disconnect()
        }
        super.onStop()
    }

    override fun onDestroy() {
        Log.i(Constants.TAG, "onDestroy() callback method was invoked")
        googleApiClient = null
        super.onDestroy()
    }

    override fun onConnected(connectionHint: Bundle?) {
        Log.i(Constants.TAG, "onConnected() callback method has been invoked")
    }

    override fun onConnectionSuspended(cause: Int) {
        Log.i(Constants.TAG, "onConnectionSuspended() callback method has been invoked with cause $cause")
    }

    override fun onConnectionFailed(connectionResult: ConnectionResult) {
        Log.i(Constants.TAG, "onConnectionFailed() callback method has been invoked")
    }
}
