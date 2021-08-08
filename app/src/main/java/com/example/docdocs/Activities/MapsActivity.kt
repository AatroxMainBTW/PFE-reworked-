package com.example.docdocs.Activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.docdocs.R

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.example.docdocs.databinding.ActivityMapsBinding

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding
    val EXTRA_VALUE_POSX: String = "posX"
    val EXTRA_VALUE_POSY: String = "posY"
    val EXTRA_VALUE_NAMEPOS: String = "namePos"
    var latitude:Double? = null
    var longitude:Double? = null
    var posx:String? = null
    var posy:String? = null
    var namepos:String? = null
    var bundle: Bundle? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
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
        bundle = intent.extras!!
        posx = bundle?.get(EXTRA_VALUE_POSX).toString()
        posy = bundle?.get(EXTRA_VALUE_POSY).toString()
        namepos = bundle?.get(EXTRA_VALUE_NAMEPOS).toString()
        latitude = posx?.toDouble()
        longitude = posy?.toDouble()
        // Add a marker in Sydney and move the camera
        val position = LatLng(latitude!!, longitude!!)
        mMap.addMarker(MarkerOptions().position(position).title("$namepos"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(position))
    }
}