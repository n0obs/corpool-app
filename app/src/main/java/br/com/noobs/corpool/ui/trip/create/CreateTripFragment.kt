package br.com.noobs.corpool.ui.trip.create

import android.location.Geocoder
import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.TextView.OnEditorActionListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import br.com.noobs.corpool.R
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions


class CreateTripFragment : Fragment() {

    companion object {
        fun newInstance() = CreateTripFragment()
    }

    private lateinit var viewModel: CreateTripViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_create_trip, container, false)


        val addressEt = view.findViewById<EditText>(R.id.et_destiny)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment



        addressEt.setOnEditorActionListener(OnEditorActionListener { _, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH
                || actionId == EditorInfo.IME_ACTION_DONE
                || event != null
                && event.action == KeyEvent.ACTION_DOWN
                && event.keyCode == KeyEvent.KEYCODE_ENTER
            ) {
                if (event == null || !event.isShiftPressed) {

                    mapFragment.getMapAsync { googleMap ->
                        val location = addressEt.text.toString()
                        val geoCoder = Geocoder(inflater.context)
                        val addressList = geoCoder.getFromLocationName(location, 1)
                        if (addressList != null && addressList.size > 0) {
                            val address = addressList[0]
                            val latLng = LatLng(address.latitude, address.longitude)
                            googleMap.addMarker(
                                MarkerOptions().position(latLng)
                                    .title(address.getAddressLine(0))
                            )
                            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 17f))
                        }
                    }




                    return@OnEditorActionListener true // consume.
                }
            }
            false
        })


        return view


    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(CreateTripViewModel::class.java)
        // TODO: Use the ViewModel
    }

}