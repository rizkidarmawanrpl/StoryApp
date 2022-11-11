package com.erdeprof.storyapp.dashboard

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.erdeprof.storyapp.R
import com.erdeprof.storyapp.dashboard.data.Story
import com.erdeprof.storyapp.dashboard.presenter.StoriesPresenter
import com.erdeprof.storyapp.dashboard.presenter.StoriesView

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.erdeprof.storyapp.databinding.ActivityMapsBinding
import com.google.android.gms.maps.model.LatLngBounds

class MapsActivity : AppCompatActivity(), OnMapReadyCallback, StoriesView {
    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding
    private val boundsBuilder = LatLngBounds.Builder()
    private lateinit var sharedPreferences: SharedPreferences
    private var token: String? = ""
    private lateinit var storiesPresenter: StoriesPresenter
    private lateinit var tourismPlace: ArrayList<Story>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        sharedPreferences = getSharedPreferences("user_details", MODE_PRIVATE);

        token = sharedPreferences.getString("token", null);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        mMap.uiSettings.isZoomControlsEnabled = true
        mMap.uiSettings.isIndoorLevelPickerEnabled = true
        mMap.uiSettings.isCompassEnabled = true
        mMap.uiSettings.isMapToolbarEnabled = true

        // Add a marker in Sydney and move the camera
//        val sydney = LatLng(-34.0, 151.0)
//        mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))

//        val dicodingSpace = LatLng(-6.8957643, 107.6338462)
//        mMap.addMarker(
//            MarkerOptions()
//                .position(dicodingSpace)
//                .title("Dicoding Space")
//                .snippet("Batik Kumeli No.50")
//        )
//        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(dicodingSpace, 15f))

        val location = 1;

        storiesPresenter = StoriesPresenter(this)
        storiesPresenter.stories(token, location)
    }

//    data class TourismPlace(
//        val name: String,
//        val latitude: Double,
//        val longitude: Double
//    )

    private fun addManyMarker() {
//        val tourismPlace = listOf(
//            TourismPlace("Floating Market Lembang", -6.8168954,107.6151046),
//            TourismPlace("The Great Asia Africa", -6.8331128,107.6048483),
//            TourismPlace("Rabbit Town", -6.8668408,107.608081),
//            TourismPlace("Alun-Alun Kota Bandung", -6.9218518,107.6025294),
//            TourismPlace("Orchid Forest Cikole", -6.780725, 107.637409),
//        )

        tourismPlace.forEach { tourism ->
            val latLng = LatLng(tourism.lat.toString().toDouble(), tourism.lon.toString().toDouble())
            mMap.addMarker(MarkerOptions().position(latLng).title(tourism.name).snippet(tourism.description))
            boundsBuilder.include(latLng)
        }

        val bounds: LatLngBounds = boundsBuilder.build()
        mMap.animateCamera(
            CameraUpdateFactory.newLatLngBounds(
                bounds,
                resources.displayMetrics.widthPixels,
                resources.displayMetrics.heightPixels,
                300
            )
        )
    }

    override fun onSuccessStories(msg: String?, data: ArrayList<Story>?) {
        if (data != null) {
            tourismPlace = data
            addManyMarker()
        }
    }

    override fun onFailedStories(msg: String?) {
        Toast.makeText(this@MapsActivity, msg, Toast.LENGTH_SHORT).show()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}